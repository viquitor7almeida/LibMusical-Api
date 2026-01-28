package com.libmusical.api.repositories;

import com.libmusical.api.models.VersesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VersesRepository extends JpaRepository<VersesModel, Long> {
    List<VersesModel> findByMusicId(Long musicId);
    
}