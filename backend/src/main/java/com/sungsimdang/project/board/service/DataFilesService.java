package com.sungsimdang.project.board.service;

import com.sungsimdang.project.board.domain.DataFiles;
import com.sungsimdang.project.board.domain.Notice;
import com.sungsimdang.project.board.dto.DataFilesResponse;
import com.sungsimdang.project.board.dto.NoticeResponse;
import com.sungsimdang.project.board.repository.DataFilesRepository;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.repository.MemberRepository;
import com.sungsimdang.project.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataFilesService {
    private final DataFilesRepository dataFilesRepository;
    private final MemberRepository memberRepository;

    //목록
    public List<DataFilesResponse> findAll() {
        return dataFilesRepository.findAllByOrderByCreatedAtDesc().stream().map(DataFilesResponse::from).toList();
    }

    //게시글 찾기
    public DataFiles findById(Long id) {
        return dataFilesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
    }

    //게시글 단건 조회
    public DataFilesResponse findByDataFiles(Long id) {
        return DataFilesResponse.from(findById(id));
    }

    //게시글 검색
    public List<DataFilesResponse> findByKeyword(String type, String keyword){
        List<DataFiles> dataFiles;
        if ("TITLE".equals(type)) {dataFiles =  dataFilesRepository.findByTitleContainingOrderByCreatedAtDesc(keyword);}
        else if ("CONTENT".equals(type)) {dataFiles = dataFilesRepository.findByContentContainingOrderByCreatedAtDesc(keyword);}
        else {dataFiles = dataFilesRepository.findByTitleContainingOrContentContainingOrderByCreatedAtDesc(keyword,keyword);}
        return dataFiles.stream().map(DataFilesResponse::from).toList();
    }

    //등록 - 등록하거나 수정은 member의 상태와 권한 따져야함
    @Transactional
    public DataFilesResponse create(String title, String content, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("잘못된 아이디 입니다."));
        DataFiles dataFiles = DataFiles.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
        return DataFilesResponse.from(dataFilesRepository.save(dataFiles));
    }

    //수정
    @Transactional
    public DataFilesResponse update(Long id, String title, String content, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("잘못된 아이디 입니다."));
        DataFiles dataFiles = findById(id);
        dataFiles.setTitle(title);
        dataFiles.setContent(content);
        dataFiles.setMember(member);
        return DataFilesResponse.from(dataFiles);
    }

    //삭제
    @Transactional
    public void delete(Long id) {
        DataFiles dataFiles = findById(id);
        dataFilesRepository.delete(dataFiles);
    }
}
