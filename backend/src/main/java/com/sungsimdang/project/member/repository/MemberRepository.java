package com.sungsimdang.project.member.repository;

import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.domain.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId (String loginId);
    Optional<Member> findByEmail (String email);
    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);
    List<Member> findAllByOrderByIdDesc();
    List<Member> findByStatusOrderByIdDesc(MemberStatus status);
    List<Member> findTop10ByOrderByIdDesc();

}
