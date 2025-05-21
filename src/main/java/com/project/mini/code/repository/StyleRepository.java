package com.project.mini.code.repository;

import com.project.mini.code.entity.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StyleRepository extends JpaRepository<Style, Long> {
    @Query("SELECT s.name FROM Style s WHERE s.styleId = :styleId")
    String findNameByStyleId(Long styleId);
}
