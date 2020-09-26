package com.education21century.preferences.search.search;

import com.education21century.preferences.search.preference.Preference;
import com.education21century.preferences.search.preference.dto.PreferenceRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/preferences")
    public ResponseEntity<Set<Preference>> getPreferences(@RequestBody PreferenceRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(searchService.findPreferences(dto));
    }

}
