package com.example.rest.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class ReservationUpdateDto {
  @Valid @NotNull public ClientDto client;
  @NotNull public LocalDate dateDebut;
  @NotNull public LocalDate dateFin;
  @NotNull @Size(min = 1) public String preferences;
  @NotNull public Long chambreId;
}
