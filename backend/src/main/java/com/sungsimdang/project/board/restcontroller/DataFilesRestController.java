package com.sungsimdang.project.board.restcontroller;

import com.sungsimdang.project.board.dto.DataFilesRequest;
import com.sungsimdang.project.board.dto.DataFilesResponse;
import com.sungsimdang.project.board.service.DataFilesService;
import com.sungsimdang.project.config.LoginSession;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/datafiles")
public class DataFilesRestController {
    private final DataFilesService dataFilesService;

    //자료실 목록
    @GetMapping
    public List<DataFilesResponse> list(){
        return dataFilesService.findAll();
    }

    //자료실 검색
    @GetMapping("/search")
    public List<DataFilesResponse> searchList(@RequestParam String type, @RequestParam String keyword){
        return dataFilesService.findByKeyword(type,keyword);
    }

    //자료실 상세
    @GetMapping("/{id}")
    public DataFilesResponse detail(@PathVariable("id") Long id){
        return dataFilesService.findByDataFiles(id);
    }

    //공지사항 등록 -- 로그인 되었는지, 관리자인지
    @PostMapping
    public DataFilesResponse create(@Valid @RequestBody DataFilesRequest request, HttpSession session){
        if (!LoginSession.isLoggedIn(session)) throw new AccessDeniedException("로그인을 해주세요.");
        if (!LoginSession.isAdmin(session)) throw new AccessDeniedException("잘못된 접근 입니다.");
        Long memberId = LoginSession.memberId(session);
        return dataFilesService.create(request.getTitle(),request.getContent(),memberId);
    }

    //공지사항 수정
    @PutMapping("/{id}")
    public DataFilesResponse update(@PathVariable("id")Long id,@Valid @RequestBody DataFilesRequest request, HttpSession session){
        if (!LoginSession.isLoggedIn(session)) throw new AccessDeniedException("로그인을 해주세요.");
        if (!LoginSession.isAdmin(session)) throw new AccessDeniedException("잘못된 접근 입니다.");
        Long memberId = LoginSession.memberId(session);
        return dataFilesService.update(id,request.getTitle(),request.getContent(),memberId);
    }

    //공지사항 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")Long id, HttpSession session){
        if (!LoginSession.isLoggedIn(session)) throw new AccessDeniedException("로그인을 해주세요.");
        if (!LoginSession.isAdmin(session)) throw new AccessDeniedException("잘못된 접근 입니다.");
        dataFilesService.delete(id);
    }
}
