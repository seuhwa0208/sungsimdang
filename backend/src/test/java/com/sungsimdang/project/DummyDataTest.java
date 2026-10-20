package com.sungsimdang.project;

import com.sungsimdang.project.bike.domain.*;
import com.sungsimdang.project.bike.repository.BikeMaintenanceRepository;
import com.sungsimdang.project.bike.repository.BikeRepository;
import com.sungsimdang.project.board.domain.DataFiles;
import com.sungsimdang.project.board.domain.Notice;
import com.sungsimdang.project.board.domain.Qna;
import com.sungsimdang.project.board.domain.QnaStatus;
import com.sungsimdang.project.board.dto.QnaAnswerRequest;
import com.sungsimdang.project.board.repository.DataFilesRepository;
import com.sungsimdang.project.board.repository.NoticeRepository;
import com.sungsimdang.project.board.repository.QnaRepository;
import com.sungsimdang.project.board.service.QnaService;
import com.sungsimdang.project.facility.domain.*;
import com.sungsimdang.project.facility.repository.FacilityMaintenanceRepository;
import com.sungsimdang.project.facility.repository.FacilityRepository;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.domain.MemberRole;
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
    private NoticeRepository noticeRepository;

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

    @Autowired
    private QnaService qnaService;

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
    void insertDummyDataFiles() {
        List<Member> members = memberRepository.findAllByOrderByIdDesc();
        for (int i = 1; i <= 10000; i++) {
            Member member = members.get(i % members.size());
            DataFiles dataFiles = DataFiles.builder()
                    .title("더미글 "+i)
                    .content("더미 데이터파일 내용 " + i + "\n입니다.")
                    .member(member)
                    .build();
            dataFilesRepository.save(dataFiles);
        }
        System.out.println("더미 데이터파일 10000건 추가 완료");
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
        facilityMaintenanceRepository.deleteAll();
        facilityRepository.deleteAll();
        List<Facility> facilities = facilityRepository.findAllByOrderByCreatedAtDesc();
        for (int i = 1; i <= 2500; i++) {

            Facility facility = Facility.builder()
                    .location(FacilityLocation.CITY_HALL)
                    .facilityType(FacilityType.ELEVATOR)
                    .build();
            facilityRepository.save(facility);
        }
        for (int i = 1; i <= 2500; i++) {

            Facility facility = Facility.builder()
                    .location(FacilityLocation.CITY_HALL)
                    .facilityType(FacilityType.BIKE_STATION)
                    .build();
            facilityRepository.save(facility);
        }
        for (int i = 1; i <= 2500; i++) {

            Facility facility = Facility.builder()
                    .location(FacilityLocation.CITY_HALL)
                    .facilityType(FacilityType.SCREEN_DOOR)
                    .build();
            facilityRepository.save(facility);
        }
        for (int i = 1; i <= 2500; i++) {

            Facility facility = Facility.builder()
                    .location(FacilityLocation.CITY_HALL)
                    .facilityType(FacilityType.BIKE_RACK)
                    .build();
            facilityRepository.save(facility);
        }
        System.out.println("기존 데이터 삭제 후 더미데이터 1만 건 추가 완");
    }
    @Test
    void insertDummyDataNotice() {
        List<Member> members = memberRepository.findAllByOrderByIdDesc();
        if (members.isEmpty()) {
            throw new IllegalArgumentException("더미 데이터를 넣으려면 회원이 최소 1명은 있어야 함.");
        }
        for (int i = 1; i <= 10000; i++) {
            Member member = members.get(i % members.size());

            Notice notice = Notice.builder()
                    .title("더미글 "+i)
                    .content("더미 qna 내용 " + i + "\n입니다.")
                    .member(member)
                    .build();
            noticeRepository.save(notice);
        }
        System.out.println("더미데이터 10000건 추가 완료");
    }

    @Test
    void insertDummyDataMember() {
        List<Member> members = memberRepository.findAllByOrderByIdDesc();
        for (int i = 1; i <= 10000; i++) {
            Member member = Member.builder()
                    .loginId("dummyMan "+i)
                    .name("더미휴먼 " + i)
                    .password("1111")
                    .email("dummy"+i+"@example.com")
                    .build();
            memberRepository.save(member);
        }
        System.out.println("더미 멤버 10000건 추가 완료");
    }
    @Test
    void insertDummyDataBikeMaintenance() {
        List<Bike> bikes = bikeRepository.findAll();
        Member member = memberRepository.findAll().stream()
                .filter(m -> m.getRole() == MemberRole.ADMIN
                        || m.getRole() == MemberRole.MANAGER)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("정비 담당자가 없습니다."));
        for (Bike bike : bikes) {
            BikeMaintenance maintenance = BikeMaintenance.builder()
                    .bike(bike)
                    .member(member)
                    .bikeFaultType(BikeFaultType.BRAKE)
                    .status(BikeStatus.AVAILABLE)
                    .build();
            bikeMaintenanceRepository.save(maintenance);
        }
        System.out.println("자전거별 정비 이력 생성 완료: " + bikes.size() + "건");
    }
    @Test
    void insertDummyDataFacilityMaintenance() {
        List<Facility> facilities = facilityRepository.findAll();
        Member member = memberRepository.findAll().stream()
                .filter(m -> m.getRole() == MemberRole.ADMIN
                        || m.getRole() == MemberRole.MANAGER)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("정비 담당자가 없습니다."));
        for (Facility facility : facilities) {
            FacilityMaintenance maintenance = FacilityMaintenance.builder()
                    .facility(facility)
                    .member(member)
                    .facilityFaultType(FacilityFaultType.OPERATION_FAILURE)
                    .facilityStatus(FacilityStatus.AVAILABLE)
                    .build();
            facilityMaintenanceRepository.save(maintenance);
        }
        System.out.println("시설별 정비 이력 생성 완료: " + facilities.size() + "건");
    }


    @Test
    void insertDummyDataQnaAnswer() {
        List<Qna> qnas = qnaRepository.findAll();
        int count = 0;
        for (Qna qna : qnas) {
            // 답변 대기 중인 질문만 처리
            if (qna.getStatus() != QnaStatus.WAITING) {
                continue;
            }
            // 답변 등록
            qna.writeAnswer("문의하신 사항을 확인했습니다. 감사합니다.");
            qnaRepository.save(qna);
            count++;
            if (count >= 5000) {
                break;
            }
        }
        System.out.println("Q&A 답변 더미데이터 " + count + "건 등록 완료");
    }
}
