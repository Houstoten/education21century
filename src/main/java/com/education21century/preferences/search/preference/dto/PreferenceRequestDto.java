package com.education21century.preferences.search.preference.dto;

import com.education21century.preferences.search.tag.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class PreferenceRequestDto {
    private String author;
    private Set<Tag> tags;
    private Float rating;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createdAt;
}
