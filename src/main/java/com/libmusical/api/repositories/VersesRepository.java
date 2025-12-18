package com.libmusical.api.repositories;

import com.libmusical.api.models.VersesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersesRepository extends JpaRepository<VersesModel, Long> {
}