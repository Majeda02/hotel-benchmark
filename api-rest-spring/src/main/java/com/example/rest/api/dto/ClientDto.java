package com.example.rest.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClientDto {
  public Long id;

  @NotBlank public String nom;
  @NotBlank public String prenom;
  @Email @NotBlank public String email;
  @NotBlank public String telephone;
}
