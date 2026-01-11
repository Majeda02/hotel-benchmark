package com.example.rest.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class ReservationCreateDto {
  @Valid @NotNull public ClientDto client;

  @NotNull @Positive public Long chambreId;

  @NotNull public LocalDate dateDebut;
  @NotNull public LocalDate dateFin;

  @NotNull @Size(min = 1) public String preferences;
}
