package com.sungsimdang.project.bike.service;

import com.sungsimdang.project.bike.domain.Bike;
import com.sungsimdang.project.bike.domain.BikeLocation;
import com.sungsimdang.project.bike.domain.BikeMaintenance;
import com.sungsimdang.project.bike.domain.BikeStatus;
import com.sungsimdang.project.bike.dto.*;
import com.sungsimdang.project.bike.repository.BikeMaintenanceRepository;
import com.sungsimdang.project.bike.repository.BikeRepository;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BikeService {
    private final BikeRepository bikeRepository;
    private final BikeMaintenanceRepository bikeMaintenanceRepository;
    private final MemberRepository memberRepository;
    //관리자
    // 자전거 등록
    @Transactional
    public BikeResponse register(BikeRequest request) {
        Bike bike = new Bike();
        bike.setLocation(request.getLocation());
        return BikeResponse.from(bikeRepository.save(bike));
    }
    // 자전거 수정
    @Transactional
    public BikeResponse update(Long id, BikeRequest request) {
        Bike bike = bikeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("자전거를 찾을 수 없습니다."));
        bike.setLocation(request.getLocation());
        return BikeResponse.from(bike);
    }
    // 자전거 목록 - 대시보드 / 최신순 정렬 - 사용자 공동
    public List<BikeResponse> listCreatedAt() {
        return bikeRepository.findAllByOrderByCreatedAtDesc().stream().map(BikeResponse::from).toList();
    }
    // 자전거 목록 - 대시보드 / 상태별 정렬
    public List<BikeResponse> listStatus() {
        return bikeRepository.findAllByOrderByStatusDesc().stream().map(BikeResponse::from).toList();
    }
    // 자전거 ID 검색
    public BikeResponse findById(Long id) {
        Bike bike = bikeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("자전거를 찾을 수 없습니다."));
        return BikeResponse.from(bike);
    }
    // 자전거 정비 이력 전체 조회 - maintenance
    public List<BikeMaintenanceResponse> findAll() {
        return bikeMaintenanceRepository.findAllByOrderByCreatedAtDesc().stream().map(BikeMaintenanceResponse::from).toList();
    }
    // 자전거 고장 등록 - 고장 유형 변경
    @Transactional
    public BikeMaintenanceResponse faultRegister(Long id, Long memberId, BikeFaultRequest request) {
        Bike bike = bikeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("자전거를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        if (bike.getStatus() != BikeStatus.UNAVAILABLE) {throw new IllegalStateException("상태가 올바르지 않습니다.");}
        BikeMaintenance bikeMaintenance = new BikeMaintenance();
        bikeMaintenance.setBikeFaultType(request.getFaultType());
        bikeMaintenance.setBike(bike);
        bikeMaintenance.setMember(member);
        bikeMaintenance.setStatus(BikeStatus.REPAIRING);
        bike.setStatus(BikeStatus.REPAIRING);
        return BikeMaintenanceResponse.from(bikeMaintenanceRepository.save(bikeMaintenance));
    }
    // 자전거 상태 변경
    @Transactional
    public BikeResponse statusUpdate(Long id, BikeStatusRequest request) {
        Bike bike = bikeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("자전거를 찾을 수 없습니다."));
        bike.setStatus(request.getStatus());
        return BikeResponse.from(bike);
    }
    // 자전거 삭제
    @Transactional
    public void bikeDelete(Long id) {
        Bike bike = bikeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("자전거를 찾을 수 없습니다."));
        bikeMaintenanceRepository.deleteByBike_Id(id);
        bikeRepository.delete(bike);
    }

    // 사용자
    // 자전거 위치별 검색
    public List<BikeResponse> listLocation(BikeLocation location) {
        return bikeRepository.findByLocationOrderByCreatedAtDesc(location).stream().map(BikeResponse::from).toList();
    }
    // 자전거 상세 보기
    public BikeResponse detail(Long id) {
        return BikeResponse.from(bikeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 자전거를 찾을 수 없습니다.")));
    }

    // 상태 변화가 Repairing -> Available 되면 자동으로 점검 이력에 history가 남음
    @Transactional
    public BikeMaintenanceResponse repairingCompleted(Long id, Long memberId){
        Bike bike = findByBikeId(id);
        if (bike.getStatus() != BikeStatus.REPAIRING) {throw new IllegalStateException("상태가 올바르지 않습니다.");}
        Member member = memberRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("아이디가 올바르지 않습니다."));
        BikeMaintenance preMaintenance = bikeMaintenanceRepository.findFirstByBike_IdAndStatusOrderByCreatedAtDesc(id, BikeStatus.REPAIRING).orElseThrow(()->new IllegalStateException("상태가 올바르지 않습니다."));
        bike.setStatus(BikeStatus.AVAILABLE);
        BikeMaintenance bikeMaintenance = BikeMaintenance.builder()
                .bike(bike)
                .member(member)
                .bikeFaultType(preMaintenance.getBikeFaultType())
                .status(BikeStatus.AVAILABLE)
                .build();
        return BikeMaintenanceResponse.from(bikeMaintenanceRepository.save(bikeMaintenance));
    }

    public Bike findByBikeId(Long id) {
        return bikeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("자전거를 찾을 수 없습니다."));
    }

    // 자전거 대여 - 미구현 예정

    // 자전거 반납 - 미구현 예정
}