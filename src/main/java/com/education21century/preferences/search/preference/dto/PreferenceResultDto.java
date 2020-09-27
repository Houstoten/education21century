package com.education21century.preferences.search.preference.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreferenceResultDto {
    private UUID id;
    private LocalDate createdAt;
    private String title;
    private String author;
    private String backgroundImage;
    private double rating;
    private String className;
}
