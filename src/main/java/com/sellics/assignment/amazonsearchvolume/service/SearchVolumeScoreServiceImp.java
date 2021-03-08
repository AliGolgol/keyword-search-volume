package com.sellics.assignment.amazonsearchvolume.service;

import com.sellics.assignment.amazonsearchvolume.client.AmazonAutocompleteClient;
import com.sellics.assignment.amazonsearchvolume.dto.AmazonSuggestions;
import com.sellics.assignment.amazonsearchvolume.dto.ScoreKeywordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;

@Service
public class SearchVolumeScoreServiceImp implements SearchVolumeScoreService {

    private final AmazonAutocompleteClient amazonAutocompleteClient;

    @Autowired
    public SearchVolumeScoreServiceImp(AmazonAutocompleteClient amazonAutocompleteClient) {
        this.amazonAutocompleteClient = amazonAutocompleteClient;
    }


    /**
     * Calculate the search volume of keyword base on Amazon Autocomplete API`s suggestions
     * @param keyword
     * @return the keyword and its related score
     */
    @Override
    public ScoreKeywordDto calculateAmazonSearchVolume(String keyword) {
        String[] keywordList = keyword.split(" ");
        int searchVolumeScore = 0;
        StringJoiner keywordJoiner = new StringJoiner(" ");

        for (int index = 0; index < keywordList.length; index++) {
            keywordJoiner.add(keywordList[index]);
            AmazonSuggestions suggestions = amazonAutocompleteClient.getAmazonSuggestion(keywordJoiner.toString());
            if (suggestions.findByKeyword(keyword.trim()) != -1) {
                searchVolumeScore = calculateSearchVolume(
                        calculateScoreOfKeyword(keywordList, index),
                        calculateScoreOfSuggestion(suggestions.findByKeyword(keyword), suggestions.suggestions.size()));
                break;
            }
        }
        return ScoreKeywordDto.builder().keyword(keyword).score(searchVolumeScore).build();
    }

    /**
     * Calculate the score of Amazon`s suggestion base on its order
     * @param suggestionIndex position of the suggestion in the list of suggestion
     * @param lengthOfSuggestionList size of the suggestion list
     * @return score of suggestion at current position in the list
     */
    private int calculateScoreOfSuggestion(int suggestionIndex, int lengthOfSuggestionList) {
        final int MAX_SCORE = 100;
        final int STEP_VALUE = MAX_SCORE / lengthOfSuggestionList;
        return (MAX_SCORE - (STEP_VALUE * suggestionIndex));
    }

    /**
     * Calculate the score of each keyword in autocomplete
     * @param keywords list of keyword to score
     * @param index position of the keyword in the list
     * @return score of the keyword at current position of autocomplete
     */
    private int calculateScoreOfKeyword(String[] keywords, int index) {
        final int MAX_SCORE = 100;
        final int STEP_VALUE = MAX_SCORE / keywords.length;
        return (MAX_SCORE - (STEP_VALUE * index));
    }

    /**
     * Calculate the consequent of search volume
     * @param keywordScore score of keyword
     * @param suggestionScore score of suggestion
     * @return score of search volume
     */
    private int calculateSearchVolume(int keywordScore, int suggestionScore) {
        final int MAX_SCORE = 100;
        return (keywordScore * suggestionScore) / MAX_SCORE;
    }
}
