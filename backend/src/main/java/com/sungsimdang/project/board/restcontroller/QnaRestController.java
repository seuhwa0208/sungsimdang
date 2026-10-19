package com.sungsimdang.project.board.restcontroller;

import com.sungsimdang.project.board.dto.QnaAnswerRequest;
import com.sungsimdang.project.board.dto.QnaRequest;
import com.sungsimdang.project.board.dto.QnaResponse;
import com.sungsimdang.project.board.service.QnaService;
import com.sungsimdang.project.config.LoginSession;
import com.sungsimdang.project.member.domain.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class QnaRestController {
    private final QnaService qnaService;

    //로그인 여부 확인
    private boolean isLogin(HttpSession session) {
        return LoginSession.isLoggedIn(session);
    }

    //관리자 여부 확인
    private boolean isAdmin(HttpSession session) {
        return LoginSession.isAdmin(session);
    }
    //로그인한 회원이 관리자인지 확인하는 것 - 에러 코드 분류용
    private void requireAdmin(HttpSession session) {
        if (!isLogin(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        if (!isAdmin(session)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
    }

    //Q&A 목록
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<QnaResponse> list() {
        return qnaService.findAll();
    }

    // Q&A 상세 보기 + 조회 수 중복 증가 방지
    @GetMapping("/{qnaId}")
    @ResponseStatus(HttpStatus.OK)
    public QnaResponse detail(@PathVariable("qnaId") Long qnaId,
                              @CookieValue(value = "viewedQnas", defaultValue = "") String viewedQnas,
                              HttpServletResponse response) {
        String qnaKey = "_" + qnaId + "_";
        boolean firstView = !viewedQnas.contains(qnaKey);
        QnaResponse result;
        if (firstView) {
            // 최초 조회: 조회 수 증가
            result = qnaService.increaseHits(qnaId);
            Cookie cookie = new Cookie("viewedQnas", viewedQnas + qnaKey);
            cookie.setMaxAge(60 * 60 * 24);
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            // 이미 조회한 게시글: 조회 수 증가 없이 상세 조회
            result = qnaService.detail(qnaId);
        }
        return result;
    }
    //마이 Q&A
    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public List<QnaResponse> myQna(HttpSession session) {
        if(!isLogin(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        Member member = (Member) session.getAttribute("loginMember");
        return qnaService.findByMyQna(member.getId());
    }

    //Q&A 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QnaResponse create(@Valid @RequestBody QnaRequest request, HttpSession session) {
        if(!isLogin(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        Member member = (Member) session.getAttribute("loginMember");
        return qnaService.create(member.getId(), request);
    }

    //Q&A 수정 - 관리자 || 작성자
    @PutMapping("/{qnaId}")
    @ResponseStatus(HttpStatus.OK)
    public QnaResponse update(@PathVariable("qnaId") Long qnaId, @Valid @RequestBody QnaRequest request, HttpSession session) {
        if (!isLogin(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        Member member = (Member) session.getAttribute("loginMember");
        boolean admin = isAdmin(session);
        return qnaService.update(member.getId(), qnaId, request, admin);
    }
    //Q&A 삭제 - 관리자 || 작성자
    @DeleteMapping("/{qnaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("qnaId") Long qnaId, HttpSession session) {
        if(!isLogin(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        boolean admin = isAdmin(session);
        Member member = (Member) session.getAttribute("loginMember");
        qnaService.delete(qnaId, member.getId(), admin);
    }

    //답변 대기 중인 Q&A 목록 보기
    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public List<QnaResponse> waitingList(HttpSession session) {
        requireAdmin(session);
        return qnaService.waitingList();
    }
    //답변 등록
    @PostMapping("/admin/{qnaId}")
    @ResponseStatus(HttpStatus.CREATED)
    public QnaResponse answer(@PathVariable("qnaId")Long qnaId, @Valid @RequestBody QnaAnswerRequest request, HttpSession session) {
        requireAdmin(session);
        return qnaService.answer(qnaId, request);
    }

    //답변 수정
    @PutMapping("/admin/{qnaId}")
    @ResponseStatus(HttpStatus.OK)
    public QnaResponse answerUpdate(@PathVariable("qnaId")Long qnaId, @Valid @RequestBody QnaAnswerRequest request, HttpSession session) {
        requireAdmin(session);
        return qnaService.answerUpdate(qnaId, request);
    }

    //답변 삭제
    @DeleteMapping("/admin/{qnaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void answerDelete(@PathVariable("qnaId")Long qnaId, HttpSession session) {
        requireAdmin(session);
        qnaService.answerDelete(qnaId);
    }
}