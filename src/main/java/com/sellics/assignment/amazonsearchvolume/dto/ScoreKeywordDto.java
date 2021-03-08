package com.sellics.assignment.amazonsearchvolume.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ScoreKeywordDto {
    String keyword;
    int score;

}
