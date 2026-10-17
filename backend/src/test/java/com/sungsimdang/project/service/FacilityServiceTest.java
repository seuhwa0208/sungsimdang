package com.sungsimdang.project.service;

import com.sungsimdang.project.facility.domain.Facility;
import com.sungsimdang.project.facility.domain.FacilityStatus;
import com.sungsimdang.project.facility.domain.FacilityType;
import com.sungsimdang.project.facility.dto.FacilityResponse;
import com.sungsimdang.project.facility.repository.FacilityRepository;
import com.sungsimdang.project.facility.service.FacilityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FacilityServiceTest {

    @Mock
    private FacilityRepository facilityRepository;

    @InjectMocks
    private FacilityService facilityService;

    // 시설 기능 테스트
    // 1. 목록 조회
    //      시설이 없는 경우 - 빈 목록 반환
    @Test
    public void emptyListTest(){
        // given
        when(facilityRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(List.of());

        // when
        var result = facilityService.findAll();

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(facilityRepository).findAllByOrderByCreatedAtDesc();
    }
    //      시설이 있는 경우 -  목록 반환
    @Test
    public void fillListTest(){
        // given
        Facility facility = Facility.builder()
                .id(1L)
                .location("대전역")
                .facilityType(FacilityType.ELEVATOR)
                .facilityStatus(FacilityStatus.AVAILABLE)
                .build();

        when(facilityRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(List.of(facility));

        // when
        var result = facilityService.findAll();

        // then
        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals("대전역", result.get(0).getLocation());
        verify(facilityRepository).findAllByOrderByCreatedAtDesc();
    }
    
    // 2. 위치 기반 목록
    //      위치 정보를 받고 정상적으로 작동하는가
    @Test
    void listByLocation() {
        // given
        String location = "대전역";

        Facility facility = Facility.builder()
                .id(1L)
                .location(location)
                .facilityType(FacilityType.ELEVATOR) // 실제 enum 값 사용
                .facilityStatus(FacilityStatus.AVAILABLE)
                .build();

        when(facilityRepository.findByLocationOrderByCreatedAtDesc(location))
                .thenReturn(List.of(facility));

        // when
        var result = facilityService.findByLocationList(location);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("대전역", result.get(0).getLocation());
        verify(facilityRepository)
                .findByLocationOrderByCreatedAtDesc(location);
    }
    //      선택한 위치에 시설이 존재 하지 않음 - 빈 목록
    @Test
    void emptyListByLocation() {
        // given
        String location = "대전역";

        when(facilityRepository.findByLocationOrderByCreatedAtDesc(location))
                .thenReturn(List.of());

        // when
        var result = facilityService.findByLocationList(location);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(facilityRepository)
                .findByLocationOrderByCreatedAtDesc(location);
    }

    //3. 상태순서로 목록 정렬
    //      DB를 사용하지 않는한 정렬되는지 확인 불가 - 조회 성공 여부만 확인
    @Test
    void listByStatus() {
        // given
        Facility facility = Facility.builder()
                .id(1L)
                .location("대전역")
                .facilityType(FacilityType.ELEVATOR) // 실제 enum 값 사용
                .facilityStatus(FacilityStatus.AVAILABLE)
                .build();

        when(facilityRepository.findAllByOrderByFacilityStatusDesc())
                .thenReturn(List.of(facility));

        // when
        var result = facilityService.findAllOrderByStatus();

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("대전역", result.get(0).getLocation());
        verify(facilityRepository).findAllByOrderByFacilityStatusDesc();
    }
    
    // 4. 시설 상세
    //      시설 상세 - 성공
    @Test
    void detailSuccess() {
        // given
        Long facilityId = 1L;

        Facility facility = Facility.builder()
                .id(facilityId)
                .location("대전역")
                .facilityType(FacilityType.ELEVATOR) // 실제 enum 값 사용
                .facilityStatus(FacilityStatus.AVAILABLE)
                .build();

        when(facilityRepository.findById(facilityId))
                .thenReturn(Optional.of(facility));

        // when
        FacilityResponse result = facilityService.findByFacility(facilityId);

        // then
        assertNotNull(result);
        assertEquals("대전역", result.getLocation());

        verify(facilityRepository).findById(facilityId);
    }
    //      시설 상세 - 조회 실패 - 예외 발생
    @Test
    void detailFailed() {
        // given
        Long facilityId = 999L;

        when(facilityRepository.findById(facilityId))
                .thenReturn(Optional.empty());

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> facilityService.findByFacility(facilityId)
        );
        assertEquals("시설에 관한 정보가 없습니다.", exception.getMessage());
    }
    
    // 5. 시설 정보 등록
    //
    @Test
    void registerSuccess() {
        // given
        String location = "대전역";
        FacilityType facilityType = FacilityType.ELEVATOR; // 실제 enum 값 사용

        Facility facility = Facility.builder()
                .id(1L)
                .location(location)
                .facilityType(facilityType)
                .facilityStatus(FacilityStatus.AVAILABLE)
                .build();

        when(facilityRepository.save(any(Facility.class)))
                .thenReturn(facility);

        // when
        FacilityResponse result =
                facilityService.register(location, facilityType);

        // then
        assertNotNull(result);
        assertEquals("대전역", result.getLocation());
        verify(facilityRepository).save(any(Facility.class));
    }
}
