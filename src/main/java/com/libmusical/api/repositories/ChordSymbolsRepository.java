package com.libmusical.api.repositories;

import com.libmusical.api.models.ChordSymbolsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChordSymbolsRepository extends JpaRepository<ChordSymbolsModel, Long> {
}