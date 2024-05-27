package com.wedding.backend.service.IService.user;

import com.wedding.backend.base.BaseResult;
import com.wedding.backend.base.BaseResultWithData;
import com.wedding.backend.base.BaseResultWithDataAndCount;
import com.wedding.backend.dto.user.UserDTO;
import com.wedding.backend.entity.UserEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    BaseResultWithDataAndCount<List<UserDTO>> getAllUsers(Pageable pageable);

    BaseResultWithData<UserDTO> getUser(String userId);

    BaseResult updateUser(String userId, UserDTO userDTO);
}
