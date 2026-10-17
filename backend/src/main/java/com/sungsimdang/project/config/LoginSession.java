package com.sungsimdang.project.config;

import com.sungsimdang.project.member.domain.Member;
import jakarta.servlet.http.HttpSession;

public class LoginSession {

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

    public static boolean isLoggedIn(HttpSession session) {
        return memberId(session) != null;
    }
}