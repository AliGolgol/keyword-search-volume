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
public class SearchVolumeControllerTest {

    @Autowired
    SearchVolumeScoreService searchVolumeScoreService;

    @MockBean
    AmazonAutocompleteClient amazonAutocompleteClient;

    @MockBean
    AmazonAPI amazonAPI;

//    @Autowired
//    ObjectMapper objectMapper;

    @Test
    public void test() {
        AmazonSuggestions amazonSuggestions = new AmazonSuggestions();
        amazonSuggestions.setSuggestions(Stream.of(
                new Suggestion("iphone charger"),
                new Suggestion("iphone charger"),
                new Suggestion("iphone charger"),
                new Suggestion("iphone charger"),
                new Suggestion("iphone charger"))
                .collect(Collectors.toList()));

        when(amazonAPI.getUrl()).thenReturn("https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1&q=%s");
        when(amazonAutocompleteClient.getAmazonSuggestion("iphone charger")).thenReturn(amazonSuggestions);

        ScoreKeywordDto scoreKeywordDto = ScoreKeywordDto.builder().keyword("iphone charger").score(50).build();

        Assert.assertEquals(scoreKeywordDto, searchVolumeScoreService.calculateAmazonSearchVolume("iphone charger"));
    }
}