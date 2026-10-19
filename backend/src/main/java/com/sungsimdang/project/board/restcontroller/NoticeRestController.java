package com.sungsimdang.project.board.restcontroller;

import com.sungsimdang.project.board.dto.NoticeRequest;
import com.sungsimdang.project.board.dto.NoticeResponse;
import com.sungsimdang.project.board.service.NoticeService;
import com.sungsimdang.project.config.LoginSession;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.domain.MemberRole;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeRestController {
    private final NoticeService noticeService;

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
    //공지사항 목록
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoticeResponse> list(){
        return noticeService.findAll();
    }

    //공지사항 검색
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<NoticeResponse> searchList(@RequestParam String type,@RequestParam String keyword){
        return noticeService.findByKeyword(type,keyword);
    }
    
    //공지사항 상세
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoticeResponse detail(@PathVariable("id") Long id){
        return noticeService.findByNotice(id);
    }
    
    //공지사항 등록 -- 로그인 되었는지, 관리자인지
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoticeResponse create(@Valid @RequestBody NoticeRequest request, HttpSession session){
        requireAdmin(session);
        Long memberId = LoginSession.memberId(session);
        return noticeService.create(request.getTitle(),request.getContent(),memberId);
    }

    //공지사항 수정
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoticeResponse update(@PathVariable("id")Long id,@Valid @RequestBody NoticeRequest request, HttpSession session){
        requireAdmin(session);
        Long memberId = LoginSession.memberId(session);
        return noticeService.update(id,request.getTitle(),request.getContent(),memberId);
    }

    //공지사항 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")Long id, HttpSession session){
        requireAdmin(session);
        noticeService.delete(id);
    }
}
