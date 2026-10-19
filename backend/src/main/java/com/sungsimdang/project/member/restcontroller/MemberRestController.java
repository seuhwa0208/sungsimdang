package com.sungsimdang.project.member.restcontroller;


import com.sungsimdang.project.config.LoginSession;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.dto.*;
import com.sungsimdang.project.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberRestController {
    private final MemberService memberService;

    //로그인 여부 확인
    private boolean isLogin(HttpSession session) {
        return LoginSession.isLoggedIn(session);
    }

    //로그인
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        Member member = memberService.login(request.getLoginId(), request.getPassword());
        LoginSession.login(session, member);
        return MemberResponse.from(member);
    }

    //로그아웃
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpSession session) {
        if(!isLogin(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        session.invalidate();
    }

    //회원 가입
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse register(@Valid @RequestBody SignupRequest request) {
        return memberService.register(request);
    }

    //마이 페이지
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse myPage(HttpSession session) {
        if(!isLogin(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        Member member = (Member) session.getAttribute("loginMember");
        return memberService.findById(member.getId());
    }

    //회원 정보 수정
    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse update(@Valid @RequestBody MemberUpdateRequest request, HttpSession session) {
        if(!isLogin(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        Member member = (Member) session.getAttribute("loginMember");
        return memberService.update(member.getId(), request);
    }

    //회원 탈퇴
    @PostMapping("/inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inActive(HttpSession session) {
        if(!isLogin(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        Member member = (Member) session.getAttribute("loginMember");
        memberService.inActive(member.getId());
        session.invalidate();
    }

    //비밀번호 변경
    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@Valid @RequestBody PasswordChangeRequest request, HttpSession session) {
        if(!isLogin(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        Member member = (Member) session.getAttribute("loginMember");
        memberService.passwordChange(member.getId(), request);
    }
}
