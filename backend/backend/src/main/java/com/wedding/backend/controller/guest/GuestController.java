package com.wedding.backend.controller.guest;

import com.wedding.backend.service.IService.service.IService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/guest")
@CrossOrigin("*")
@RequiredArgsConstructor
public class GuestController {
    private final IService service;

    @GetMapping("/albByName")
    public ResponseEntity<?> getAlbumOfServiceByNameAlb(@RequestParam Long serviceId, @RequestParam String albName) {
        return ResponseEntity.ok(service.getAlbumOfServiceByNameAlb(serviceId, albName));
    }
}
