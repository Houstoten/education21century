package com.education21century.preferences.search.preference;

import com.education21century.preferences.search.tag.Tag;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceFilterFactory;
import org.apache.lucene.analysis.standard.ClassicTokenizerFactory;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Indexed
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@AnalyzerDef(name = "ngramAnalyzer",
        tokenizer = @TokenizerDef(factory = ClassicTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
                        @org.hibernate.search.annotations.Parameter(name = "pattern", value = "([^a-zA-Z0-9\\.])"),
                        @org.hibernate.search.annotations.Parameter(name = "replacement", value = " "),
                        @org.hibernate.search.annotations.Parameter(name = "replace", value = "all") }),
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = NGramFilterFactory.class, params = {
                        @org.hibernate.search.annotations.Parameter(name = "minGramSize", value = "3"),
                        @Parameter(name = "maxGramSize", value = "50") }) })
public class Preference {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Field(store = Store.YES)
    @Column(name = "createdAt", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private LocalDate updatedAt;

    @Field(store = Store.YES)
    @Analyzer(definition = "ngramAnalyzer")
    private String title;

    @IndexedEmbedded(depth = 2, includeEmbeddedObjectId = true)
    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<Tag> tags = new HashSet<>();

    @Analyzer(definition = "ngramAnalyzer")
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
