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
        return dataFilesRepository.findAll().stream().map(DataFilesResponse::from).toList();
    }

    //게시글 찾기
    public DataFiles findById(Long id) {
        return dataFilesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
    }

    //게시글 단건 조회
    public DataFilesResponse findByDataFiles(Long id) {
        return DataFilesResponse.from(findById(id));
    }

    //등록 - 등록하거나 수정은 member의 상태와 권한 따져야함
    @Transactional
    public DataFilesResponse create(String title, String content, String loginId) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(()->new IllegalArgumentException("잘못된 아이디 입니다."))
        DataFiles dataFiles = DataFiles.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
        return DataFilesResponse.from(dataFilesRepository.save(dataFiles));
    }

    //수정
    @Transactional
    public NoticeResponse update(Long id, String title, String content, String loginId) {
        Member member = memberService.findByLoginId(loginId);
        Notice notice = findById(id);
        notice.setTitle(title);
        notice.setContent(content);
        return NoticeResponse.from(notice);
    }

    //삭제
    @Transactional
    public void delete(Long id) {
        Notice notice = findById(id);
        noticeRepository.delete(notice);
    }
}
