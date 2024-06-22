package com.wedding.backend.controller.service;

import com.wedding.backend.service.IService.service.IService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/service")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ServiceController {
    private final IService service;

    /*
    * Find all service with condition is deleted is false and status is APPROVED
    * */
    @GetMapping("/getAllByDeleted")
    public ResponseEntity<?> getAllByFalseDeletedAndAcceptStatus(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                    @RequestParam(name = "size", required = false, defaultValue = "5") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAllByFalseDeletedAndAcceptStatus(pageable));
    }
}
