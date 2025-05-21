package com.project.mini.code.repository;

import com.project.mini.code.entity.Personality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonalityRepository extends JpaRepository<Personality, Long> {
    @Query("SELECT p.name FROM Personality p WHERE p.personalityId = :personalityId")
    String findNameByPersonalityId(Long personalityId);
}
