package com.sungsimdang.project.board.service;

import com.sungsimdang.project.board.domain.Notice;
import com.sungsimdang.project.board.dto.NoticeResponse;
import com.sungsimdang.project.board.repository.NoticeRepository;
import com.sungsimdang.project.member.domain.Member;
import com.sungsimdang.project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    //목록
    public List<NoticeResponse> findAll(){
        return noticeRepository.findAllByOrderByCreatedAtDesc().stream().map(NoticeResponse::from).toList();
    }

    //게시글 찾기
    public Notice findById(Long id){
       return noticeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("게시글이 존재 하지 않습니다."));
    }

    //게시글 단건 조회
    public NoticeResponse findByNotice(Long id){
        return NoticeResponse.from(findById(id));
    }
    
    //게시글 검색
    public List<NoticeResponse> findByKeyword(String type, String keyword){
        List<Notice> notices;
        if ("TITLE".equals(type)) {notices =  noticeRepository.findByTitleContainingOrderByCreatedAtDesc(keyword);}
        else if ("CONTENT".equals(type)) {notices = noticeRepository.findByContentContainingOrderByCreatedAtDesc(keyword);}
        else {notices = noticeRepository.findByTitleContainingOrContentContainingOrderByCreatedAtDesc(keyword,keyword);}
        return notices.stream().map(NoticeResponse::from).toList();
    }

    //등록 - 등록하거나 수정은 member의 상태와 권한 따져야함
    @Transactional
    public NoticeResponse create(String title, String content, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("잘못된 아이디 입니다."));
        Notice notice =  Notice.builder()
                        .title(title)
                        .content(content)
                        .member(member)
                        .build();
        return NoticeResponse.from(noticeRepository.save(notice));
    }
    
    //수정
    @Transactional
    public NoticeResponse update(Long id, String title, String content, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("잘못된 아이디 입니다."));
        Notice notice = findById(id);
        notice.setTitle(title);
        notice.setContent(content);
        notice.setMember(member);
        return NoticeResponse.from(notice);
    }

    //삭제
    @Transactional
    public void delete(Long id){
        Notice notice = findById(id);
        noticeRepository.delete(notice);
    }
}
