package com.project.mini.code.repository;

import com.project.mini.code.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContentRepository extends JpaRepository<Content, Long> {
    @Query("SELECT c.name FROM Content c WHERE c.contentId = :contentId")
    String findNameByContentId(Long contentId);
}
