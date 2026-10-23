package com.sungsimdang.project.facility.service;

import com.sungsimdang.project.bike.domain.Bike;
import com.sungsimdang.project.bike.domain.BikeMaintenance;
import com.sungsimdang.project.bike.dto.BikeFaultRequest;
import com.sungsimdang.project.bike.dto.BikeMaintenanceResponse;
import com.sungsimdang.project.facility.domain.*;
import com.sungsimdang.project.facility.dto.FacilityFaultRequest;
import com.sungsimdang.project.facility.dto.FacilityMaintenanceResponse;
import com.sungsimdang.project.facility.dto.FacilityResponse;
import com.sungsimdang.project.facility.dto.FacilityStatusRequest;
import com.sungsimdang.project.facility.repository.FacilityMaintenanceRepository;
import com.sungsimdang.project.facility.repository.FacilityRepository;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final FacilityMaintenanceRepository facilityMaintenanceRepository;
    private final MemberRepository memberRepository;

    // 목록
    public List<FacilityResponse> findAll(){
        return facilityRepository.findAllByOrderByCreatedAtDesc().stream().map(FacilityResponse::from).toList();
    }

    //
    public Facility findById(Long id){
        return facilityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("시설을 찾을 수 없습니다."));
    }

    //위치 검색으로 시설 목록 불러오기
    public List<FacilityResponse> findByLocationList (FacilityLocation location){
        return facilityRepository.findByLocationOrderByCreatedAtDesc(location).stream().map(FacilityResponse::from).toList();
    }

    // 목록 - 상태별로 정렬하기
    public List<FacilityResponse> findAllOrderByStatus (){
        return facilityRepository.findAllByOrderByFacilityStatusDesc().stream().map(FacilityResponse::from).toList();
    }

    // 상세
    public FacilityResponse findByFacility (Long id){
        return FacilityResponse.from(findById(id));
    }

    //등록
    @Transactional
    public FacilityResponse register(FacilityLocation location, FacilityType facilityType){
        Facility facility = Facility.builder()
                .location(location)
                .facilityType(facilityType)
                .build();
        return FacilityResponse.from(facilityRepository.save(facility));
    }

    //수정
    @Transactional
    public FacilityResponse update(Long id, FacilityLocation location, FacilityType facilityType){
        Facility facility = findById(id);
        facility.setLocation(location);
        facility.setFacilityType(facilityType);
        return FacilityResponse.from(facility);
    }

    //삭제
    @Transactional
    public void delete(Long id){
        Facility facility = findById(id);
        facilityMaintenanceRepository.deleteByFacility_Id(id);
        facilityRepository.delete(facility);
    }
    
    // 시설 고장 접수 기능 maintenance
    // 시설 점검 - 등록 - 하면 facility 상태값 변화해야함
    @Transactional
    public FacilityMaintenanceResponse reportMaintenance(Long facilityId, Long memberId, FacilityFaultRequest request) {
        Facility facility = findById(facilityId);
        Member member = memberRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("아이디가 올바르지 않습니다."));
        if (facility.getFacilityStatus() != FacilityStatus.UNAVAILABLE) {throw new IllegalStateException("상태가 올바르지 않습니다.");}
        FacilityMaintenance facilityMaintenance = new FacilityMaintenance();
        facilityMaintenance.setFacilityFaultType(request.getFaultType());
        facilityMaintenance.setFacility(facility);
        facilityMaintenance.setMember(member);
        facilityMaintenance.setFacilityStatus(FacilityStatus.REPAIRING);
        facility.setFacilityStatus(FacilityStatus.REPAIRING);
        return FacilityMaintenanceResponse.from(facilityMaintenanceRepository.save(facilityMaintenance));
    }

    // 시설 점검 - 최신순 정렬
    public List<FacilityMaintenanceResponse> maintenanceOrderByCreatedAt () {
        return facilityMaintenanceRepository.findAllByOrderByCreatedAtDesc().stream().map(FacilityMaintenanceResponse::from).toList();
    }

    // 상태 변화가 Repairing -> Available 되면 자동으로 점검 이력에 history가 남음
    @Transactional
    public FacilityMaintenanceResponse repairingCompleted(Long facilityId, Long memberId){
        Facility facility = findById(facilityId);
        if (facility.getFacilityStatus() != FacilityStatus.REPAIRING) {throw new IllegalStateException("상태가 올바르지 않습니다.");}
        Member member = memberRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("아이디가 올바르지 않습니다."));

        FacilityMaintenance preMaintenance = facilityMaintenanceRepository.findFirstByFacility_IdAndFacilityStatusOrderByCreatedAtDesc(
                facilityId, FacilityStatus.REPAIRING).orElseThrow(()->new IllegalStateException("상태가 올바르지 않습니다."));

        facility.setFacilityStatus(FacilityStatus.AVAILABLE);
        FacilityMaintenance facilityMaintenance = FacilityMaintenance.builder()
                .facility(facility)
                .member(member)
                .facilityFaultType(preMaintenance.getFacilityFaultType())
                .facilityStatus(FacilityStatus.AVAILABLE)
                .build();
        return FacilityMaintenanceResponse.from(facilityMaintenanceRepository.save(facilityMaintenance));
    }

    // 시설 상태 변경
    @Transactional
    public FacilityResponse statusUpdate(Long id, FacilityStatusRequest request) {
        Facility facility = findById(id);
        facility.setFacilityStatus(request.getFacilityStatus());
        return FacilityResponse.from(facility);
    }
}
