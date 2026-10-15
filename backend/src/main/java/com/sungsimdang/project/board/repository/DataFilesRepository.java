package com.sungsimdang.project.board.repository;

import com.sungsimdang.project.board.domain.DataFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataFilesRepository extends JpaRepository<DataFiles, Long> {
    List<DataFiles> findAllByOrderByCreatedAtDesc();
    List<DataFiles> findByTitleContainingOrderByCreatedAtDesc(String title);
    List<DataFiles> findByContentContainingOrderByCreatedAtDesc(String content);
    List<DataFiles> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(String title, String content);
}
