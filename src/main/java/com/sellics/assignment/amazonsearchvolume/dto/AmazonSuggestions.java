package com.sellics.assignment.amazonsearchvolume.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AmazonSuggestions
{
    public List<Suggestion> suggestions;

    /**
     * Find the position of keyword in the list
     * @param keyword Keyword to get its index
     * @return Index of keyword within list
     */
    public int findByKeyword(String keyword){
        return suggestions.indexOf(keyword);
    }

}
