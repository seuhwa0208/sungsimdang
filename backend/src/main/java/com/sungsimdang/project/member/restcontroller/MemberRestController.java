package com.sungsimdang.project.member.restcontroller;


import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.domain.MemberRole;
import com.sungsimdang.project.member.dto.LoginRequest;
import com.sungsimdang.project.member.dto.MemberResponse;
import com.sungsimdang.project.member.dto.SignupRequest;
import com.sungsimdang.project.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberRestController {
    private final MemberService memberService;

    //로그인 여부 확인
    private boolean isLogin(HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");
        return member != null;
    }

    //로그인
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        Member member = memberService.login(request.getLoginId(), request.getPassword());
        session.setAttribute("loginMember", member);
        return MemberResponse.from(member);
    }

    //로그아웃
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpSession session) {
        session.invalidate();
    }

    //회원 가입
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Member register(@Valid @RequestBody SignupRequest request) {
        return memberService.register(request);
    }

    //마이 페이지
    @GetMapping("/me/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Member myPage(@PathVariable("id") Long id) {
        return memberService.findById(id);
    }


    //회원 정보 수정
    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public Member update(@PathVariable("id") Long id, ) {
        return memberService.update(id);
    }

    //회원 탈퇴
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    //비밀번호 변경
    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)


}
