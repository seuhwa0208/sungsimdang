package com.sungsimdang.project.board.service;


import com.sungsimdang.project.board.domain.Qna;
import com.sungsimdang.project.board.domain.QnaStatus;
import com.sungsimdang.project.board.dto.QnaAnswerRequest;
import com.sungsimdang.project.board.dto.QnaRequest;
import com.sungsimdang.project.board.dto.QnaResponse;
import com.sungsimdang.project.board.repository.QnaRepository;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaService {
    private final QnaRepository qnaRepository;
    private final MemberRepository memberRepository;

    //전체 질문 및 답변의 목록
    public List<QnaResponse> findAll() {
        return qnaRepository.findAllByOrderByCreatedAtDesc().stream().map(QnaResponse::from).toList();
    }
    //내가 쓴 Q&A 목록 보기
    public List<QnaResponse> findByMyQna(Long memberId) {
        return qnaRepository.findByMemberIdOrderByCreatedAtDesc(memberId).stream().map(QnaResponse::from).toList();
    }
    //질문 및 답변 상세보기 - 옵셔널 처리
    public QnaResponse detail(Long id) {
        return QnaResponse.from(findById(id));
    }
    //질문 등록 - 회원만 가능하므로 회원인지 여부부터 판단
    @Transactional
    public QnaResponse create(Long memberId, QnaRequest request) {
        Member member = getMember(memberId);
        Qna qna = new Qna();
        qna.setMember(member);
        qna.setTitle(request.getTitle());
        qna.setContent(request.getContent());
        return QnaResponse.from(qnaRepository.save(qna));
    }
    //작성자이거나 관리자가 게시글 수정하기
    @Transactional
    public QnaResponse update(Long memberId, Long qnaId, QnaRequest request, boolean admin) {
        Qna qna = findById(qnaId);
        if(!admin && !qna.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("작성자 또는 관리자가 아닙니다.");
        }
        qna.updateQuestion(requireText(request.getTitle(), "제목"), requireText(request.getContent(), "내용"));
        return QnaResponse.from(qna);
    }
    //작성자이거나 관리자가 질문 삭제하기
    @Transactional
    public void delete(Long qnaId, Long memberId, boolean admin) {
        Qna qna = findById(qnaId);
        if(!admin && !qna.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("작성자 또는 관리자가 아닙니다.");
        }
        qnaRepository.delete(qna);
    }
    //관리자 - 답변 대기 QnA 목록 확인
    public List<QnaResponse> waitingList() {
        return qnaRepository.findByStatusOrderByCreatedAtDesc(QnaStatus.WAITING).stream().map(QnaResponse::from).toList();
    }

    //답변하기(관리자만)
    @Transactional
    public QnaResponse answer(Long qnaId, QnaAnswerRequest request) {
        Qna qna = findById(qnaId);
        if(qna.getStatus() == QnaStatus.ANSWERED) {
            throw new IllegalArgumentException("이미 등록된 답변이 존재합니다.");
        }
        qna.writeAnswer(request.getAnswer());
        return QnaResponse.from(qna);
    }
    //답변 수정(관리자만)
    @Transactional
    public QnaResponse answerUpdate(Long qnaId, QnaAnswerRequest request) {
        Qna qna = findById(qnaId);
        if(qna.getStatus() != QnaStatus.ANSWERED) {
            throw new IllegalArgumentException("등록된 답변이 없습니다.");
        }
        qna.setAnswer(request.getAnswer());
        return QnaResponse.from(qna);
    }

    //답변 삭제(관리자만)
    @Transactional
    public void answerDelete(Long qnaId) {
        Qna qna = findById(qnaId);
        if(qna.getStatus() != QnaStatus.ANSWERED) {
            throw new IllegalArgumentException("삭제할 답변이 없습니다.");
        }
        qna.deleteAnswer();
    }
    //회원 여부 판단 - 옵셔널 처리
    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }
    //Q&A 존재 여부 판단 - 옵셔널 처리
    private Qna findById(Long id) {
        return qnaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 Q&A를 찾지 못 했습니다."));
    }

    //빈 값 또는 널 여부 판단
    private String requireText(String value, String fieldName) {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + "은(는) 필수입니다.");
        }
        return value.trim();
    }

    //조회 수 증가
    @Transactional
    public QnaResponse increaseHits(Long id) {
        Qna qna = findById(id);
        qna.setHits(qna.getHits() + 1);
        return QnaResponse.from(qna);
    }
}
