package com.sellics.assignment.amazonsearchvolume.client;

import com.sellics.assignment.amazonsearchvolume.dto.AmazonSuggestions;

public interface AmazonAutocompleteClient {
    AmazonSuggestions getAmazonSuggestion(String keyword);
}
