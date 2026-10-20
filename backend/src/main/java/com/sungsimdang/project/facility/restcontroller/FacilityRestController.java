package com.sungsimdang.project.facility.restcontroller;


import com.sungsimdang.project.config.LoginSession;
import com.sungsimdang.project.facility.domain.FacilityLocation;
import com.sungsimdang.project.facility.dto.*;
import com.sungsimdang.project.facility.service.FacilityService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/facilities")
public class FacilityRestController {
    private final FacilityService facilityService;

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

    // 시설 목록 - 매니저 및 관리자 전용임 - 로그인 되어있어야하며 매니저이거나 관리자여야함
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityResponse> list(HttpSession session){
        requireAdmin(session);
        return facilityService.findAll();}

    //시설 목록 - 상태 별로 정렬
    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityResponse> listSortStatus(HttpSession session){
        requireAdmin(session);
        return facilityService.findAllOrderByStatus();
    }

    // 시설 상세
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FacilityResponse detail(@PathVariable("id") Long id, HttpSession session){
        requireAdmin(session);
        return facilityService.findByFacility(id);
    }

    // 시설 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityResponse register(@Valid @RequestBody FacilityRequest request, HttpSession session){
        requireAdmin(session);
        return facilityService.register(request.getLocation(), request.getFacilityType());
    }

    // 시설 수정
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FacilityResponse update(@PathVariable("id") Long id, @Valid @RequestBody FacilityRequest request, HttpSession session){
        requireAdmin(session);
        return facilityService.update(id,request.getLocation(),request.getFacilityType());
    }

    // 시설 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")Long id, HttpSession session){
        requireAdmin(session);
        facilityService.delete(id);
    }

    //시설 고장 접수
    //시설 고장 접수 - 목록
    @GetMapping("/maintenance")
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityMaintenanceResponse> history(HttpSession session){
        requireAdmin(session);
        return facilityService.maintenanceOrderByCreatedAt();
    }

    //시설 고장 접수 - 등록
    @PostMapping("/{facilityId}/maintenance")
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityMaintenanceResponse report(@PathVariable("facilityId") Long facilityId, @Valid @RequestBody FacilityFaultRequest request, HttpSession session){
        requireAdmin(session);
        Long memberId = LoginSession.memberId(session);
        return facilityService.reportMaintenance(facilityId,memberId, request);
    }

    //시설 고장 접수 - 상태 변경 Repairing -> Available
    @PatchMapping("/{facilityId}/status")
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityMaintenanceResponse repairingCompleted(@PathVariable("facilityId")Long facilityId, HttpSession session){
        requireAdmin(session);
        Long memberId = LoginSession.memberId(session);
        return facilityService.repairingCompleted(facilityId,memberId);
    }

    //시설 상태 변경
    @PatchMapping("/{facilityId}/maintenance")
    @ResponseStatus(HttpStatus.OK)
    public FacilityResponse statusUpdate(@PathVariable("facilityId") Long id, @Valid @RequestBody FacilityStatusRequest request, HttpSession session) {
        requireAdmin(session);
        return facilityService.statusUpdate(id, request);
    }

    // 사용자
    // 시설 목록 - 최신순 정렬 - 사용자
    @GetMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityResponse> listMember() {
        return facilityService.findAll();
    }
    // 시설 위치별 검색
    @GetMapping("/member/search")
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityResponse> listLocation(@RequestParam("location") FacilityLocation location) {
        return facilityService.findByLocationList(location);
    }

}
