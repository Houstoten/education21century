package com.education21century.preferences.search.preference;

import com.education21century.preferences.search.tag.Tag;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Indexed
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Preference {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Field(store = Store.YES)
    @Column(name = "createdAt", updatable = false, nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private Date updatedAt;

    @Field(store = Store.YES)
    private String title;

    @IndexedEmbedded(depth = 2, includeEmbeddedObjectId = true)
    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<Tag> tags = new HashSet<>();

    @Field(store = Store.YES)
    private String author;

    @Field(store = Store.YES)
    @Column(name = "image")
    private String backgroundImage;

    @Max(5)
    @Min(0)
    @Field(store = Store.YES)
    private double rating;
}
