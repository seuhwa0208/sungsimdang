package com.sungsimdang.project.board.repository;

import com.sungsimdang.project.board.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findAllByOrderByCreatedAtDesc(); // qna 목록 전체 보기 정렬
    List<Qna> findByMemberIdOrderByCreatedAtDesc(Long memberId); // 특정 회원이 작성한 qna 목록 정렬
}
