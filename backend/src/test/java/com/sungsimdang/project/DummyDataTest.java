package com.sungsimdang.project;

import com.sungsimdang.project.bike.domain.Bike;
import com.sungsimdang.project.bike.domain.BikeLocation;
import com.sungsimdang.project.bike.repository.BikeMaintenanceRepository;
import com.sungsimdang.project.bike.repository.BikeRepository;
import com.sungsimdang.project.board.domain.Qna;
import com.sungsimdang.project.board.repository.DataFilesRepository;
import com.sungsimdang.project.board.repository.QnaRepository;
import com.sungsimdang.project.facility.domain.Facility;
import com.sungsimdang.project.facility.domain.FacilityLocation;
import com.sungsimdang.project.facility.domain.FacilityType;
import com.sungsimdang.project.facility.repository.FacilityMaintenanceRepository;
import com.sungsimdang.project.facility.repository.FacilityRepository;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DummyDataTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private DataFilesRepository dataFilesRepository;

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private BikeMaintenanceRepository bikeMaintenanceRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private FacilityMaintenanceRepository facilityMaintenanceRepository;

    @Test
    void insertDummyData() {
        List<Member> members = memberRepository.findAllByOrderByIdDesc();
        if (members.isEmpty()) {
            throw new IllegalArgumentException("더미 데이터를 넣으려면 회원이 최소 1명은 있어야 함.");
        }
        for (int i = 1; i <= 10000; i++) {
            Member member = members.get(i % members.size());

            Qna qna = Qna.builder()
                    .title("더미글 "+i)
                    .content("더미 qna 내용 " + i + "\n입니다.")
                    .member(member)
                    .build();
            qnaRepository.save(qna);
        }
        System.out.println("더미데이터 10000건 추가 완료");
    }
    @Test
    void insertDummyDataBike() {
        bikeMaintenanceRepository.deleteAll();
        bikeRepository.deleteAll();
        List<Bike> bikes = bikeRepository.findAllByOrderByCreatedAtDesc();
        for (int i = 1; i <= 10000; i++) {

            Bike bike = Bike.builder()
                    .location(BikeLocation.BANSEOK)
                    .build();
            bikeRepository.save(bike);
        }
        System.out.println("기존 데이터 삭제 후 더미데이터 1만 건 추가 완");
    }

    @Test
    void insertDummyDataFacility() {
        List<Facility> facilities = facilityRepository.findAllByOrderByCreatedAtDesc();
        for (int i = 1; i <= 10000; i++) {

            Facility facility = Facility.builder()
                    .location(FacilityLocation.CITY_HALL)
                    .facilityType(FacilityType.ELEVATOR)
                    .build();
            facilityRepository.save(facility);
        }
        System.out.println("더미데이터 1만 건 추가 완");
    }
}
