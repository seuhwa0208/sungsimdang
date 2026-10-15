package com.sungsimdang.project.board.repository;

import com.sungsimdang.project.board.domain.DataFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataFilesRepository extends JpaRepository<DataFiles, Long> {
}
