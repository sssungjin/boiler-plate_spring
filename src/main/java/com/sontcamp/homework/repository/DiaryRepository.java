package com.sontcamp.homework.repository;

import com.sontcamp.homework.domain.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query(value = "SELECT * FROM diaries WHERE MATCH(title, content) AGAINST(:keyword)",
            countQuery = "SELECT count(*) FROM diaries WHERE MATCH(title, content) AGAINST(:keyword)",
            nativeQuery = true)
    Page<Diary> findDiariesByTitleContainingOrContentContaining(String keyword, Pageable pageable);
}
