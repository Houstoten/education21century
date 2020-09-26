package com.education21century.preferences.search.search;

import com.education21century.preferences.search.preference.Preference;
import com.education21century.preferences.search.preference.dto.PreferenceRequestDto;
import com.education21century.preferences.search.tag.Tag;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public Set<Preference> findPreferences(PreferenceRequestDto dto){
        return searchRepository.findPreferences(Map.of(
                dto.getAuthor()
                , new String[]{"author"},

                dto.getTags().stream().map(Tag::getTitle).collect(Collectors.joining(" "))
                , new String[]{"tags.title"},

                dto.getRating()
                , new String[]{"rating"},

                dto.getCreatedAt()
                , new String[]{"createdAt"}
        ));
    }
}
