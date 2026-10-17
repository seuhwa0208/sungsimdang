package com.sungsimdang.project.facility.restcontroller;

import com.sungsimdang.project.config.LoginSession;
import com.sungsimdang.project.facility.domain.Facility;
import com.sungsimdang.project.facility.domain.FacilityFaultType;
import com.sungsimdang.project.facility.domain.FacilityMaintenance;
import com.sungsimdang.project.facility.dto.FacilityMaintenanceResponse;
import com.sungsimdang.project.facility.dto.FacilityRequest;
import com.sungsimdang.project.facility.dto.FacilityResponse;
import com.sungsimdang.project.facility.repository.FacilityMaintenanceRepository;
import com.sungsimdang.project.facility.repository.FacilityRepository;
import com.sungsimdang.project.facility.service.FacilityService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/facilities")
public class FacilityRestController {
    private final FacilityService facilityService;

    // 시설 대시보드에서 권한 검증
    public void checkLoginAndRole(HttpSession session){
        if (!LoginSession.isLoggedIn(session)) { throw new AccessDeniedException("로그인을 해주세요.");}
        if (!LoginSession.isAdmin(session) && !LoginSession.isManager(session)) { throw new AccessDeniedException("권한이 없습니다.");}
    }

    // 시설 목록 - 매니저 및 관리자 전용임 - 로그인 되어있어야하며 매니저이거나 관리자여야함
    @GetMapping
    public List<FacilityResponse> list(HttpSession session){
        checkLoginAndRole(session);
        return facilityService.findAll();}

    //시설 목록 - 상태 별로 정렬
    @GetMapping("/sort/status")
    public List<FacilityResponse> listSortStatus(HttpSession session){
        checkLoginAndRole(session);
        return facilityService.findAllOrderByStatus();
    }

    //시설 검색(위치로만)
    @GetMapping("/search")
    public List<FacilityResponse> searchList(@RequestParam String keyword, HttpSession session){
        checkLoginAndRole(session);
        return facilityService.findByLocationList(keyword);
    }
    // 시설 상세
    @GetMapping("/{id}")
    public FacilityResponse detail(@PathVariable("id") Long id, HttpSession session){
        checkLoginAndRole(session);
        return facilityService.findByFacility(id);
    }

    // 시설 등록
    @PostMapping
    public FacilityResponse register(@Valid @RequestBody FacilityRequest request, HttpSession session){
        checkLoginAndRole(session);
        return facilityService.register(request.getLocation(), request.getFacilityType());
    }

    // 시설 수정
    @PutMapping("/{id}")
    public FacilityResponse update(@PathVariable("id") Long id, @Valid @RequestBody FacilityRequest request, HttpSession session){
        checkLoginAndRole(session);
        return facilityService.update(id,request.getLocation(),request.getFacilityType());
    }

    // 시설 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")Long id, HttpSession session){
        checkLoginAndRole(session);
        facilityService.delete(id);
    }

    //시설 고장 접수
    //시설 고장 접수 - 목록
    @GetMapping("/maintenances")
    public List<FacilityMaintenanceResponse> history(HttpSession session){
        checkLoginAndRole(session);
        return facilityService.maintenanceOrderByCreatedAt();
    }

    //시설 고장 접수 - 등록
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
}
