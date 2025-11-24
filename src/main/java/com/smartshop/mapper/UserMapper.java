    package com.smartshop.mapper;

    import com.smartshop.dto.UserDTO;
    import com.smartshop.model.Client;
    import com.smartshop.model.User;

    public class UserMapper {

        public static UserDTO toDto(User user){
            if (user == null) return null;
            return UserDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .nom(user.getNom())
                    .role(user.getRole())
                    .build();
        }

        public static Client toEntity(UserDTO dto){
            if (dto == null) return null;
              return Client.builder()
                      .id(dto.getId())
                      .email(dto.getEmail())
                      .nom(dto.getNom())
                      .role(dto.getRole())
                      .build();
        }
    }
