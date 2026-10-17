package com.sungsimdang.project.board.restcontroller;

import com.sungsimdang.project.board.dto.NoticeRequest;
import com.sungsimdang.project.board.dto.NoticeResponse;
import com.sungsimdang.project.board.service.NoticeService;
import com.sungsimdang.project.config.LoginSession;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeRestController {
    private final NoticeService noticeService;

    //공지사항 목록
    @GetMapping
    public List<NoticeResponse> list(){
        return noticeService.findAll();
    }

    //공지사항 검색
    @GetMapping("/search")
    public List<NoticeResponse> searchList(@RequestParam String type,@RequestParam String keyword){
        return noticeService.findByKeyword(type,keyword);
    }
    
    //공지사항 상세
    @GetMapping("/{id}")
    public NoticeResponse detail(@PathVariable("id") Long id){
        return noticeService.findByNotice(id);
    }
    
    // 공지사항 등록 수정 삭제는 관리자만 가능 - 권한 검사
    public void checkLoginAndAdmin(HttpSession session){
        if (!LoginSession.isLoggedIn(session)) {throw new AccessDeniedException("로그인을 해주세요.");}
        if (!LoginSession.isAdmin(session)) {throw new AccessDeniedException("잘못된 접근 입니다.");}
    }
    
    //공지사항 등록 -- 로그인 되었는지, 관리자인지
    @PostMapping
    public NoticeResponse create(@Valid @RequestBody NoticeRequest request, HttpSession session){
        checkLoginAndAdmin(session);
        Long memberId = LoginSession.memberId(session);
        return noticeService.create(request.getTitle(),request.getContent(),memberId);
    }

    //공지사항 수정
    @PutMapping("/{id}")
    public NoticeResponse update(@PathVariable("id")Long id,@Valid @RequestBody NoticeRequest request, HttpSession session){
        checkLoginAndAdmin(session);
        Long memberId = LoginSession.memberId(session);
        return noticeService.update(id,request.getTitle(),request.getContent(),memberId);
    }

    //공지사항 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")Long id, HttpSession session){
        checkLoginAndAdmin(session);
        noticeService.delete(id);
    }
}
