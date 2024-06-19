package com.wedding.backend.dto.customer.wishlist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wishlist")
@CrossOrigin("*")
public class WishListController {
    @GetMapping("/get")
    public ResponseEntity<?> getWishList(Principal connectedUser){
        return null;
    }
}
