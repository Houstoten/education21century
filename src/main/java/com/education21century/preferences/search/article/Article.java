package com.education21century.preferences.search.article;

import com.education21century.preferences.search.preference.Preference;
import lombok.experimental.SuperBuilder;
import org.hibernate.search.annotations.*;
import javax.persistence.*;
import lombok.*;

@Indexed
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "articles")
@SuperBuilder
public class Article extends Preference {

    @Column(columnDefinition = "TEXT")
    private String body;

}
