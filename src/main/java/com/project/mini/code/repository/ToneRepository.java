package com.project.mini.code.repository;

import com.project.mini.code.entity.Tone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ToneRepository extends JpaRepository<Tone, Long> {
    @Query("SELECT t.name FROM Tone t WHERE t.toneId = :toneId")
    String findNameByToneId(Long toneId);
}
