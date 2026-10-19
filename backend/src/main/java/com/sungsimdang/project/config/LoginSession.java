package com.sungsimdang.project.config;

import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.domain.MemberRole;
import jakarta.servlet.http.HttpSession;

public class LoginSession {

    private LoginSession() {
    }

    // 로그인 정보 저장
    public static void login(HttpSession session, Member member) {
        session.setAttribute("loginMember", member);
    }

    public static Long memberId(HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");
        return member != null ? member.getId() : null;
    }
    // 매니저 여부 확인
    public static boolean isManager(HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");
        return member != null && member.getRole() == MemberRole.MANAGER;
    }

    // 관리자 여부 확인
    public static boolean isAdmin(HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");
        return member != null && member.getRole() == MemberRole.ADMIN;
    }

    // 로그인 여부 확인
    public static boolean isLoggedIn(HttpSession session) {
        return memberId(session) != null;
    }
}