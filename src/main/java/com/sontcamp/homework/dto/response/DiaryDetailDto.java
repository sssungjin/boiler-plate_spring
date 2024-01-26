package com.sontcamp.homework.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sontcamp.homework.domain.Diary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record DiaryDetailDto (
        @JsonProperty("id")
        Long id,

        @JsonProperty("created_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        String createdDate,

        @JsonProperty("title")
        String title,

        @JsonProperty("content")
        String content,

        @JsonProperty("user_id")
        Long userId
) {
    public static DiaryDetailDto of(Diary diary) {
        return new DiaryDetailDto(
                diary.getId(),
                diary.getCreatedDate().toString(),
                diary.getTitle(),
                diary.getContent(),
                diary.getUser().getId());
    }
}

