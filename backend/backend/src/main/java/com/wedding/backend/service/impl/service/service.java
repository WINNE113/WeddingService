package com.wedding.backend.service.impl.service;

import com.wedding.backend.base.BaseResultWithData;
import com.wedding.backend.base.BaseResultWithDataAndCount;
import com.wedding.backend.common.StatusCommon;
import com.wedding.backend.dto.service.ImageAlbDTO;
import com.wedding.backend.dto.service.ImageAlbDTOConvert;
import com.wedding.backend.dto.service.ServiceDTO;
import com.wedding.backend.dto.service.ServiceDetail;
import com.wedding.backend.entity.ServiceTypeEntity;
import com.wedding.backend.exception.ResourceNotFoundException;
import com.wedding.backend.mapper.ServiceMapper;
import com.wedding.backend.repository.ServiceRepository;
import com.wedding.backend.repository.ServiceTypeRepository;
import com.wedding.backend.service.IService.service.IService;
import com.wedding.backend.util.message.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class service implements IService {
    private final ServiceRepository repository;
    private final ServiceMapper mapper;
    private final ServiceTypeRepository serviceTypeRepository;

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

    public ImageAlbDTOConvert convertData(ImageAlbDTO dataConvert) {
        return ImageAlbDTOConvert.builder()
                .imageURL(dataConvert.getImagesURL())
                .albName(dataConvert.getNameAlb())
                .build();
    }
}
