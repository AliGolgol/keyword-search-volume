package com.sellics.assignment.amazonsearchvolume.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sellics.assignment.amazonsearchvolume.config.AmazonAPI;
import com.sellics.assignment.amazonsearchvolume.dto.AmazonSuggestions;
import com.sellics.assignment.amazonsearchvolume.dto.Suggestion;
import com.sellics.assignment.amazonsearchvolume.exception.AmazonAutocompleteException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class AmazonAutocompleteClientImpl implements AmazonAutocompleteClient {

    private static final Logger LOGGER = getLogger(AmazonAutocompleteClientImpl.class);

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AmazonAPI amazonAPI;

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
