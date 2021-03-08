package com.sellics.assignment.amazonsearchvolume.service;

import com.sellics.assignment.amazonsearchvolume.dto.ScoreKeywordDto;

public interface SearchVolumeScoreService {
    ScoreKeywordDto calculateAmazonSearchVolume(String keyword);
}
