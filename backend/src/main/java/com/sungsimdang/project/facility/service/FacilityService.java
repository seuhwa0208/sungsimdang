package com.sungsimdang.project.facility.service;

<<<<<<< HEAD
import com.sungsimdang.project.facility.domain.*;
import com.sungsimdang.project.facility.dto.FacilityMaintenanceResponse;
import com.sungsimdang.project.facility.dto.FacilityResponse;
=======
import com.sungsimdang.project.bike.domain.Bike;
import com.sungsimdang.project.bike.domain.BikeMaintenance;
import com.sungsimdang.project.bike.dto.BikeFaultRequest;
import com.sungsimdang.project.bike.dto.BikeMaintenanceResponse;
import com.sungsimdang.project.facility.domain.*;
import com.sungsimdang.project.facility.dto.FacilityFaultRequest;
import com.sungsimdang.project.facility.dto.FacilityMaintenanceResponse;
import com.sungsimdang.project.facility.dto.FacilityResponse;
import com.sungsimdang.project.facility.dto.FacilityStatusRequest;
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
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

//    //검색
//    public List<FacilityResponse> findByKeyword (String facilityNo) {
//        return facilityRepository.findByFacilityNoContainingOrderByCreatedAtDesc(facilityNo).stream().map(FacilityResponse::from).toList();
//    }

    //위치 검색으로 시설 목록 불러오기
<<<<<<< HEAD
    public List<FacilityResponse> findByLocationList (String location){
=======
    public List<FacilityResponse> findByLocationList (FacilityLocation location){
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
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
<<<<<<< HEAD
    public FacilityResponse register(String location, FacilityType facilityType){
=======
    public FacilityResponse register(FacilityLocation location, FacilityType facilityType){
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
        Facility facility = Facility.builder()
                .location(location)
                .facilityType(facilityType)
                .build();
        return FacilityResponse.from(facilityRepository.save(facility));
    }

    //수정
    @Transactional
<<<<<<< HEAD
    public FacilityResponse update(Long id, String location, FacilityType facilityType){
=======
    public FacilityResponse update(Long id, FacilityLocation location, FacilityType facilityType){
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
        Facility facility = findById(id);
        facility.setLocation(location);
        facility.setFacilityType(facilityType);
        return FacilityResponse.from(facility);
    }

    //삭제
    @Transactional
    public void delete(Long id){
        Facility facility = findById(id);
        facilityRepository.delete(facility);
    }
    
    // 시설 고장 접수 기능 maintenance
    // 시설 점검 - 등록 - 하면 facility 상태값 변화해야함
    @Transactional
<<<<<<< HEAD
    public FacilityMaintenanceResponse reportMaintenance(Long facilityId, Long memberId, FacilityFaultType facilityFaultType){
        Facility facility = findById(facilityId);
        Member member = memberRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("아이디가 올바르지 않습니다."));
        if (facility.getFacilityStatus() != FacilityStatus.UNAVAILABLE) {throw new IllegalStateException("상태가 올바르지 않습니다.");}
        facility.setFacilityStatus(FacilityStatus.REPAIRING);
        FacilityMaintenance facilityMaintenance = FacilityMaintenance.builder()
                .facility(facility)
                .member(member)
                .facilityFaultType(facilityFaultType)
                .build();
=======
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
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
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

<<<<<<< HEAD
        FacilityMaintenance preMaintenance = facilityMaintenanceRepository.findByFacility_IdAndFacilityStatusOrderByCreatedAtDesc(
=======
        FacilityMaintenance preMaintenance = facilityMaintenanceRepository.findFirstByFacility_IdAndFacilityStatusOrderByCreatedAtDesc(
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
                facilityId, FacilityStatus.REPAIRING).orElseThrow(()->new IllegalStateException("상태가 올바르지 않습니다."));

        facility.setFacilityStatus(FacilityStatus.AVAILABLE);
        FacilityMaintenance facilityMaintenance = FacilityMaintenance.builder()
                .facility(facility)
                .member(member)
                .facilityFaultType(preMaintenance.getFacilityFaultType())
<<<<<<< HEAD
                .build();
        return FacilityMaintenanceResponse.from(facilityMaintenanceRepository.save(facilityMaintenance));
    }
=======
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
>>>>>>> c4ae55049f94a90180007b7ae9a0e7b118fdb392
}
