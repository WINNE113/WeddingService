package com.wedding.backend.service.impl.supplier;

import com.wedding.backend.base.BaseResult;
import com.wedding.backend.base.BaseResultWithData;
import com.wedding.backend.base.BaseResultWithDataAndCount;
import com.wedding.backend.common.ModelCommon;
import com.wedding.backend.common.StatusCommon;
import com.wedding.backend.dto.supplier.LicencesSupplier;
import com.wedding.backend.dto.supplier.SupplierDTO;
import com.wedding.backend.dto.supplier.UpdateSupplierRequest;
import com.wedding.backend.entity.RoleEntity;
import com.wedding.backend.entity.SupplierEntity;
import com.wedding.backend.entity.UserEntity;
import com.wedding.backend.exception.ResourceNotFoundException;
import com.wedding.backend.mapper.SupplierMapper;
import com.wedding.backend.repository.RoleRepository;
import com.wedding.backend.repository.SupplierRepository;
import com.wedding.backend.repository.UserRepository;
import com.wedding.backend.service.IService.supplier.ISupplierService;
import com.wedding.backend.util.handler.FileHandler;
import com.wedding.backend.util.message.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SupplierService implements ISupplierService {
    private final SupplierRepository repository;
    private final FileHandler fileHandler;
    private final SupplierMapper supplierMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public BaseResultWithData<SupplierDTO> getSupplier(Long supplierId) {
        BaseResultWithData<SupplierDTO> result = new BaseResultWithData<>();
        try {
            Optional<SupplierEntity> supplier = repository.findById(supplierId);
            if (supplier.isPresent()) {
                result.Set(true, MessageUtil.MSG_SUCCESS, supplierMapper.entityToDto(supplier.get()));
            } else {
                result.Set(false, MessageUtil.SUPPLIER_NOT_FOUND, null);
            }
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return result;
    }

    @Override
    public BaseResultWithData<SupplierDTO> updateSupplier(UpdateSupplierRequest supplier, MultipartFile supplierImage, List<MultipartFile> imageLicence, Principal connectedUser) {
        return null;
    }

    @Override
    public BaseResult addSupplier(SupplierDTO request, List<MultipartFile> imageLicence, MultipartFile supplierImage, Principal connectedUser) {
        BaseResult result = new BaseResult();
        try {
            var userEntity = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            SupplierEntity supplier;

            // Kiểm tra xem có id trong request không
            if (request.getId() != null) {
                // Tìm nhà cung cấp trong DB theo id
                var supplierFromDB = repository.findById(request.getId());
                if (supplierFromDB.isPresent()) {
                    supplier = supplierFromDB.get();
                } else {
                    result.setSuccess(false);
                    result.setMessage(MessageUtil.SUPPLIER_NOT_FOUND);
                    return result;
                }
            } else {
                // Thêm mới nhà cung cấp
                supplier = new SupplierEntity();
                supplier.setUser(userEntity);
                supplier.setStatusSupplier(StatusCommon.REVIEW);
                supplier.setCreatedBy(userEntity.getId());
                supplier.setCreatedDate(new Date());
            }

            // Kiểm tra số điện thoại nếu có
            if (request.getPhoneNumberSupplier() != null) {
                var supplierWithPhone = repository.findByPhoneNumberSupplier(request.getPhoneNumberSupplier());
                if (supplierWithPhone.isPresent() && !supplierWithPhone.get().getId().equals(request.getId())) {
                    result.setSuccess(false);
                    result.setMessage(MessageUtil.MSG_PHONE_NUMBER_IS_EXITED);
                    return result;
                }
                supplier.setPhoneNumberSupplier(request.getPhoneNumberSupplier());
            }

            // Kiểm tra email nếu có
            if (request.getEmailSupplier() != null) {
                var supplierWithEmail = repository.findByEmailSupplier(request.getEmailSupplier());
                if (supplierWithEmail.isPresent() && !supplierWithEmail.get().getId().equals(request.getId())) {
                    result.setSuccess(false);
                    result.setMessage(MessageUtil.MSG_EMAIL_IS_EXITED);
                    return result;
                }
                supplier.setEmailSupplier(request.getEmailSupplier());
            }

            if (request.getName() != null) {
                supplier.setName(request.getName());
            }

            if (request.getAddressSupplier() != null) {
                supplier.setAddressSupplier(request.getAddressSupplier());
            }

            if (supplierImage != null) {
                supplier.setLogo(fileHandler.getFileUrls(supplierImage));
            }

            if (imageLicence != null && !imageLicence.isEmpty()) {
                List<String> imagesURL = new ArrayList<>();
                for (MultipartFile file : imageLicence) {
                    imagesURL.add(fileHandler.getFileUrls(file));
                }
                supplier.setUrlImagesLicense(imagesURL);
            }
            repository.save(supplier);
            result.setSuccess(true);
            result.setMessage(request.getId() != null ? MessageUtil.MSG_UPDATE_SUCCESS : MessageUtil.MSG_ADD_SUCCESS);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setMessage(ex.getMessage());
            return result;
        }
        return result;
    }


    @Override
    public BaseResultWithDataAndCount<SupplierDTO> getSupplierByUser(Principal connectedUser) {
        BaseResultWithDataAndCount<SupplierDTO> result = new BaseResultWithDataAndCount<>();
        try {
            var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

            Optional<SupplierEntity> dataFromDb = repository.findByUser(user);

            if (dataFromDb.isPresent()) {
                SupplierDTO convertData = supplierMapper.entityToDto(dataFromDb.get());
                //TODO: GET LIST URL LICENCES SUPPLIER
                List<LicencesSupplier> licencesSuppliers = repository.licencesSupplier(dataFromDb.get().getId());
                List<String> urlLicence = new ArrayList<>();
                for (LicencesSupplier item : licencesSuppliers
                ) {
                    urlLicence.add(item.getImagesLicence());
                }
                convertData.setStatusSupplier(dataFromDb.get().getStatusSupplier().name());
                convertData.setImagesLicence(urlLicence);

                result.setData(convertData);
                result.setCount((long) 1);
            }

        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return result;
    }

    @Override
    public BaseResultWithDataAndCount<List<SupplierDTO>> getSuppliersByFalseDeleted(Pageable pageable) {
        BaseResultWithDataAndCount<List<SupplierDTO>> result = new BaseResultWithDataAndCount<>();
        try {
            List<SupplierDTO> resultFromDB = repository.findAllByIsDeletedFalseOrderByCreatedDateDesc(pageable)
                    .stream()
                    .map(supplierMapper::entityToDto)
                    .toList();
            result.set(resultFromDB, (long) resultFromDB.size());
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return result;
    }

    @Override
    public BaseResultWithDataAndCount<List<SupplierDTO>> getSuppliersByFalseDeletedAndByPackageService(Pageable pageable) {
        BaseResultWithDataAndCount<List<SupplierDTO>> result = new BaseResultWithDataAndCount<>();
        try {
            List<SupplierDTO> resultFromDB = repository.findAllByTransactionEntitiesAndIsDeletedFalse(pageable)
                    .stream()
                    .map(supplierMapper::entityToDto)
                    .toList();
            result.set(resultFromDB, (long) resultFromDB.size());
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return result;
    }

    @Override
    public BaseResult checkSupplierExitByUserId(Principal connectedUser) {
        try {
            var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            Optional<SupplierEntity> dataFromDb = repository.findByUser_Id(user.getId());
            if (dataFromDb.isPresent()) {
                Optional<SupplierEntity> checkByStatus = repository.findByStatusSupplierAndUser_Id(StatusCommon.APPROVED, user.getId());
                if (checkByStatus.isPresent()) {
                    return new BaseResult(true, "Supplier is exited!");
                } else {
                    return new BaseResult(false, "Thông tin nhà cung cấp đang được xét duyệt.");
                }
            } else {
                return new BaseResult(false, "Không tìm thấy thông tin nhà cung cấp");
            }
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @Override
    public BaseResultWithDataAndCount<List<SupplierDTO>> getAllSupplierByStatus(String status) {
        BaseResultWithDataAndCount<List<SupplierDTO>> result = new BaseResultWithDataAndCount<>();
        try {
            Long countAllByStatus;
            List<SupplierDTO> dataFromDB;

            // Corrected switch statement with proper data assignment
            switch (StatusCommon.valueOf(status)) {
                case REVIEW -> {
                    dataFromDB = repository.findAllByIsDeletedFalseAndStatusSupplier(StatusCommon.REVIEW)
                            .stream()
                            .map(supplierMapper::entityToDto)
                            .toList();
                    countAllByStatus = repository.countAllByIsDeletedFalseAndStatusSupplier(StatusCommon.REVIEW);
                }
                case APPROVED -> {
                    dataFromDB = repository.findAllByIsDeletedFalseAndStatusSupplier(StatusCommon.APPROVED)
                            .stream()
                            .map(supplierMapper::entityToDto)
                            .toList();
                    countAllByStatus = repository.countAllByIsDeletedFalseAndStatusSupplier(StatusCommon.APPROVED);
                }
                case REJECTED -> {
                    dataFromDB = repository.findAllByIsDeletedFalseAndStatusSupplier(StatusCommon.REJECTED)
                            .stream()
                            .map(supplierMapper::entityToDto)
                            .toList();
                    countAllByStatus = repository.countAllByIsDeletedFalseAndStatusSupplier(StatusCommon.REJECTED);
                }
                default -> {
                    // Handle invalid status
                    throw new IllegalArgumentException("Invalid status: " + status);
                }
            }

            // Iterate over each supplier and set their license images
            for (SupplierDTO supplierDTO : dataFromDB) {
                List<LicencesSupplier> licencesSuppliers = repository.licencesSupplier(supplierDTO.getId());
                List<String> licencesURLs = new ArrayList<>();
                for (LicencesSupplier licence : licencesSuppliers) {
                    licencesURLs.add(licence.getImagesLicence());
                }
                supplierDTO.setImagesLicence(licencesURLs);  // Set the licenses in the DTO
            }

            // Set the result with the list of suppliers and count
            result.set(dataFromDB, countAllByStatus);

        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Status not found: " + status);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("An error occurred while fetching suppliers: " + ex.getMessage());
        }
        return result;
    }


    @Override
    public BaseResultWithDataAndCount<List<SupplierDTO>> getAllSuppliersByAdmin(Pageable pageable) {
        BaseResultWithDataAndCount<List<SupplierDTO>> result = new BaseResultWithDataAndCount<>();
        try {
            // Fetch all non-deleted suppliers and map them to DTOs
            List<SupplierDTO> supplierDTOS = repository.findAllByIsDeletedFalseOrderByCreatedDateDesc(pageable)
                    .stream()
                    .map(supplierMapper::entityToDto)
                    .toList();
            Long countAllData = repository.countAllByIsDeletedFalse();

            // Iterate over each supplier and set their license images
            for (SupplierDTO supplierDTO : supplierDTOS) {
                List<LicencesSupplier> licencesSuppliers = repository.licencesSupplier(supplierDTO.getId());
                List<String> licencesURLs = new ArrayList<>();
                for (LicencesSupplier licence : licencesSuppliers) {
                    licencesURLs.add(licence.getImagesLicence());
                }
                supplierDTO.setImagesLicence(licencesURLs);  // Set the licenses in the DTO
            }

            // Set the result data and count
            result.set(supplierDTOS, countAllData);
        } catch (Exception ex) {
            // Throw a custom exception with a meaningful message
            throw new ResourceNotFoundException("Error fetching suppliers: " + ex.getMessage());
        }
        return result;
    }

    @Override
    public BaseResult updateStatusSupplier(Long supplierId, String statusName) {
        BaseResult result;
        try {
            Optional<SupplierEntity> supplierFromDb = repository.findById(supplierId);

            if (supplierFromDb.isPresent()) {
                Optional<UserEntity> userEntity = userRepository.findById(supplierFromDb.get().getUser().getId());
                if (userEntity.isPresent()) {
                    switch (StatusCommon.valueOf(statusName)) {
                        case APPROVED -> {
                            supplierFromDb.get().setStatusSupplier(StatusCommon.APPROVED);
                            RoleEntity roleEntity = roleRepository.findByName(ModelCommon.MANAGE);
                            Set<RoleEntity> currentRoles = userEntity.get().getRoles();
                            currentRoles.add(roleEntity);
                            userEntity.get().setRoles(currentRoles);
                            userRepository.save(userEntity.get());
                        }
                        case REJECTED -> supplierFromDb.get().setStatusSupplier(StatusCommon.REJECTED);

                    }
                    repository.save(supplierFromDb.get());
                    result = new BaseResult(true, MessageUtil.UPDATE_STATUS_SUPPLIER_SUCCESS);
                } else {
                    result = new BaseResult(false, MessageUtil.MSG_USER_BY_ID_NOT_FOUND);
                }

            } else {
                result = new BaseResult(false, MessageUtil.SUPPLIER_NOT_FOUND);
            }
        } catch (Exception ex) {
            result = new BaseResult(false, MessageUtil.MSG_SYSTEM_ERROR);
        }
        return result;
    }
}
