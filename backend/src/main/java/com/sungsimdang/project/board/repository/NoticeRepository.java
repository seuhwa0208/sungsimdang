package com.sungsimdang.project.board.repository;

import com.sungsimdang.project.board.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByCreatedAtDesc();
    List<Notice> findByTitleContainingOrderByCreatedAtDesc(String title);
    List<Notice> findByContentContainingOrderByCreatedAtDesc(String content);
    List<Notice> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(String title, String content);
}
