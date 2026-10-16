package com.sungsimdang.project.facility.service;

import com.sungsimdang.project.facility.domain.Facility;
import com.sungsimdang.project.facility.domain.FacilityFaultType;
import com.sungsimdang.project.facility.domain.FacilityMaintenance;
import com.sungsimdang.project.facility.domain.FacilityType;
import com.sungsimdang.project.facility.dto.FacilityMaintenanceResponse;
import com.sungsimdang.project.facility.dto.FacilityResponse;
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

    // 목록
    public List<FacilityResponse> findAll(){
        return facilityRepository.findAllByOrderByCreatedAtDesc().stream().map(FacilityResponse::from).toList();
    }

    //
    public Facility findById(Long id){
        return facilityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("시설에 관한 정보가 없습니다."));
    }

//    //검색
//    public List<FacilityResponse> findByKeyword (String facilityNo) {
//        return facilityRepository.findByFacilityNoContainingOrderByCreatedAtDesc(facilityNo).stream().map(FacilityResponse::from).toList();
//    }
    //위치 검색으로 시설 목록 불러오기
    public List<FacilityResponse> findByLocationList (String location){
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
    public FacilityResponse register(String facilityNo, String location, FacilityType facilityType){
        Facility facility = Facility.builder()
                .facilityNo(facilityNo)
                .location(location)
                .facilityType(facilityType)
                .build();
        return FacilityResponse.from(facilityRepository.save(facility));
    }

    //수정
    @Transactional
    public FacilityResponse update(Long id, String location, FacilityType facilityType){
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
    
    // 시설 점검 - 등록
    @Transactional
    public FacilityMaintenanceResponse createMaintenance(Facility facility, Member member, FacilityFaultType facilityFaultType){
        FacilityMaintenance facilityMaintenance = FacilityMaintenance.builder()
                .facility(facility)
                .member(member)
                .facilityFaultType(facilityFaultType)
                .build();
        return FacilityMaintenanceResponse.from(facilityMaintenanceRepository.save(facilityMaintenance));
    }

    // 시설 점검 - 최신순 정렬
    public List<FacilityMaintenanceResponse> findAllOrderByCreatedAt () {
        return facilityMaintenanceRepository.findAllByOrderByCreatedAtDesc().stream().map(FacilityMaintenanceResponse::from).toList();
    }
}
