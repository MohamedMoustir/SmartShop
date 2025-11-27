package com.smartshop.application.service;

import com.smartshop.application.mapper.ProductMapper;
import com.smartshop.domain.Excption.InvalidCredentialsException;
import com.smartshop.domain.model.Product;
import com.smartshop.infrastructuer.Repository.CommandeRepository;
import com.smartshop.infrastructuer.Repository.OrderItemRepository;
import com.smartshop.infrastructuer.Repository.ProductRepository;
import com.smartshop.presontation.dto.Request.ProductRequest;
import com.smartshop.presontation.dto.Response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.smartshop.application.mapper.ProductMapper.toResponse;

@Service
@RequiredArgsConstructor
public class ProductServise {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;


    public ProductResponse createProduct(ProductRequest request){
        Product productEntity = ProductMapper.toEntity(request);
        Product product = productRepository.save(productEntity);
        return toResponse(product);
    }

    public List<ProductResponse> getAllProduct(String nom , int page, int size){
        PageRequest pageable = PageRequest.of(page, size);

        Page<Product> productPage;
        if (nom == null || nom.trim().isEmpty() || "all".equalsIgnoreCase(nom)) {
            productPage = productRepository.findAll(pageable);
        } else {
            productPage = productRepository.findByNomContaining(nom, pageable);
        }
        return productPage.getContent().stream().map(ProductMapper::toResponse).collect(Collectors.toList());

    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product introuvable avec l'id : " + id));
          Boolean existsInOrder = orderItemRepository.existsByProduct_Id(id);
        if(!existsInOrder){
            product.setDeleted(true);
            productRepository.save(product);
        }else{
            productRepository.delete(product);

        }


    }

    public ProductResponse updateProduct(Long id ,ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (request.getNom() == null || request.getPrixUnitaire() == null) {
            throw new IllegalArgumentException("Les informations du produit sont incomplètes.");
        }
            product.setNom(request.getNom());
            product.setPrixUnitaire(request.getPrixUnitaire());
         if(request.getStockDisponible() != null) {
            product.setStockDisponible(request.getStockDisponible());
        }

        Product product1 = productRepository.save(product);
        return toResponse(product1);
    }
}
