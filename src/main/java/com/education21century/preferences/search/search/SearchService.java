package com.education21century.preferences.search.search;

import com.education21century.preferences.search.preference.Preference;
import com.education21century.preferences.search.preference.dto.PreferenceRequestDto;
import com.education21century.preferences.search.preference.dto.PreferenceResultDto;
import com.education21century.preferences.search.tag.Tag;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SearchService {

    private final SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public Set<PreferenceResultDto> findPreferences(PreferenceRequestDto dto) {
        var qb = searchRepository.getQueryBuilder(Preference.class);
        var bj = searchRepository.getBooleanJunction(Preference.class);

        if (dto.getRating() != null && dto.getRating() >= 0) {
            bj.should(qb.range()
                    .onField("rating")
                    .from(dto.getRating() - 0.5)
                    .to(dto.getRating() + 0.5)
                    .createQuery());
        }

        if (dto.getCreatedAt() != null) {
            bj.should(qb.range()
                    .onField("createdAt")
                    .from(dto.getCreatedAt().minusMonths(1))
                    .to(dto.getCreatedAt().plusMonths(1))
                    .createQuery());
        }

        if (dto.getTitle() != null) {
            bj.should(qb.keyword().onFields("title").matching(dto.getTitle()).createQuery());
        }

        if (dto.getAuthor() != null) {
            bj.should(qb.keyword().onFields("author").matching(dto.getAuthor()).createQuery());
        }

        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            bj.should(qb.keyword().onFields("tags.title")
                    .matching(dto
                            .getTags()
                            .stream()
                            .map(Tag::getTitle)
                            .collect(Collectors.joining(" "))
                    )
                    .createQuery());
        }

        Stream<Object[]> ftq = searchRepository.getFullTextFromBoolean(bj, Preference.class)
                .setProjection("id", "createdAt", "author", "title", "backgroundImage", "rating", "className")
                .getResultStream();

        return ftq.map(o -> PreferenceResultDto
                .builder()
                .id((UUID) o[0])
                .createdAt((LocalDate) o[1])
                .author((String) o[2])
                .title((String) o[3])
                .backgroundImage((String) o[4])
                .rating((Double) o[5])
                .className(o[6].toString())
                .build()
        )
                .collect(Collectors.toSet());
    }
}
