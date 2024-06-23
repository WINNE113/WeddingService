package com.wedding.backend.service.impl.user;

import com.cloudinary.Cloudinary;
import com.wedding.backend.base.BaseResult;
import com.wedding.backend.base.BaseResultWithData;
import com.wedding.backend.base.BaseResultWithDataAndCount;
import com.wedding.backend.dto.user.UpdateProfileRequest;
import com.wedding.backend.dto.user.UserDTO;
import com.wedding.backend.entity.UserEntity;
import com.wedding.backend.exception.ResourceNotFoundException;
import com.wedding.backend.mapper.UserMapper;
import com.wedding.backend.repository.UserRepository;
import com.wedding.backend.service.IService.user.IUserService;
import com.wedding.backend.util.message.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Cloudinary cloudinary;


    @Override
    public Optional<UserEntity> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public BaseResultWithDataAndCount<List<UserDTO>> getAllUsers(Pageable pageable) {
        BaseResultWithDataAndCount<List<UserDTO>> baseResultWithDataAndCount = new BaseResultWithDataAndCount<>();
        try {
            List<UserDTO> userDTOList = userRepository.findAllByIsDeletedFalse(pageable)
                    .stream()
                    .map(userEntity -> userMapper.entityToDto(userEntity))
                    .toList();
            Long countAllResultCustomer = userRepository.countAllByIsDeletedFalse();
            baseResultWithDataAndCount.set(userDTOList, countAllResultCustomer);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return baseResultWithDataAndCount;
    }

    @Override
    public BaseResultWithData<UserDTO> getUser(String userId) {
        BaseResultWithData<UserDTO> baseResultWithData = new BaseResultWithData<>();
        try {
            Optional<UserEntity> userFromDb = userRepository.findById(userId);
            if (userFromDb.isPresent()) {
                UserDTO userDTO = userMapper.entityToDto(userFromDb.get());
                baseResultWithData.Set(true, MessageUtil.MSG_PROCESS_SUCCESS, userDTO);
            } else {
                baseResultWithData.Set(false, MessageUtil.MSG_USER_BY_ID_NOT_FOUND, null);
            }
        } catch (Exception ex) {
            baseResultWithData.Set(false, ex.getMessage(), null);
        }
        return baseResultWithData;
    }

    private void checkIfCustomerExitsOrThrow(String userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new ResourceNotFoundException(
                    "User with id [%s] not found".formatted(userId)
            );
        }
    }

    @Override
    public UserDTO viewProfile(Principal connectedUser) {
        var userEntity = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return userMapper.entityToDto(userEntity);
    }

    @Override
    public BaseResult updateProfile(UpdateProfileRequest profileRequest, MultipartFile images, Principal connectedUser) throws IOException {
        try {
            var userEntity = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            // Chua te if-else
            if (profileRequest.getUserName() != null) {
                userEntity.setUserName(profileRequest.getUserName());
            }
            if (profileRequest.getEmail() != null) {
                userEntity.setEmail(profileRequest.getEmail());
            }
            if (profileRequest.getDateOfBirth() != null) {
                userEntity.setDateOfBirth(profileRequest.getDateOfBirth());
            }
            if (profileRequest.getAddress() != null) {
                userEntity.setAddress(profileRequest.getAddress());
            }
            if (images != null) {
                userEntity.setProfileImage(getFileUrls(images));
            }
            userRepository.save(userEntity);
            return new BaseResult(true, "Cập nhật thông tin thành công!");
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    private String getFileUrls(MultipartFile multipartFile) throws IOException {
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }
}
