package com.example.grpc.service;

import com.example.grpc.domain.*;
import com.example.grpc.proto.*;
import com.example.grpc.repo.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@GrpcService
public class ReservationGrpcService extends ReservationServiceGrpc.ReservationServiceImplBase {

  private final ClientRepository clients;
  private final RoomRepository rooms;
  private final ReservationRepository reservations;

  public ReservationGrpcService(ClientRepository clients, RoomRepository rooms, ReservationRepository reservations) {
    this.clients = clients;
    this.rooms = rooms;
    this.reservations = reservations;
  }

  @Override
  public void createReservation(CreateReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
    ReservationEntity saved = createOrUpdate(null, request.getClient(), request.getChambreId(),
        request.getDateDebut(), request.getDateFin(), request.getPreferences());

    ReservationResponse resp = ReservationResponse.newBuilder()
        .setReservation(GrpcMapper.toProto(saved))
        .build();

    responseObserver.onNext(resp);
    responseObserver.onCompleted();
  }

  @Override
  public void getReservation(GetReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
    ReservationEntity r = reservations.findById(request.getId()).orElseThrow(() -> new NoSuchElementException("Reservation not found"));
    responseObserver.onNext(ReservationResponse.newBuilder().setReservation(GrpcMapper.toProto(r)).build());
    responseObserver.onCompleted();
  }

  @Override
  public void updateReservation(UpdateReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
    ReservationEntity saved = createOrUpdate(request.getId(), request.getClient(), request.getChambreId(),
        request.getDateDebut(), request.getDateFin(), request.getPreferences());

    responseObserver.onNext(ReservationResponse.newBuilder().setReservation(GrpcMapper.toProto(saved)).build());
    responseObserver.onCompleted();
  }

  @Override
  public void deleteReservation(DeleteReservationRequest request, StreamObserver<DeleteReservationResponse> responseObserver) {
    reservations.deleteById(request.getId());
    responseObserver.onNext(DeleteReservationResponse.newBuilder().setDeleted(true).build());
    responseObserver.onCompleted();
  }

  private ReservationEntity createOrUpdate(Long id, Client clientMsg, long chambreId, String dateDebut, String dateFin, String preferences) {
    ClientEntity client = clients.findByEmail(clientMsg.getEmail()).orElseGet(() -> {
      ClientEntity c = new ClientEntity();
      c.setNom(clientMsg.getNom());
      c.setPrenom(clientMsg.getPrenom());
      c.setEmail(clientMsg.getEmail());
      c.setTelephone(clientMsg.getTelephone());
      return clients.save(c);
    });

    RoomEntity room = rooms.findById(chambreId).orElseThrow(() -> new NoSuchElementException("Room not found"));

    ReservationEntity r = (id == null)
        ? new ReservationEntity()
        : reservations.findById(id).orElseThrow(() -> new NoSuchElementException("Reservation not found"));

    r.setClient(client);
    r.setChambre(room);
    r.setDateDebut(LocalDate.parse(dateDebut));
    r.setDateFin(LocalDate.parse(dateFin));
    r.setPreferences(preferences);

    return reservations.save(r);
  }
}
