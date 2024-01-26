package com.sontcamp.homework.controller;

import com.sontcamp.homework.annotation.UserId;
import com.sontcamp.homework.dto.common.ResponseDto;
import com.sontcamp.homework.dto.request.DiaryRequestDto;
import com.sontcamp.homework.exception.CommonException;
import com.sontcamp.homework.exception.ErrorCode;
import com.sontcamp.homework.service.DiaryService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("")
    public ResponseDto<?> createDiary(@UserId Long userId, @RequestBody DiaryRequestDto diaryRequestDto) {
        return ResponseDto.created(diaryService.createDiary(userId, diaryRequestDto));
    }

    @GetMapping("/{diaryId}")
    public ResponseDto<?> getDiary(@PathVariable Long diaryId) {
        return ResponseDto.ok(diaryService.readDiary(diaryId));
    }

    //미완성
    @GetMapping("")
    public ResponseDto<?> readDiaries(@RequestParam(value = "keyword") @NotNull String keyword,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value= "size", defaultValue = "10") Integer size) {
        if (page < 1 || size < 1) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER);
        }
        return ResponseDto.ok(diaryService.readDiaries(keyword, page, size));
    }

    @Transactional
    @PatchMapping("/{diaryId}")
    public ResponseDto<?> updateDiary(@UserId Long userId, @PathVariable Long diaryId, @RequestBody DiaryRequestDto diaryRequestDto) {
        return ResponseDto.ok(diaryService.updateDiary(userId, diaryId, diaryRequestDto));
    }

    @Transactional
    @DeleteMapping("/{diaryId}")
    public ResponseDto<?> deleteDiary(@UserId Long userId, @PathVariable Long diaryId) {
        return ResponseDto.ok(diaryService.deleteDiary(userId, diaryId));
    }
}
