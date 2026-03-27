  package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.Mapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Product p) {

        Product saved = service.create(p);

        return ResponseEntity.status(201)
                .body(new ApiResponse<>("Product created", Mapper.toProductDTO(saved)));
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<?> getAll() {

        List<ProductDTO> list = service.getAll()
                .stream()
                .map(Mapper::toProductDTO)
                .toList();

        return ResponseEntity.ok(new ApiResponse<>("Product list", list));
    }

    // ✅ GET ONE
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {

        Product product = service.getById(id);

        return ResponseEntity.ok(
                new ApiResponse<>("Product found", Mapper.toProductDTO(product))
        );
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product p) {

        Product updated = service.update(id, p);

        return ResponseEntity.ok(
                new ApiResponse<>("Product updated", Mapper.toProductDTO(updated))
        );
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok(
                new ApiResponse<>("Product deleted", null)
        );
    }
}