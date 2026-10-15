package com.sungsimdang.project.board.restcontroller;

import com.sungsimdang.project.board.dto.NoticeRequest;
import com.sungsimdang.project.board.dto.NoticeResponse;
import com.sungsimdang.project.board.service.NoticeService;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.domain.MemberRole;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeRestController {
    private final NoticeService noticeService;

    //관리자 여부 확인
    private boolean isAdmin(HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");
        return member != null && member.getRole() == MemberRole.ADMIN;
    }

    //공지사항 목록
    @GetMapping("/")
    public List<NoticeResponse> list(){
        return noticeService.findAll();
    }
    
    //공지사항 상세
    @GetMapping("/{id}")
    public NoticeResponse detail(@PathVariable("id") Long id){
        return noticeService.findByNotice(id);
    }
    
    //공지사항 등록 -- 관리자 인지 확인해야함 - 서비스에 있음
    @PostMapping("/")
    public NoticeResponse create(@Valid @RequestBody NoticeRequest request, HttpSession session){
        if (!isAdmin(session)) throw new AccessDeniedException("잘못된 접근 입니다.");
        String loginId = (String) session.getAttribute("loginId");
        return noticeService.create(request.getTitle(),request.getContent(),loginId);
    }

    //공지사항 수정
    @PutMapping("/{id}")
    public NoticeResponse update(@PathVariable("id")Long id, @RequestBody NoticeRequest request, HttpSession session){
        if (!isAdmin(session)) throw new AccessDeniedException("잘못된 접근 입니다.");
        String loginId = (String) session.getAttribute("loginId");
        return noticeService.update(id,request.getTitle(),request.getContent(),loginId);
    }

    //공지사항 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")Long id, HttpSession session){
        if (!isAdmin(session)) throw new AccessDeniedException("잘못된 접근 입니다.");
        noticeService.delete(id);
    }
}
