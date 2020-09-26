package com.education21century.preferences.search.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import java.util.UUID;

@Indexed
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tags", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title"})
})
public class Tag {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Field(store = Store.YES)
    private String title;
}
