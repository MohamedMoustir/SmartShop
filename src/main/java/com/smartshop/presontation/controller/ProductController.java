package com.smartshop.presontation.controller;

import com.smartshop.application.service.ProductServise;
import com.smartshop.domain.model.OrderItem;
import com.smartshop.presontation.dto.Request.ProductRequest;
import com.smartshop.presontation.dto.Response.CommandeResponse;
import com.smartshop.presontation.dto.Response.OrderItemResponse;
import com.smartshop.presontation.dto.Response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServise productServise;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductRequest request) {
        ProductResponse response = productServise.createProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        List<ProductResponse> products = productServise.getAllProduct(search, page, size);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getOrderById(@PathVariable("id") Long id){
        ProductResponse response = productServise.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequest request
    ) {
        ProductResponse response = productServise.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productServise.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


}