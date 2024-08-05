package com.wedding.backend.service.impl.service;

import com.wedding.backend.base.BaseResult;
import com.wedding.backend.base.BaseResultWithData;
import com.wedding.backend.base.BaseResultWithDataAndCount;
import com.wedding.backend.common.StatusCommon;
import com.wedding.backend.dto.service.*;
import com.wedding.backend.entity.ServiceEntity;
import com.wedding.backend.entity.ServiceTypeEntity;
import com.wedding.backend.entity.SupplierEntity;
import com.wedding.backend.entity.UserEntity;
import com.wedding.backend.exception.ResourceNotFoundException;
import com.wedding.backend.mapper.ServiceMapper;
import com.wedding.backend.repository.ServiceRepository;
import com.wedding.backend.repository.ServiceTypeRepository;
import com.wedding.backend.repository.SupplierRepository;
import com.wedding.backend.service.IService.service.IService;
import com.wedding.backend.util.handler.FileHandler;
import com.wedding.backend.util.message.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class service implements IService {
    private final ServiceRepository repository;
    private final ServiceMapper mapper;
    private final ServiceTypeRepository serviceTypeRepository;
    private final SupplierRepository supplierRepository;
    private final FileHandler fileHandler;

    @Override
    public BaseResultWithDataAndCount<List<ServiceDTO>> getAllByFalseDeletedAndAcceptStatus(Pageable pageable) {
        BaseResultWithDataAndCount<List<ServiceDTO>> result = new BaseResultWithDataAndCount<>();
        try {
            List<ServiceDTO> resultFromDb = repository.findAllByIsDeletedFalse(pageable)
                    .stream()
                    .map(serviceEntity -> mapper.entityToDto(serviceEntity))
                    .filter(serviceDTO -> serviceDTO.getStatus().equals("APPROVED"))
                    .toList();
            Long countResultFromDb = (long) resultFromDb.size();

            result.set(resultFromDb, countResultFromDb);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return result;
    }

    @Override
    public BaseResultWithDataAndCount<List<ServiceDTO>> getAllByServiceTypeAndAcceptStatus(Long serviceTypeId, Pageable pageable) {
        BaseResultWithDataAndCount<List<ServiceDTO>> result = new BaseResultWithDataAndCount<>();
        try {
            Optional<ServiceTypeEntity> serviceTypeFromDb = serviceTypeRepository.findById(serviceTypeId);
            if (serviceTypeFromDb.isPresent()) {
                List<ServiceDTO> resultFromDb = repository.findAllByServiceTypeAndIsDeletedFalse(serviceTypeFromDb.get(), pageable)
                        .stream()
                        .map(serviceEntity -> mapper.entityToDto(serviceEntity))
                        .filter(serviceDTO -> serviceDTO.getStatus().equals("APPROVED"))
                        .toList();
                Long countResultFromDb = (long) resultFromDb.size();

                result.set(resultFromDb, countResultFromDb);
            } else {
                throw new ResourceNotFoundException(MessageUtil.MSG_SERVICE_TYPE_NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return result;
    }

    @Override
    public BaseResultWithData<ServiceDetail> getDetailServiceById(Long serviceId) {
        BaseResultWithData<ServiceDetail> result = new BaseResultWithData<>();
        try {
            ServiceDetail resultFromDb = repository.serviceDetailById(serviceId);
            result.Set(true, "", resultFromDb);
        } catch (Exception ex) {
            result.Set(false, ex.getMessage(), null);
        }
        return result;
    }

    @Override
    public BaseResultWithDataAndCount<List<ImageAlbDTOConvert>> getAlbumOfServiceByNameAlb(Long serviceId, String albName) {
        BaseResultWithDataAndCount<List<ImageAlbDTOConvert>> result = new BaseResultWithDataAndCount<>();
        try {
            List<ImageAlbDTOConvert> dataAfterConvert = repository.imagesOfAlbum(serviceId, albName)
                    .stream()
                    .map(this::convertData)
                    .toList();
            result.set(dataAfterConvert, (long) dataAfterConvert.size());
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return result;
    }

    @Override
    public BaseResultWithDataAndCount<List<ServiceDTO>> getServiceBySupplier(Pageable pageable, Principal connectedUser) {
        BaseResultWithDataAndCount<List<ServiceDTO>> result = new BaseResultWithDataAndCount<>();
        try {
            var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            Optional<SupplierEntity> supplier = supplierRepository.findByUser_Id(user.getId());
            if (supplier.isPresent()) {
                List<ServiceDTO> resultFromDB = repository.findAllBySupplier_Id(pageable, supplier.get().getId())
                        .stream()
                        .map(mapper::entityToDto)
                        .toList();

                result.set(resultFromDB, (long) resultFromDB.size());
            }
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return result;
    }

    @Override
    public BaseResult upSertService(UpSertServiceDTO serviceDTO, MultipartFile avatar, List<MultipartFile> albums, Principal connectedUser) {
        try {
            ServiceEntity service;
            boolean isNewService = serviceDTO.getId() == null;
            var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            Optional<SupplierEntity> supplier = supplierRepository.findByUser_Id(user.getId());

            if (isNewService) {
                // Insert new service
                service = new ServiceEntity();
                supplier.ifPresent(service::setSupplier);
            } else {
                // Update existing service
                Optional<ServiceEntity> optionalService = repository.findById(serviceDTO.getId());
                if (optionalService.isPresent()) {
                    service = optionalService.get();
                } else {
                    return new BaseResult(false, MessageUtil.MSG_SERVICE_NOT_FOUND);
                }
            }

            // Common fields for insert and update
            service.setTitle(serviceDTO.getTitle());
            service.setInformation(serviceDTO.getInformation());
            service.setAddress(serviceDTO.getAddress());
            service.setPhoneNumber(serviceDTO.getPhoneNumber());
            service.setLinkWebsite(serviceDTO.getLinkWebsite());
            service.setLinkFacebook(serviceDTO.getLinkFacebook());
            service.setRotation(serviceDTO.getRotation());
            service.setDeleted(false); // Might want to check if this should only be set on creation
            service.setStatus(isNewService ? StatusCommon.REVIEW : service.getStatus());

            Optional<ServiceTypeEntity> serviceType = serviceTypeRepository.findById(serviceDTO.getServiceTypeId());
            serviceType.ifPresent(service::setServiceType);

            // Handle avatar upload
            if (avatar != null && !avatar.isEmpty()) {
                service.setImage(fileHandler.getFileUrls(avatar));
            }

            // TODO: set serviceAlbum && set ServicePromotion
            // You may want to handle album and promotion updates/insertions here

            // Save or update the service entity
            repository.save(service);

            return new BaseResult(true, MessageUtil.MSG_ADD_SUCCESS);
        } catch (Exception ex) {
            return new BaseResult(false, ex.getMessage());
        }
    }

    public ImageAlbDTOConvert convertData(ImageAlbDTO dataConvert) {
        return ImageAlbDTOConvert.builder()
                .imageURL(dataConvert.getImagesURL())
                .albName(dataConvert.getNameAlb())
                .build();
    }
}
