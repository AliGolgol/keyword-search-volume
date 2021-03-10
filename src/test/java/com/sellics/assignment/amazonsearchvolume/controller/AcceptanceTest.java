package com.sellics.assignment.amazonsearchvolume.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sellics.assignment.amazonsearchvolume.client.AmazonAutocompleteClient;
import com.sellics.assignment.amazonsearchvolume.config.AmazonAPI;
import com.sellics.assignment.amazonsearchvolume.dto.AmazonSuggestions;
import com.sellics.assignment.amazonsearchvolume.dto.ScoreKeywordDto;
import com.sellics.assignment.amazonsearchvolume.dto.Suggestion;
import com.sellics.assignment.amazonsearchvolume.service.SearchVolumeScoreService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AcceptanceTest {

    @Autowired
    SearchVolumeScoreService searchVolumeScoreService;

    @Autowired
    AmazonAutocompleteClient amazonAutocompleteClient;

//    @MockBean
//    AmazonAPI amazonAPI;

//    @Autowired
//    ObjectMapper objectMapper;

    @Test
    public void test() {
        String keyword = "watch repair kit band";
//        AmazonSuggestions amazonSuggestions = new AmazonSuggestions();
//        amazonSuggestions.setSuggestions(Stream.of(
//                new Suggestion("watch repair kit battery replacement"),
//                new Suggestion("watch repair kit band"),
//                new Suggestion("watch repair kit battery"),
//                new Suggestion("iphone charger"),
//                new Suggestion("iphone charger"))
//                .collect(Collectors.toList()));

        ScoreKeywordDto scoreKeywordDto = ScoreKeywordDto.builder().keyword(keyword).score(45).build();

        Assert.assertEquals(scoreKeywordDto.getScore(), searchVolumeScoreService.calculateAmazonSearchVolume(keyword).getScore());
    }
}