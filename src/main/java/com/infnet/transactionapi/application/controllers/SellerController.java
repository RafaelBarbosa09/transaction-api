package com.infnet.transactionapi.application.controllers;

import com.infnet.transactionapi.application.DTO.SellerDTO;
import com.infnet.transactionapi.application.services.SellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping()
    public ResponseEntity<Object> getAllSellers() {
        try {
            List<SellerDTO> sellers = sellerService.getAllSellers();
            return ResponseEntity.ok(sellers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
