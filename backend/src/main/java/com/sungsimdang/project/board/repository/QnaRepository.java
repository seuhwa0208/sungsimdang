package com.sungsimdang.project.board.repository;


import com.sungsimdang.project.board.domain.Qna;
import com.sungsimdang.project.board.domain.QnaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {
    @EntityGraph(attributePaths = "member")
    List<Qna> findAllByOrderByCreatedAtDesc(); // qna 목록 전체 보기 정렬
    @EntityGraph(attributePaths = "member")
    List<Qna> findByMemberIdOrderByCreatedAtDesc(Long memberId); // 특정 회원이 작성한 qna 목록 정렬
    @EntityGraph(attributePaths = "member")
    List<Qna> findByStatusOrderByCreatedAtDesc(QnaStatus status); // 답변 대기 중인 qna 목록 확인
}

