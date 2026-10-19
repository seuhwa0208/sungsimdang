package com.sungsimdang.project.facility.restcontroller;

<<<<<<< HEAD
import com.sungsimdang.project.config.LoginSession;
import com.sungsimdang.project.facility.domain.Facility;
import com.sungsimdang.project.facility.domain.FacilityFaultType;
import com.sungsimdang.project.facility.domain.FacilityMaintenance;
import com.sungsimdang.project.facility.dto.FacilityMaintenanceResponse;
import com.sungsimdang.project.facility.dto.FacilityRequest;
import com.sungsimdang.project.facility.dto.FacilityResponse;
import com.sungsimdang.project.facility.repository.FacilityMaintenanceRepository;
import com.sungsimdang.project.facility.repository.FacilityRepository;
=======

import com.sungsimdang.project.config.LoginSession;
import com.sungsimdang.project.facility.domain.FacilityLocation;
import com.sungsimdang.project.facility.dto.*;
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
import com.sungsimdang.project.facility.service.FacilityService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/facilities")
public class FacilityRestController {
    private final FacilityService facilityService;

<<<<<<< HEAD
    // 시설 대시보드에서 권한 검증
    public void checkLoginAndRole(HttpSession session){
        if (!LoginSession.isLoggedIn(session)) { throw new AccessDeniedException("로그인을 해주세요.");}
        if (!LoginSession.isAdmin(session) && !LoginSession.isManager(session)) { throw new AccessDeniedException("권한이 없습니다.");}
=======
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
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
    }

    // 시설 목록 - 매니저 및 관리자 전용임 - 로그인 되어있어야하며 매니저이거나 관리자여야함
    @GetMapping
<<<<<<< HEAD
    public List<FacilityResponse> list(HttpSession session){
        checkLoginAndRole(session);
        return facilityService.findAll();}

    //시설 목록 - 상태 별로 정렬
    @GetMapping("/sort/status")
    public List<FacilityResponse> listSortStatus(HttpSession session){
        checkLoginAndRole(session);
=======
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityResponse> list(HttpSession session){
        requireAdmin(session);
        return facilityService.findAll();}

    //시설 목록 - 상태 별로 정렬
    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityResponse> listSortStatus(HttpSession session){
        requireAdmin(session);
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
        return facilityService.findAllOrderByStatus();
    }

    //시설 검색(위치로만)
    @GetMapping("/search")
<<<<<<< HEAD
    public List<FacilityResponse> searchList(@RequestParam String keyword, HttpSession session){
        checkLoginAndRole(session);
        return facilityService.findByLocationList(keyword);
    }
    // 시설 상세
    @GetMapping("/{id}")
    public FacilityResponse detail(@PathVariable("id") Long id, HttpSession session){
        checkLoginAndRole(session);
=======
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityResponse> searchList(@RequestParam FacilityLocation location, HttpSession session){
        requireAdmin(session);
        return facilityService.findByLocationList(location);
    }
    // 시설 상세
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FacilityResponse detail(@PathVariable("id") Long id, HttpSession session){
        requireAdmin(session);
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
        return facilityService.findByFacility(id);
    }

    // 시설 등록
    @PostMapping
<<<<<<< HEAD
    public FacilityResponse register(@Valid @RequestBody FacilityRequest request, HttpSession session){
        checkLoginAndRole(session);
=======
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityResponse register(@Valid @RequestBody FacilityRequest request, HttpSession session){
        requireAdmin(session);
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
        return facilityService.register(request.getLocation(), request.getFacilityType());
    }

    // 시설 수정
    @PutMapping("/{id}")
<<<<<<< HEAD
    public FacilityResponse update(@PathVariable("id") Long id, @Valid @RequestBody FacilityRequest request, HttpSession session){
        checkLoginAndRole(session);
=======
    @ResponseStatus(HttpStatus.OK)
    public FacilityResponse update(@PathVariable("id") Long id, @Valid @RequestBody FacilityRequest request, HttpSession session){
        requireAdmin(session);
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
        return facilityService.update(id,request.getLocation(),request.getFacilityType());
    }

    // 시설 삭제
    @DeleteMapping("/{id}")
<<<<<<< HEAD
    public void delete(@PathVariable("id")Long id, HttpSession session){
        checkLoginAndRole(session);
=======
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")Long id, HttpSession session){
        requireAdmin(session);
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
        facilityService.delete(id);
    }

    //시설 고장 접수
    //시설 고장 접수 - 목록
<<<<<<< HEAD
    @GetMapping("/maintenances")
    public List<FacilityMaintenanceResponse> history(HttpSession session){
        checkLoginAndRole(session);
=======
    @GetMapping("/maintenance")
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityMaintenanceResponse> history(HttpSession session){
        requireAdmin(session);
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
        return facilityService.maintenanceOrderByCreatedAt();
    }

    //시설 고장 접수 - 등록
<<<<<<< HEAD
    @PostMapping("/maintenances")
    public FacilityMaintenanceResponse report(@PathVariable("facilityId") Long facilityId, @RequestParam FacilityFaultType facilityFaultType, HttpSession session){
        checkLoginAndRole(session);
        Long memberId = LoginSession.memberId(session);
        return facilityService.reportMaintenance(facilityId,memberId,facilityFaultType);
    }

    //시설 고장 접수 - 상태 변경 Repairing -> Available
    @PatchMapping("/{facilityId}/maintenance")
    public FacilityMaintenanceResponse repairingCompleted(@PathVariable("facilityId")Long facilityId, HttpSession session){
        checkLoginAndRole(session);
        Long memberId = LoginSession.memberId(session);
        return facilityService.repairingCompleted(facilityId,memberId);
    }
=======
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
    // 자전거 위치별 검색
    @GetMapping("/member/search")
    @ResponseStatus(HttpStatus.OK)
    public List<FacilityResponse> listLocation(@RequestParam("location") FacilityLocation location) {
        return facilityService.findByLocationList(location);
    }
    // 자전거 상세 보기
    @GetMapping("/member/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FacilityResponse detail(@PathVariable("id") Long id) {
        return facilityService.findByFacility(id);
    }
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
}
