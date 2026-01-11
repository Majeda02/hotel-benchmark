package com.example.grpc.service;

import com.example.grpc.domain.ReservationEntity;
import com.example.grpc.proto.*;

public final class GrpcMapper {
  private GrpcMapper() {}

  public static Reservation toProto(ReservationEntity e) {
    Client c = Client.newBuilder()
        .setId(e.getClient().getId())
        .setNom(e.getClient().getNom())
        .setPrenom(e.getClient().getPrenom())
        .setEmail(e.getClient().getEmail())
        .setTelephone(e.getClient().getTelephone())
        .build();

    Chambre ch = Chambre.newBuilder()
        .setId(e.getChambre().getId())
        .setType(e.getChambre().getType())
        .setPrix(e.getChambre().getPrix().toPlainString())
        .setDisponible(Boolean.TRUE.equals(e.getChambre().getDisponible()))
        .build();

    return Reservation.newBuilder()
        .setId(e.getId())
        .setClient(c)
        .setChambre(ch)
        .setDateDebut(e.getDateDebut().toString())
        .setDateFin(e.getDateFin().toString())
        .setPreferences(e.getPreferences())
        .build();
  }
}
