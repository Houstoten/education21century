package com.education21century.preferences.search.search;

import com.education21century.preferences.search.preference.Preference;
import com.education21century.preferences.search.preference.dto.PreferenceRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @SneakyThrows
    @PostMapping("/preferences")
    public String getPreferences(@RequestBody PreferenceRequestDto dto) {
        var result = searchService.findPreferences(dto);
        return new GsonBuilder().create().toJson(result);
    }

}
