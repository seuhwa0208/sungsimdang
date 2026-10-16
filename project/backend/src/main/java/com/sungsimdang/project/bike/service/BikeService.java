package com.sungsimdang.project.bike.service;

import com.sungsimdang.project.bike.domain.Bike;
import com.sungsimdang.project.bike.domain.BikeMaintenance;
import com.sungsimdang.project.bike.dto.*;
import com.sungsimdang.project.bike.repository.BikeMaintenanceRepository;
import com.sungsimdang.project.bike.repository.BikeRepository;
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
        Bike bike = bikeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 자전거가 존재하지 않습니다."));
        bike.setLocation(request.getLocation());
        return BikeResponse.from(bike);
    }
    // 자전거 목록 - 대시보드 / 최신순 정렬 - 사용자 공동
    public List<BikeResponse> listCreatedAt() {
        return bikeRepository.findAllByOrderByCreatedAtDesc().stream().map(BikeResponse::from).toList();
    }
    // 자전거 목록 - 대시보드 / 상태별 정렬
    public List<BikeResponse> listStatus() {
        return bikeRepository.findAllByOrderByBikeStatusDesc().stream().map(BikeResponse::from).toList();
    }
    // 자전거 ID 검색
    public BikeResponse findById(Long id) {
        Bike bike = bikeRepository.findByIdOrderByCreatedAtDesc(id).orElseThrow(() -> new IllegalArgumentException("해당 자전거가 존재하지 않습니다."));
        return BikeResponse.from(bike);
    }
    // 자전거 정비 이력 조회 - maintenance
    public List<BikeMaintenanceResponse> findAll() {
        return bikeMaintenanceRepository.findAllByOrderByCreatedAtDesc().stream().map(BikeMaintenanceResponse::from).toList();
    }
    // 자전거 고장 등록 - 고장 유형 변경
    @Transactional
    public BikeMaintenanceResponse faultRegister(BikeFaultRequest request) {
        BikeMaintenance bikeMaintenance = new BikeMaintenance();
        bikeMaintenance.setBikeFaultType(request.getFaultType());
        return BikeMaintenanceResponse.from(bikeMaintenance);
    }
    // 자전거 상태 변경
    @Transactional
    public BikeResponse statusUpdate(BikeStatusRequest request) {
        Bike bike = new Bike();
        bike.setStatus(request.getStatus());
        return BikeResponse.from(bike);
    }
    // 자전거 삭제
    @Transactional
    public void bikeDelete(Long id) {
        Bike bike = bikeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("자전거를 찾을 수 없습니다."));
        bikeRepository.delete(bike);
    }

    // 사용자
    // 자전거 위치별 검색
    public List<BikeResponse> listLocation() {
        return bikeRepository.findByLocationOrderByCreatedAtDesc().stream().map(BikeResponse::from).toList();
    }
    // 자전거 상세 보기
    public BikeResponse detail(Long id) {
        return BikeResponse.from(bikeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 자전거를 찾을 수 없습니다.")));
    }



    // 자전거 대여 - 미구현 예정

    // 자전거 반납 - 미구현 예정
}