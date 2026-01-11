package com.example.rest.repo;

import com.example.rest.domain.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
  Optional<ClientEntity> findByEmail(String email);
}
