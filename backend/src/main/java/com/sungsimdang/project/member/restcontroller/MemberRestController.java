package com.sungsimdang.project.member.restcontroller;


import com.sungsimdang.project.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberRestController {
    private final MemberService memberService;

    //로그인
    @PostMapping("/login")


    //로그아웃
    @PostMapping("/logout")


    //회원 가입
    @PostMapping("/signup")


    //마이 페이지
    @GetMapping("/mypage")


    //회원 정보 수정
    @PutMapping("/mypage")


    //회원 탈퇴
    @DeleteMapping("/delete")

    //비밀번호 변경
    @PutMapping("/passwordChange")

}
