package com.example.rest.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "chambres")
public class RoomEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false) private String type;
  @Column(nullable = false) private BigDecimal prix;
  @Column(nullable = false) private Boolean disponible;

  public Long getId() { return id; }
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public BigDecimal getPrix() { return prix; }
  public void setPrix(BigDecimal prix) { this.prix = prix; }
  public Boolean getDisponible() { return disponible; }
  public void setDisponible(Boolean disponible) { this.disponible = disponible; }
}
