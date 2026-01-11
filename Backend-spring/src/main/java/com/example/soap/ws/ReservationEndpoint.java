package com.example.soap.ws;

import com.example.soap.domain.*;
import com.example.soap.repo.*;
import org.springframework.ws.server.endpoint.annotation.*;

import java.util.NoSuchElementException;

@Endpoint
public class ReservationEndpoint {

  private static final String NS = "http://example.com/reservations";

  private final ClientRepository clients;
  private final RoomRepository rooms;
  private final ReservationRepository reservations;

  public ReservationEndpoint(ClientRepository clients, RoomRepository rooms, ReservationRepository reservations) {
    this.clients = clients;
    this.rooms = rooms;
    this.reservations = reservations;
  }

  @PayloadRoot(namespace = NS, localPart = "createReservationRequest")
  @ResponsePayload
  public CreateReservationResponse create(@RequestPayload CreateReservationRequest req) {
    ClientEntity client = clients.findByEmail(req.getClient().getEmail()).orElseGet(() -> {
      ClientEntity c = new ClientEntity();
      c.setNom(req.getClient().getNom());
      c.setPrenom(req.getClient().getPrenom());
      c.setEmail(req.getClient().getEmail());
      c.setTelephone(req.getClient().getTelephone());
      return clients.save(c);
    });

    RoomEntity room = rooms.findById(req.getChambreId()).orElseThrow(() -> new NoSuchElementException("Room not found"));

    ReservationEntity r = new ReservationEntity();
    r.setClient(client);
    r.setChambre(room);
    r.setDateDebut(req.getDateDebut().toGregorianCalendar().toZonedDateTime().toLocalDate());
    r.setDateFin(req.getDateFin().toGregorianCalendar().toZonedDateTime().toLocalDate());
    r.setPreferences(req.getPreferences());

    ReservationEntity saved = reservations.save(r);

    CreateReservationResponse resp = new CreateReservationResponse();
    resp.setReservation(SoapMapper.toSoap(saved));
    return resp;
  }

  @PayloadRoot(namespace = NS, localPart = "getReservationRequest")
  @ResponsePayload
  public GetReservationResponse get(@RequestPayload GetReservationRequest req) {
    ReservationEntity r = reservations.findById(req.getId()).orElseThrow(() -> new NoSuchElementException("Reservation not found"));
    GetReservationResponse resp = new GetReservationResponse();
    resp.setReservation(SoapMapper.toSoap(r));
    return resp;
  }

  @PayloadRoot(namespace = NS, localPart = "updateReservationRequest")
  @ResponsePayload
  public UpdateReservationResponse update(@RequestPayload UpdateReservationRequest req) {
    ReservationEntity r = reservations.findById(req.getId()).orElseThrow(() -> new NoSuchElementException("Reservation not found"));

    ClientEntity client = clients.findByEmail(req.getClient().getEmail()).orElseGet(() -> {
      ClientEntity c = new ClientEntity();
      c.setNom(req.getClient().getNom());
      c.setPrenom(req.getClient().getPrenom());
      c.setEmail(req.getClient().getEmail());
      c.setTelephone(req.getClient().getTelephone());
      return clients.save(c);
    });

    RoomEntity room = rooms.findById(req.getChambreId()).orElseThrow(() -> new NoSuchElementException("Room not found"));

    r.setClient(client);
    r.setChambre(room);
    r.setDateDebut(req.getDateDebut().toGregorianCalendar().toZonedDateTime().toLocalDate());
    r.setDateFin(req.getDateFin().toGregorianCalendar().toZonedDateTime().toLocalDate());
    r.setPreferences(req.getPreferences());

    ReservationEntity saved = reservations.save(r);

    UpdateReservationResponse resp = new UpdateReservationResponse();
    resp.setReservation(SoapMapper.toSoap(saved));
    return resp;
  }

  @PayloadRoot(namespace = NS, localPart = "deleteReservationRequest")
  @ResponsePayload
  public DeleteReservationResponse delete(@RequestPayload DeleteReservationRequest req) {
    reservations.deleteById(req.getId());
    DeleteReservationResponse resp = new DeleteReservationResponse();
    resp.setDeleted(true);
    return resp;
  }
}
