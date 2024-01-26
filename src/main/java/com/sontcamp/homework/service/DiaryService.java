package com.sontcamp.homework.service;

import com.sontcamp.homework.domain.Diary;
import com.sontcamp.homework.domain.User;
import com.sontcamp.homework.dto.request.DiaryRequestDto;
import com.sontcamp.homework.dto.response.DiaryDetailDto;
import com.sontcamp.homework.exception.CommonException;
import com.sontcamp.homework.exception.ErrorCode;
import com.sontcamp.homework.repository.DiaryRepository;
import com.sontcamp.homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    public DiaryDetailDto createDiary(Long userId, DiaryRequestDto diaryRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Diary diary = Diary.builder()
                .user(user)
                .title(diaryRequestDto.title())
                .content(diaryRequestDto.content())
                .build();

        return DiaryDetailDto.of(diaryRepository.save(diary));
    }

    public DiaryDetailDto readDiary(Long diaryId) {
        return DiaryDetailDto.of(diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE)));
    }

    public List<DiaryDetailDto> readDiaries(String keyword, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Diary> diaryPage = diaryRepository.findDiariesByTitleContainingOrContentContaining(keyword, pageable);

        return diaryPage.getContent().stream()
                .map(DiaryDetailDto::of)
                .toList();
    }

    public DiaryDetailDto updateDiary(Long userId, Long diaryId, DiaryRequestDto diaryRequestDto) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        if (!diary.getUser().getId().equals(userId)) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        diary.update(diaryRequestDto.title(), diaryRequestDto.content());

        return DiaryDetailDto.of(diaryRepository.save(diary));
    }

    public DiaryDetailDto deleteDiary(Long userId, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        if (!diary.getUser().getId().equals(userId)) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        diaryRepository.delete(diary);

        return DiaryDetailDto.of(diary);
    }

}
