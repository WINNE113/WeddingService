package com.wedding.backend.service.impl.user;

import com.wedding.backend.base.BaseResult;
import com.wedding.backend.base.BaseResultWithData;
import com.wedding.backend.base.BaseResultWithDataAndCount;
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

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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

    @Override
    public BaseResult updateUser(String userId, UserDTO userDTO) {
        BaseResult baseResult = null;
        try {
            //TODO
            UserEntity userEntity = userMapper.DtoToEntity(userDTO);
            userRepository.save(userEntity);
            baseResult = BaseResult.builder()
                    .success(true)
                    .message(MessageUtil.MSG_ADD_SUCCESS)
                    .build();
        } catch (Exception ex) {
            baseResult = BaseResult.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .build();
        }
        return baseResult;
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
}
