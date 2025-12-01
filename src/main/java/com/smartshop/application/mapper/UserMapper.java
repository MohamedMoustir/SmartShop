package com.smartshop.application.mapper;

import com.smartshop.domain.model.Client;
import com.smartshop.domain.model.User;
import com.smartshop.presontation.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);
    Client toEntity(UserDTO dto);
}