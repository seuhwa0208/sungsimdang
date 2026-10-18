package com.sungsimdang.project.board.restcontroller;

import com.sungsimdang.project.board.dto.DataFilesRequest;
import com.sungsimdang.project.board.dto.DataFilesResponse;
import com.sungsimdang.project.board.service.DataFilesService;
import com.sungsimdang.project.config.LoginSession;
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
@RequestMapping("/api/datafiles")
public class DataFilesRestController {
    private final DataFilesService dataFilesService;

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
    //자료실 목록
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DataFilesResponse> list(){
        return dataFilesService.findAll();
    }

    //자료실 검색
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<DataFilesResponse> searchList(@RequestParam String type, @RequestParam String keyword){
        return dataFilesService.findByKeyword(type,keyword);
    }

    //자료실 상세
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DataFilesResponse detail(@PathVariable("id") Long id){
        return dataFilesService.findByDataFiles(id);
    }

    //자료실 등록 -- 로그인 되었는지, 관리자인지
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataFilesResponse create(@Valid @RequestBody DataFilesRequest request, HttpSession session){
        requireAdmin(session);
        Long memberId = LoginSession.memberId(session);
        return dataFilesService.create(request.getTitle(),request.getContent(),memberId);
    }

    //자료실 수정
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DataFilesResponse update(@PathVariable("id")Long id,@Valid @RequestBody DataFilesRequest request, HttpSession session){
        requireAdmin(session);
        Long memberId = LoginSession.memberId(session);
        return dataFilesService.update(id,request.getTitle(),request.getContent(),memberId);
    }

    //자료실 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")Long id, HttpSession session){
        requireAdmin(session);
        dataFilesService.delete(id);
    }
}
