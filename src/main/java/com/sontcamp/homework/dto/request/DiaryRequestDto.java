package com.sontcamp.homework.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
public record DiaryRequestDto (
        @NotNull
        @Size(max = 255, message = "제목은 255자 이내로 입력해주세요.")
        @JsonProperty("title")
        String title,

        @NotNull
        @Size(max = 1000, message = "내용은 1000자 이내로 입력해주세요.")
        @JsonProperty("content")
        String content
) { }