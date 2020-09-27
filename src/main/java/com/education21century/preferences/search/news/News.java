package com.education21century.preferences.search.news;

import com.education21century.preferences.search.preference.Preference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;

@Indexed
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news")
@SuperBuilder
public class News extends Preference {

    @Column(columnDefinition = "TEXT")
    private String body;
}
