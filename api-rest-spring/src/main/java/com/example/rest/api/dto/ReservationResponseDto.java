package com.example.rest.api.dto;

import java.time.LocalDate;

public class ReservationResponseDto {
  public Long id;
  public ClientDto client;
  public ChambreDto chambre;
  public LocalDate dateDebut;
  public LocalDate dateFin;
  public String preferences;

  public static class ChambreDto {
    public Long id;
    public String type;
    public String prix;
    public Boolean disponible;
  }
}
