package com.wedding.backend.service.impl.auth;

import com.wedding.backend.base.BaseResult;
import com.wedding.backend.common.ModelCommon;
import com.wedding.backend.dto.auth.LoginDTO;
import com.wedding.backend.dto.auth.LoginResponse;
import com.wedding.backend.dto.auth.RegisterDTO;
import com.wedding.backend.dto.auth.TokenTypeDTO;
import com.wedding.backend.entity.RoleEntity;
import com.wedding.backend.entity.TokenEntity;
import com.wedding.backend.entity.UserEntity;
import com.wedding.backend.repository.RoleRepository;
import com.wedding.backend.repository.TokenRepository;
import com.wedding.backend.repository.UserRepository;
import com.wedding.backend.service.IService.auth.IAuthenticationService;
import com.wedding.backend.service.IService.auth.IJWTService;
import com.wedding.backend.util.helper.HashHelper;
import com.wedding.backend.util.message.MessageUtil;
import com.wedding.backend.util.validator.PhoneNumberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IJWTService jwtService;
    private final TokenRepository tokenRepository;


    @Override
    public ResponseEntity<?> register(RegisterDTO request) {
        ResponseEntity<?> response = null;
        try {
            if (!PhoneNumberValidator.isValidPhoneNumber(request.getPhoneNumber())) {
                BaseResult baseResult = new BaseResult(false, MessageUtil.MSG_PHONE_NUMBER_FORMAT_INVALID);
                response = new ResponseEntity<>(baseResult, HttpStatus.BAD_REQUEST);
                return response;
            }

            Optional<UserEntity> existingUser = userRepository.findByPhoneNumber(PhoneNumberValidator.normalizePhoneNumber(request.getPhoneNumber()));

            if (existingUser.isPresent()) {
                BaseResult baseResult = new BaseResult(false, MessageUtil.MSG_PHONE_NUMBER_IS_EXITED);
                response = new ResponseEntity<>(baseResult, HttpStatus.BAD_REQUEST);
                return response;
            }

            if (request.getRole().equals(ModelCommon.CUSTOMER)) {
                boolean checkRegister = this.baseRegister(request);
                if (checkRegister) {
                    BaseResult baseResult = new BaseResult(true, MessageUtil.MSG_REGISTER_SUCCESS);
                    response = new ResponseEntity<>(baseResult, HttpStatus.OK);
                }
            }
            //TODO: Check if other role like manager..

        } catch (Exception ex) {
            BaseResult baseResult = new BaseResult(false, ex.getMessage());
            response = new ResponseEntity<>(baseResult, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public LoginResponse login(LoginDTO request) {
        // normalize phone number
        String normalizePhoneNumber = PhoneNumberValidator.normalizePhoneNumber(request.getPhoneNumber());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            normalizePhoneNumber,
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            return LoginResponse.error(MessageUtil.MSG_AUTHENTICATION_FAIL);
        }

        var user = userRepository.findByPhoneNumber(normalizePhoneNumber);
        if (user.isPresent()) {
            var jwtToken = jwtService.generateToken(user.get());

            revokeAllUserTokens(user.get());
            saveUserToken(user, jwtToken);

            return LoginResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            return LoginResponse.error(MessageUtil.MSG_USER_BY_TOKEN_NOT_FOUND);
        }
    }

    private void saveUserToken(Optional<UserEntity> user, String jwtToken) {
        if (user.isPresent()) {
            var token = TokenEntity.builder()
                    .userEntity(user.get())
                    .token(jwtToken)
                    .tokeType(TokenTypeDTO.BEARER)
                    .revoked(false)
                    .expired(false)
                    .build();
            tokenRepository.save(token);
        }
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private boolean baseRegister(RegisterDTO request) {
        RoleEntity role = roleRepository.findByName(request.getRole());
        if (role != null) {
            UserEntity user = new UserEntity();
            user.setId(HashHelper.generateEntityId());
            user.setUserName(request.getUserName());
            user.setBalance(new BigDecimal(0));     // Default balance of user is 0 Vnd
            user.setPhoneNumber(PhoneNumberValidator.normalizePhoneNumber(request.getPhoneNumber()));
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            user.setCreatedBy("");
            user.setCreatedDate(new Date());
            if (request.getRole().equals(ModelCommon.CUSTOMER)) {
                user.setRoles(Set.of(role));
                user.setPhoneNumberConfirmed(false);
                user.setTwoFactorEnable(false);
            } else if (request.getRole().equals(ModelCommon.MANAGER)) {
                //TODO
            }
            user.setActive(true);
            user.setDeleted(false);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
