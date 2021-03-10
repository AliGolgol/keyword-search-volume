package com.sellics.assignment.amazonsearchvolume.controller;

import com.sellics.assignment.amazonsearchvolume.client.AmazonAutocompleteClient;
import com.sellics.assignment.amazonsearchvolume.dto.ScoreKeywordDto;
import com.sellics.assignment.amazonsearchvolume.service.SearchVolumeScoreService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AcceptanceTest {

    @Autowired
    SearchVolumeScoreService searchVolumeScoreService;

    @Autowired
    AmazonAutocompleteClient amazonAutocompleteClient;

    @Test
    public void should_calculateTheSearchVolume_when_thereAreSuggestionFromAmazonAPI() {
        String keyword = "watch repair kit band";

        ScoreKeywordDto expectedSearchVolume = ScoreKeywordDto.builder().keyword(keyword).score(5).build();
        ScoreKeywordDto calculatedSearchVolume = searchVolumeScoreService.calculateAmazonSearchVolume(keyword);
        Assert.assertEquals("Should calculate the search volume base on a defined keyword ",expectedSearchVolume.getScore(),calculatedSearchVolume.getScore());
    }
}