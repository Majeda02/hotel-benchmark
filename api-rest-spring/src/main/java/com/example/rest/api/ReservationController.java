package com.example.rest.api;

import com.example.rest.api.dto.*;
import com.example.rest.domain.*;
import com.example.rest.repo.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  private final ClientRepository clients;
  private final RoomRepository rooms;
  private final ReservationRepository reservations;

  public ReservationController(ClientRepository clients, RoomRepository rooms, ReservationRepository reservations) {
    this.clients = clients;
    this.rooms = rooms;
    this.reservations = reservations;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ReservationResponseDto create(@Valid @RequestBody ReservationCreateDto req) {
    ClientEntity client = clients.findByEmail(req.client.email).orElseGet(() -> {
      ClientEntity c = new ClientEntity();
      c.setNom(req.client.nom);
      c.setPrenom(req.client.prenom);
      c.setEmail(req.client.email);
      c.setTelephone(req.client.telephone);
      return clients.save(c);
    });

    RoomEntity room = rooms.findById(req.chambreId).orElseThrow(() -> new NoSuchElementException("Room not found"));

    ReservationEntity r = new ReservationEntity();
    r.setClient(client);
    r.setChambre(room);
    r.setDateDebut(req.dateDebut);
    r.setDateFin(req.dateFin);
    r.setPreferences(req.preferences);

    return toDto(reservations.save(r));
  }

  @GetMapping("/{id}")
  public ReservationResponseDto get(@PathVariable Long id) {
    return toDto(reservations.findById(id).orElseThrow(() -> new NoSuchElementException("Reservation not found")));
  }

  @PutMapping("/{id}")
  public ReservationResponseDto update(@PathVariable Long id, @Valid @RequestBody ReservationUpdateDto req) {
    ReservationEntity r = reservations.findById(id).orElseThrow(() -> new NoSuchElementException("Reservation not found"));

    ClientEntity client = clients.findByEmail(req.client.email).orElseGet(() -> {
      ClientEntity c = new ClientEntity();
      c.setNom(req.client.nom);
      c.setPrenom(req.client.prenom);
      c.setEmail(req.client.email);
      c.setTelephone(req.client.telephone);
      return clients.save(c);
    });

    RoomEntity room = rooms.findById(req.chambreId).orElseThrow(() -> new NoSuchElementException("Room not found"));

    r.setClient(client);
    r.setChambre(room);
    r.setDateDebut(req.dateDebut);
    r.setDateFin(req.dateFin);
    r.setPreferences(req.preferences);

    return toDto(reservations.save(r));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    reservations.deleteById(id);
  }

  private static ReservationResponseDto toDto(ReservationEntity e) {
    ReservationResponseDto dto = new ReservationResponseDto();
    dto.id = e.getId();

    ClientDto c = new ClientDto();
    c.id = e.getClient().getId();
    c.nom = e.getClient().getNom();
    c.prenom = e.getClient().getPrenom();
    c.email = e.getClient().getEmail();
    c.telephone = e.getClient().getTelephone();
    dto.client = c;

    ReservationResponseDto.ChambreDto ch = new ReservationResponseDto.ChambreDto();
    ch.id = e.getChambre().getId();
    ch.type = e.getChambre().getType();
    ch.prix = e.getChambre().getPrix().toPlainString();
    ch.disponible = e.getChambre().getDisponible();
    dto.chambre = ch;

    dto.dateDebut = e.getDateDebut();
    dto.dateFin = e.getDateFin();
    dto.preferences = e.getPreferences();
    return dto;
  }
}
