package com.smartshop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank(message = "Le email d'utilisateur est requis.")
    private String email;

    @NotBlank(message = "Le mod de pass est requis.")
    private String password;

}
