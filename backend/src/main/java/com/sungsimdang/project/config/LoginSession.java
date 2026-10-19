package com.sungsimdang.project.config;

import com.sungsimdang.project.member.domain.Member;
<<<<<<< HEAD
=======
import com.sungsimdang.project.member.domain.MemberRole;
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
import jakarta.servlet.http.HttpSession;

public class LoginSession {

<<<<<<< HEAD
    public static final String MEMBER_ID = "LOGIN_MEMBER_ID";
    public static final String MEMBER_ROLE = "LOGIN_MEMBER_ROLE";
    public static final String MEMBER_NAME = "LOGIN_MEMBER_NAME";

    private LoginSession() {
    }

    public static void login(HttpSession session, Member member) {
        session.setAttribute(MEMBER_ID, member.getId());
        session.setAttribute(MEMBER_ROLE, member.getRole().name());
        session.setAttribute(MEMBER_NAME, member.getName());
    }

    public static Long memberId(HttpSession session) {
        Object value = session.getAttribute(MEMBER_ID);

        return value instanceof Long ? (Long) value : null;
    }

    public static boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute(MEMBER_ROLE));
    }

    public static boolean isManager(HttpSession session) {
        return "MANAGER".equals(session.getAttribute(MEMBER_ROLE));
    }

=======
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
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
    public static boolean isLoggedIn(HttpSession session) {
        return memberId(session) != null;
    }
}