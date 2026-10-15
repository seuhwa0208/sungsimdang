package com.sungsimdang.project.board.service;

import com.sungsimdang.project.board.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
}
