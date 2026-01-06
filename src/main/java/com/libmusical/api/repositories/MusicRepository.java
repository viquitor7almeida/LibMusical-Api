package com.libmusical.api.repositories;

import com.libmusical.api.models.MusicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<MusicModel, Long> {
    Optional<MusicModel> findByUserId(Long userId);
}