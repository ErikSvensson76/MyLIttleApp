package org.example.mylittleapp.service.repos;

import org.example.mylittleapp.model.entity.EntityBinary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EntityBinaryRepo extends JpaRepository<EntityBinary, String> {
  @Query("SELECT b FROM EntityBinary b " +
      "WHERE lower(b.fileName) = :fileName")
  Optional<EntityBinary> findByFileName(String fileName);
}