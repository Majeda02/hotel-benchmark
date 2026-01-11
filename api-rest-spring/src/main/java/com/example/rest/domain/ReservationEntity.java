package com.example.rest.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class ReservationEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  private ClientEntity client;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "chambre_id")
  private RoomEntity chambre;

  @Column(name = "date_debut", nullable = false)
  private LocalDate dateDebut;

  @Column(name = "date_fin", nullable = false)
  private LocalDate dateFin;

  @Lob
  @Column(nullable = false)
  private String preferences;

  public Long getId() { return id; }
  public ClientEntity getClient() { return client; }
  public void setClient(ClientEntity client) { this.client = client; }
  public RoomEntity getChambre() { return chambre; }
  public void setChambre(RoomEntity chambre) { this.chambre = chambre; }
  public LocalDate getDateDebut() { return dateDebut; }
  public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
  public LocalDate getDateFin() { return dateFin; }
  public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
  public String getPreferences() { return preferences; }
  public void setPreferences(String preferences) { this.preferences = preferences; }
}
