package com.sungsimdang.project.bike.restcontroller;

import com.sungsimdang.project.bike.domain.BikeLocation;
import com.sungsimdang.project.bike.dto.*;
import com.sungsimdang.project.bike.service.BikeService;
import com.sungsimdang.project.config.LoginSession;
import com.sungsimdang.project.facility.dto.FacilityMaintenanceResponse;
import com.sungsimdang.project.member.domain.Member;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/bike")
@RequiredArgsConstructor
public class BikeRestController {
    private final BikeService bikeService;

    //관리자 || 매니저 여부 확인
    private boolean isAdminOrManager(HttpSession session) {
        return LoginSession.isAdmin(session) || LoginSession.isManager(session);
    }
    //로그인 여부 확인
    private boolean isLogin(HttpSession session) {
        return LoginSession.isLoggedIn(session);
    }
    //로그인한 회원이 관리자인지 확인하는 것 - 에러 코드 분류용
    private void requireAdmin(HttpSession session) {
        if (!isLogin(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        if (!isAdminOrManager(session)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
    }
    // 자전거 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BikeResponse register(@Valid @RequestBody BikeRequest request, HttpSession session) {
        requireAdmin(session);
        return bikeService.register(request);
    }
    // 자전거 수정
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BikeResponse update(@PathVariable("id") Long id, @Valid @RequestBody BikeRequest request, HttpSession session) {
        requireAdmin(session);
        return bikeService.update(id, request);
    }

    // 자전거 목록 - 대시보드 / 최신순 정렬 - 관리자
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BikeResponse> listAdmin(HttpSession session) {
        requireAdmin(session);
        return bikeService.listCreatedAt();
    }
    // 자전거 목록 - 대시보드 / 상태별 정렬 - 관리자
    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<BikeResponse> findAllStatus(HttpSession session) {
        requireAdmin(session);
        return bikeService.listStatus();
    }
    // 자전거 ID 검색 - 관리자
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BikeResponse findById(@PathVariable("id") Long id, HttpSession session) {
        requireAdmin(session);
        return bikeService.findById(id);
    }

    // 자전거 정비 이력 전체 조회 - maintenance
    @GetMapping("/maintenance")
    @ResponseStatus(HttpStatus.OK)
    public List<BikeMaintenanceResponse> findAll(HttpSession session) {
        requireAdmin(session);
        return bikeService.findAll();
    }

    // 자전거 고장 등록 - 고장 유형 변경
    @PostMapping("/{id}/maintenance")
    @ResponseStatus(HttpStatus.CREATED)
    public BikeMaintenanceResponse faultRegister(@PathVariable("id") Long id, @Valid @RequestBody BikeFaultRequest request, HttpSession session) {
        requireAdmin(session);
        Member member = (Member) session.getAttribute("loginMember");
        return bikeService.faultRegister(id, member.getId(), request);
    }

    //자전거 고장 접수 - 상태 변경 Repairing -> Available
    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.CREATED)
    public BikeMaintenanceResponse repairingCompleted(@PathVariable("id")Long id, HttpSession session){
        requireAdmin(session);
        Long memberId = LoginSession.memberId(session);
        return bikeService.repairingCompleted(id, memberId);
    }

    // 자전거 상태 변경
    @PatchMapping("/{id}/maintenance")
    @ResponseStatus(HttpStatus.OK)
    public BikeResponse statusUpdate(@PathVariable("id") Long id, @Valid @RequestBody BikeStatusRequest request, HttpSession session) {
        requireAdmin(session);
        return bikeService.statusUpdate(id, request);
    }

    // 자전거 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bikeDelete(@PathVariable("id") Long id, HttpSession session) {
        requireAdmin(session);
        bikeService.bikeDelete(id);
    }
    // 사용자
    // 자전거 목록 - 최신순 정렬 - 사용자
    @GetMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public List<BikeResponse> listMember() {
        return bikeService.listCreatedAt();
    }
    // 자전거 위치별 검색
    @GetMapping("/member/search")
    @ResponseStatus(HttpStatus.OK)
    public List<BikeResponse> listLocation(@RequestParam("location") BikeLocation location) {
        return bikeService.listLocation(location);
    }
    // 자전거 상세 보기
    @GetMapping("/member/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BikeResponse detail(@PathVariable("id") Long id) {
        return bikeService.detail(id);
    }
}