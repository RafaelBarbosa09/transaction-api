package com.infnet.transactionapi.application.controllers;

import com.infnet.transactionapi.application.dto.SellerDTO;
import com.infnet.transactionapi.application.services.SellerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Sellers")
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
            return ResponseEntity.status(HttpStatus.OK).body(sellers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSellerById(@PathVariable Long id) {
        try {
            SellerDTO seller = sellerService.getSellerById(id);
            return ResponseEntity.status(HttpStatus.OK).body(seller);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createSeller(@RequestBody SellerDTO sellerDTO) {
        try {
            SellerDTO seller = sellerService.createSeller(sellerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(seller);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSeller(@RequestBody SellerDTO sellerDTO, @PathVariable Long id) {
        try {
            SellerDTO seller = sellerService.updateSeller(id, sellerDTO);
            return ResponseEntity.status(HttpStatus.OK).body(seller);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }
}
