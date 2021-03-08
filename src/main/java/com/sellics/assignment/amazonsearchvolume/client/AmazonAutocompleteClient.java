package com.sellics.assignment.amazonsearchvolume.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sellics.assignment.amazonsearchvolume.config.AmazonAPI;
import com.sellics.assignment.amazonsearchvolume.dto.AmazonSuggestions;
import com.sellics.assignment.amazonsearchvolume.dto.Suggestion;
import com.sellics.assignment.amazonsearchvolume.exception.AmazonAutocompleteException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class AmazonAutocompleteClient {

    private static final Logger LOGGER = getLogger(AmazonAutocompleteClient.class);
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final AmazonAPI amazonAPI;

    public AmazonAutocompleteClient(ObjectMapper objectMapper, AmazonAPI amazonAPI) {
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
        this.amazonAPI = amazonAPI;
    }


    /**
     * To call Amazon Autocomplete API
     *
     * @param keyword The keyword to search through Amazon Autocomplete API
     * @return list of Amazon`s suggestions which is {@link AmazonSuggestions} object
     */
    public AmazonSuggestions getAmazonSuggestion(String keyword) {
        String url = String.format(amazonAPI.getUrl(), keyword);
        String response = restTemplate.getForObject(url, String.class);
        AmazonSuggestions results = parseResponse(response);
        return results;
    }

    /**
     * Parse the response of Amazon Autocomplete API to {@link AmazonSuggestions}
     *
     * @param searchResult the Amazon Autocomplete API`s response
     * @return
     */
    private AmazonSuggestions parseResponse(String searchResult) {
        Object[] resultArray;

        AmazonSuggestions amazonSuggestions = new AmazonSuggestions();
        try {
            resultArray = objectMapper.readValue(searchResult, Object[].class);
            amazonSuggestions.setSuggestions((List<Suggestion>) resultArray[1]);
            LOGGER.info(amazonSuggestions.suggestions.toString());
        } catch (IOException ex) {
            LOGGER.error(ex.toString());
            throw new AmazonAutocompleteException("Amazon Autocomplete API parsing error" + ex);
        }
        return amazonSuggestions;
    }


}
