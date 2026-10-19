package com.sungsimdang.project.config;

import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.domain.MemberRole;
import com.sungsimdang.project.member.domain.MemberStatus;
import com.sungsimdang.project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity//메소드 단위의 권한 검사를 사용할 수 있게 함
public class SpringSecurity {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Bean
    CommandLineRunner initAdmin() {
        return args -> {
            if (!memberRepository.existsByLoginId("admin")) {
                memberRepository.save(Member.builder()
                        .loginId("admin")
                        .password(passwordEncoder.encode("1111"))
                        .name("관리자")
                        .tel("010-4444-6666")
                        .email("admin@kyi.local")
                        .role(MemberRole.ADMIN)
                        .status(MemberStatus.ACTIVE)
                        .termsAgreed(true)
                        .privacyAgreed(true)
                        .build());
            }
        };
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
<<<<<<< HEAD
=======
                // SPA: XSRF-TOKEN 쿠키 발급 및 X-XSRF-TOKEN 헤더 검증
                .csrf(csrf -> csrf.spa())
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
                //인가 방법
                .authorizeHttpRequests(auth -> auth
                        //모든 사용자에 대한 접근 => permitAll()
                        .requestMatchers("/", "/login", "/members/register", "/css/**", "/js/**", "/error", "/favicon.ico")
                        .permitAll()
                        //최고 관리자(ADMIN)에 대한 접근 => hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        //회원에 대한 접근 => hasRole("USER"): anyRequest().permitAll()
                        .requestMatchers("/boards/new", "/boards/edit/*", "/boards/delete/*").authenticated()
                        .anyRequest().permitAll()
                )
                //로그인 방법
                .formLogin(form -> form
                        .loginPage("/login")//매핑 방식이 다른 것 -> get, post가 다르듯이
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")//로그인 성공하면 홈페이지로 보내기
                        .failureUrl("/login?error=true")//로그인 실패했을 때 다시 로그인 띄우기. 근데 eroor도 띄워야 함.
                        .permitAll()
                )
                //로그아웃 방법
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)//로그아웃할 때 기존 세션 무효화
                        .deleteCookies("JSESSIONID")//로그아웃할 때 JSESSIONID 쿠키 삭제
                )
                //예외 페이지(403 페이지)
                .exceptionHandling(ex -> ex
<<<<<<< HEAD
                        .accessDeniedPage("/error/403"));
=======
                        .accessDeniedHandler((request, response, exception) -> {
                            response.setStatus(403);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"message\":\"요청이 거부되었습니다. 새로고침 후 다시 시도해 주세요.\"}");
                        }));
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
        return http.build();
    }

}
