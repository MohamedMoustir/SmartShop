package com.smartshop.application.mapper;

import com.smartshop.domain.model.Product;
import com.smartshop.presontation.dto.Request.ProductRequest;
import com.smartshop.presontation.dto.Response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductRequest product);

    ProductResponse toResponse(Product dto);
}

