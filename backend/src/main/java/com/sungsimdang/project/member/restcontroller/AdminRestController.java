package com.sungsimdang.project.member.restcontroller;

import com.sungsimdang.project.config.LoginSession;
import com.sungsimdang.project.member.domain.MemberRole;
import com.sungsimdang.project.member.dto.MemberResponse;
import com.sungsimdang.project.member.dto.MemberRoleUpdateRequest;
import com.sungsimdang.project.member.dto.MemberUpdateRequest;
import com.sungsimdang.project.member.service.MemberService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminRestController {
    private final MemberService memberService;

    //관리자 여부 확인
    private boolean isAdmin(HttpSession session) {
        return LoginSession.isAdmin(session);
    }

    //회원 목록
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MemberResponse> findAll(HttpSession session) {
        if (!isAdmin(session)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
        return memberService.findAll();
    }


    //loginId로 조회
    @GetMapping("/login-id/{loginId}")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse findByLoginId(@PathVariable("loginId") String loginId, HttpSession session) {
        if (!isAdmin(session)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
        return memberService.findByLoginId(loginId);
    }

    //회원 활성화
    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.OK)
    public void activate(@PathVariable("id")Long id, HttpSession session) {
        if (!isAdmin(session)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
        memberService.activate(id);
    }

    //회원 비활성화
    @PatchMapping("/{id}/inactivate")
    @ResponseStatus(HttpStatus.OK)
    public void inActivate(@PathVariable("id")Long id, HttpSession session) {
        if (!isAdmin(session)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
        MemberResponse target = memberService.findById(id);
        if(target.getRole() == MemberRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 계정은 정지시킬 수 없습니다.");
        }
        memberService.inActivate(id);
    }

    //회원 강퇴
    @PatchMapping("/{id}/kick")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void kicked(@PathVariable("id")Long id, HttpSession session) {
        if (!isAdmin(session)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
        MemberResponse target = memberService.findById(id);
        if(target.getRole() == MemberRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 계정은 강퇴할 수 없습니다.");
        }
        memberService.kicked(id);
    }
    //회원 권한 user 부여
    @PatchMapping("/{id}/role")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeRole(@PathVariable("id")Long id, @Valid @RequestBody MemberRoleUpdateRequest request, HttpSession session) {
        if (!isAdmin(session)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
        memberService.role(id, request);
    }
}
