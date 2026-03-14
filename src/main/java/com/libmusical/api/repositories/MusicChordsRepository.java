package com.libmusical.api.repositories;

import com.libmusical.api.models.MusicChordsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicChordsRepository extends JpaRepository<MusicChordsModel, Long> {
    List<MusicChordsModel> findByMusicId(Long musicId);
}
