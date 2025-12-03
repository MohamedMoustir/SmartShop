package com.smartshop.presontation.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientRequest {
    public interface OnCreate {}
    public interface OnUpdate {}
    @NotBlank(message = "Le nom est obligatoire", groups = {OnCreate.class, OnUpdate.class})
    private String nom;

    @Email(message = "Email invalide", groups = {OnCreate.class, OnUpdate.class})
    @NotBlank(message = "Email obligatoire", groups = {OnCreate.class, OnUpdate.class})
    private String email;

    private String role;

    @NotBlank(message = "Mot de passe obligatoire", groups = OnCreate.class)
    private String password;

}