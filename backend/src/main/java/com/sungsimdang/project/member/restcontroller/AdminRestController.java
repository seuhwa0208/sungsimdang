package com.sungsimdang.project.member.restcontroller;

import com.sungsimdang.project.member.domain.Member;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminRestController {
    private final MemberService memberService;

    //관리자 여부 확인
    private boolean isAdmin(HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");
        return member != null && member.getRole() == MemberRole.ADMIN;
    }

    //회원 목록
    @GetMapping("/members")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberResponse> findAll(HttpSession session) {
        if(!isAdmin(session)) {
            throw new IllegalArgumentException("관리자 계정 권한입니다.");
        }
        return memberService.findAll().stream().map(MemberResponse::from).toList();
    }

    //회원 상세
    //id로 회원 조회
    @GetMapping("/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse findById(@PathVariable("id") Long id, HttpSession session) {
        if(!isAdmin(session)) {
            throw new IllegalArgumentException("관리자 계정 권한입니다.");
        }
        return MemberResponse.from(memberService.findById(id));
    }
    //loginId로 조회
    @GetMapping("/members/login-id/{loginId}")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse findByLoginId(@PathVariable("loginId") String loginId, HttpSession session) {
        if(!isAdmin(session)) {
            throw new IllegalArgumentException("관리자 계정 권한입니다.");
        }
        return MemberResponse.from(memberService.findByLoginId(loginId));
    }

    //회원 정보 수정
    @PutMapping("/members/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse update(@PathVariable("id")Long id,@Valid @RequestBody MemberUpdateRequest request, HttpSession session) {
        if(!isAdmin(session)) {
            throw new IllegalArgumentException("관리자 계정 권한입니다.");
        }
        return MemberResponse.from(memberService.update(id, request));
    }
    //회원 활성화
    @PatchMapping("/members/{id}/activate")
    public void activate(@PathVariable("id")Long id, HttpSession session) {
        if(!isAdmin(session)) {
            throw new IllegalArgumentException("관리자 계정 권한입니다.");
        }
        memberService.activate(id);
    }

    //회원 비활성화
    @PatchMapping("/members/{id}/inactivate")
    public void inActivate(@PathVariable("id")Long id, HttpSession session) {
        if(!isAdmin(session)) {
            throw new IllegalArgumentException("관리자 계정 권한입니다.");
        }
        Member target = memberService.findById(id);
        if(target.getRole() == MemberRole.ADMIN) {
            throw new IllegalArgumentException("관리자 계정은 비활성화 할 수 없습니다.");
        }
        memberService.inActivate(id);
    }

    //회원 강퇴
    @PatchMapping("/members/{id}/kick")
    public void kicked(@PathVariable("id")Long id, HttpSession session) {
        if(!isAdmin(session)) {
            throw new IllegalArgumentException("관리자 계정 권한입니다.");
        }
        Member target = memberService.findById(id);
        if(target.getRole() == MemberRole.ADMIN) {
            throw new IllegalArgumentException("관리자 계정은 강퇴 할 수 없습니다.");
        }
        memberService.kicked(id);
    }
    //회원 권한 user 부여
    @PatchMapping("/members/{id}/role")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeRole(@PathVariable("id")Long id, @Valid @RequestBody MemberRoleUpdateRequest request, HttpSession session) {
        if(!isAdmin(session)) {
            throw new IllegalArgumentException("관리자 계정 권한입니다.");
        }
        memberService.role(id, request);
    }
}
