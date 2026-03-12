package com.libmusical.api.repositories;

import com.libmusical.api.enums.MusicType;
import com.libmusical.api.models.MusicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<MusicModel, Long> {
    List<MusicModel> findByUserId(Long userId);
    List<MusicModel> findByType(MusicType type);
    List<MusicModel> findByUserIdAndType(Long userId, MusicType type);
}