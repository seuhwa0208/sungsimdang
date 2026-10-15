package com.sungsimdang.project.board.service;

import com.sungsimdang.project.board.Qna;
import com.sungsimdang.project.board.QnaStatus;
import com.sungsimdang.project.board.dto.QnaAnswerRequest;
import com.sungsimdang.project.board.dto.QnaRequest;
import com.sungsimdang.project.board.dto.QnaResponse;
import com.sungsimdang.project.board.repository.QnaRepository;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.domain.MemberRole;
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
    public List<Qna> findAll() {
        return qnaRepository.findAllByOrderByCreatedAtDesc();
    }
    //특정 회원의 질문 및 답변 목록
    public List<Qna> findByMyQna(Long memberId) {
        return qnaRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
    }
    //질문 및 답변 상세보기 - 옵셔널 처리
    public Qna findById(Long id) {
        return qnaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 Q&A를 찾지 못 했습니다."));
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
    //자신이 작성한 게시글 수정하기
    @Transactional
    public QnaResponse update(Long memberId, Long qnaId, QnaRequest request) {
        Qna qna = findById(qnaId);
        getMember(memberId);
        qna.updateQuestion(requireText(request.getTitle(), "제목"), requireText(request.getContent(), "내용"));
        return QnaResponse.from(qna);
    }
    //자신이 작성한 질문 삭제하기
    @Transactional
    public void delete(Long qnaId, Long memberId) {
        getMember(memberId);
        Qna qna = findById(qnaId);
        qnaRepository.delete(qna);
    }
    //답변하기(관리자만)
    @Transactional
    public QnaResponse answer(Long qnaId, QnaAnswerRequest request) {
        Qna qna = findById(qnaId);
        qna.writeAnswer(request.getAnswer());
        return QnaResponse.from(qna);
    }
    //답변 수정(관리자만)
    @Transactional
    public Qna answerUpdate(Long qnaId, QnaAnswerRequest request) {
        Qna qna = findById(qnaId);
        if(qna.getStatus() != QnaStatus.ANSWERED) {
            throw new IllegalArgumentException("등록된 답변이 없습니다.");
        }
        return qna;
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
//    //관리자 계정 여부 판단
//    private void checkAdmin(Member member) {
//        if(member.getRole() != MemberRole.ADMIN) {
//            throw new IllegalArgumentException("관리자 권한이 필요합니다.");
//        }
//    }
    //본인이 작성한 Qna이거나 관리자인지 여부 판단
    private void checkOwnerOrAdmin(Qna qna, Long memberId, boolean admin) {
        if(!admin && !qna.getMember().getId().equals(memberId)) {
            throw new SecurityException("본인이 작성한 Q&A만 수정하거나 삭제할 수 있습니다.");
        }
    }
    //빈 값 또는 널 여부 판단
    private String requireText(String value, String fieldName) {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + "은(는) 필수입니다.");
        }
        return value.trim();
    }
}
