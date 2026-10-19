package com.sungsimdang.project.member.service;

import com.sungsimdang.project.exception.LoginFailedException;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.domain.MemberRole;
import com.sungsimdang.project.member.domain.MemberStatus;
import com.sungsimdang.project.member.dto.*;
import com.sungsimdang.project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public MemberResponse register(SignupRequest request) {
        Member member = new Member();
        //이미 사용 중인 아이디인지 확인
        if(memberRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        member.setLoginId(request.getLoginId());
        //이미 사용 중인 이메일인지 확인
        if(memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        member.setEmail(request.getEmail());
        //이용약관에 동의하였는지 확인
        if(!request.isTermsAgreed()) {
            throw new IllegalArgumentException("이용약관에 동의해 주세요.");
        }
        member.setTermsAgreed(request.isTermsAgreed());
        //개인정보 수집 및 이용에 동의하였는지 확인
        if(!request.isPrivacyAgreed()) {
            throw new IllegalArgumentException("개인정보 수집 및 이용에 동의해 주세요.");
        }
        member.setPrivacyAgreed(request.isPrivacyAgreed());
        //비밀번호 일치 확인
        if(!request.getPassword().equals(request.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //비밀번호 암호화
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        //이름 저장
        member.setName(request.getName());
        //전화번호 저장
        member.setTel(request.getTel());
        //ROLE 값의 기본값 지정
        member.setRole(MemberRole.USER);
        //상태 값의 기본값 지정
        member.setStatus(MemberStatus.ACTIVE);
        //가입정보 리턴
        return MemberResponse.from(memberRepository.save(member));
    }
    //로그인
    @Transactional
    public Member login(String loginId, String password) {
        //회원의 존재 여부 확인
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new LoginFailedException("회원 정보를 확인해 주세요."));
        //활성화되어 있는 회원인지 확인
        if(member.getStatus() != MemberStatus.ACTIVE) {
            if(member.getStatus() == MemberStatus.KICKED) {
                throw new LoginFailedException("강퇴된 회원입니다.");
            }
            throw new LoginFailedException("비활성화된 회원입니다.");
        }
        //비밀번호가 올바른지 확인
        if(!passwordEncoder.matches(password, member.getPassword())) {
            throw new LoginFailedException("회원 정보를 확인해 주세요.");
        }
        //모두 일치하고 활성화되어 있는 회원이면, 회원 정보 리턴
        return member;
    }

    //id로 회원 조회
    public MemberResponse findById(Long id) {
        return MemberResponse.from(memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원 정보를 확인해 주세요.")));
    }
    //loginId로 조회 - 마이 페이지, 관리자의 회원 상세 정보 보기
    public MemberResponse findByLoginId(String loginId) {
        return MemberResponse.from(memberRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalArgumentException("회원 정보를 확인해 주세요")));
    }
    //회원 목록 - 관리자
    public List<MemberResponse> findAll() {
        return memberRepository.findAllByOrderByIdDesc().stream().map(MemberResponse::from).toList();
    }
    //회원 정보 수정
    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        //이름 수정
        if(request.getName() != null) {
            if(request.getName().isBlank()) {
                throw new IllegalArgumentException("이름을 입력해 주세요");
            }
            member.setName(request.getName());
        }
        //이메일 수정
        if(request.getEmail() != null) {
            if(request.getEmail().isBlank()) {
                throw new IllegalArgumentException("이메일을 입력해 주세요.");
            }
            if(!member.getEmail().equals(request.getEmail()) && memberRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
            }
            member.setEmail(request.getEmail());
        }
        //전화번호 수정
        if(request.getTel() != null) {
            member.setTel(request.getTel());
        }
        return MemberResponse.from(member);
    }
    //비밀번호 수정
    @Transactional
    public MemberResponse passwordChange(Long id, PasswordChangeRequest request) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        //현재 비밀번호 입력 여부
        if(request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("사용 중인 비밀번호를 입력해 주세요");
        }
        //입력한 비밀번호가 일치하는지 확인
        if(!passwordEncoder.matches(request.getPassword(), (member.getPassword()))) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }
        //새 비밀번호 입력
        if(request.getNewPassword() == null || request.getNewPassword().isBlank()) {
            throw new IllegalArgumentException("새 비밀번호를 입력해 주세요");
        }
        //새 비밀번호 확인
        if(!request.getNewPassword().equals(request.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //비밀번호 변경
        member.setPassword(passwordEncoder.encode(request.getNewPassword()));

        return MemberResponse.from(member);
    }

    //회원 탈퇴
    @Transactional
    public void inActive(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        member.setStatus(MemberStatus.INACTIVE);
    }
    //관리자 - 회원 활성화/비활성화/강퇴
    @Transactional
    public void activate(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        member.setStatus(MemberStatus.ACTIVE);
    }
    @Transactional
    public void inActivate(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        member.setStatus(MemberStatus.INACTIVE);
    }
    @Transactional
    public void kicked(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        member.setStatus(MemberStatus.KICKED);
    }

    //관리자 - 회원 권한 변경
    @Transactional
    public void role(Long id, MemberRoleUpdateRequest request) {
        if(request == null || request.getRole() == null) {
            throw new IllegalArgumentException("변경할 역햘을 선택해 주세요");
        }
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        member.setRole(request.getRole());
    }
}
