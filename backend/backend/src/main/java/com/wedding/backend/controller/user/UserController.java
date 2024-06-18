package com.wedding.backend.controller.user;

import com.wedding.backend.dto.user.UserDTO;
import com.wedding.backend.service.IService.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final IUserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getUser(@RequestParam String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam String userId,
                                        @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(userId, userDTO));
    }
}
