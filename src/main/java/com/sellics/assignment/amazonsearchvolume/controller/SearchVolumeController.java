package com.sellics.assignment.amazonsearchvolume.controller;

import com.sellics.assignment.amazonsearchvolume.dto.ScoreKeywordDto;
import com.sellics.assignment.amazonsearchvolume.service.SearchVolumeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchVolumeController {

    @Autowired
    private final SearchVolumeScoreService searchVolumeScore;

    public SearchVolumeController(SearchVolumeScoreService searchVolumeScore) {
        this.searchVolumeScore = searchVolumeScore;
    }

    @GetMapping(value = "/estimate")
    public ScoreKeywordDto getScore(@RequestParam String keyword){
        ScoreKeywordDto result = searchVolumeScore.calculateAmazonSearchVolume(keyword);
        return result;
    }
}
