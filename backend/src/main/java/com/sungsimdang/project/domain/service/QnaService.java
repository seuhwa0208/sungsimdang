package com.sungsimdang.project.domain.service;

import com.sungsimdang.project.domain.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
}
