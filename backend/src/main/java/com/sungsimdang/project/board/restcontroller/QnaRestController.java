package com.sungsimdang.project.board.restcontroller;

import com.sungsimdang.project.board.service.QnaService;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.domain.MemberRole;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QnaRestController {
    private final QnaService qnaService;

    //관리자 여부 확인
    private boolean isAdmin(HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");
        return member != null && member.getRole() == MemberRole.ADMIN;
    }
}
