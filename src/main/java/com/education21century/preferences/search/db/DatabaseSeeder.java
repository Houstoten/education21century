package com.education21century.preferences.search.db;

import com.education21century.preferences.search.article.Article;
import com.education21century.preferences.search.article.ArticleRepository;
import com.education21century.preferences.search.news.News;
import com.education21century.preferences.search.news.NewsRepository;
import com.education21century.preferences.search.tag.Tag;
import com.education21century.preferences.search.tag.TagRepository;
import com.education21century.preferences.search.test.Test;
import com.education21century.preferences.search.test.TestRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DatabaseSeeder {
    private final JdbcTemplate jdbcTemplate;
    private final ArticleRepository articleRepository;
    private final NewsRepository newsRepository;
    private final TagRepository tagRepository;
    private final TestRepository testRepository;

    public DatabaseSeeder(JdbcTemplate jdbcTemplate
            , ArticleRepository articleRepository
            , NewsRepository newsRepository
            , TagRepository tagRepository
            , TestRepository testRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.articleRepository = articleRepository;
        this.newsRepository = newsRepository;
        this.tagRepository = tagRepository;
        this.testRepository = testRepository;
    }


    @EventListener
    public void seed(ContextRefreshedEvent event) {
        List<Object> o = jdbcTemplate.query("SELECT * FROM tests", (resultSet, rowNum) -> null);
        o.addAll(jdbcTemplate.query("SELECT * FROM tags", (resultSet, rowNum) -> null));
        o.addAll(jdbcTemplate.query("SELECT * FROM news", (resultSet, rowNum) -> null));
        o.addAll(jdbcTemplate.query("SELECT * FROM articles", (resultSet, rowNum) -> null));
        if(o == null || o.size() <= 0) {
            var tags = seedTagsTable();
            seedArticlesTable(tags);
            seedNewsTable(tags);
            seedTestsTable(tags);
        }
    }

    private List<Tag> seedTagsTable() {
        return tagRepository.saveAll(
                List.of(new Tag(null, "science")
                        , new Tag(null, "literature")
                        , new Tag(null, "pop")
                        , new Tag(null, "art"))
        );
    }

    private List<Test> seedTestsTable(List<Tag> tags) {
        Collections.shuffle(tags);
        return testRepository.saveAll(
                List.of(
                        Test.builder()
                                .title("Spicy food")
                                .backgroundImage("https://media.baltictimes.com/media/photos/145563_17278853955d5936ffc3400_big.jpg")
                                .author("Adam")
                                .createdAt(new Date())
                                .updatedAt(new Date())
                                .tags(new HashSet<>(tags.subList(0, new Random().nextInt(tags.size()))))
                                .rating(4.6f)
                                .build()
                        , Test.builder()
                                .title("Observe")
                                .backgroundImage("https://psyfactor.org/news/i/news22.jpg.pagespeed.ce.C0Y7-rs2-1.jpg")
                                .author("Adam")
                                .createdAt(new Date())
                                .updatedAt(new Date())
                                .tags(new HashSet<>(tags.subList(0, new Random().nextInt(tags.size()))))
                                .rating(3.2f)
                                .build()
                        , Test.builder()
                                .title("Gay")
                                .backgroundImage("https://cdn.tinybuddha.com/wp-content/uploads/2015/02/Woman-hugging-heart.png")
                                .author("Glen")
                                .createdAt(new Date())
                                .updatedAt(new Date())
                                .tags(new HashSet<>(tags.subList(0, new Random().nextInt(tags.size()))))
                                .rating(2.45f)
                                .build()
                )
        );
    }

    private List<News> seedNewsTable(List<Tag> tags) {
        Collections.shuffle(tags);
        return newsRepository.saveAll(
                List.of(
                        News.builder()
                                .body("Am terminated it excellence invitation projection as. She graceful shy " +
                                        "believed distance use nay. Lively is people so basket ladies window expect." +
                                        " Supply as so period it enough income he genius. Themselves acceptance bed " +
                                        "sympathize get dissimilar way admiration son. Design for are edward regret met" +
                                        " lovers. This are calm case roof and. \n")
                                .title("Invitation projection")
                                .backgroundImage("https://media.baltictimes.com/media/photos/145563_17278853955d5936ffc3400_big.jpg")
                                .author("Sofie")
                                .createdAt(new Date())
                                .updatedAt(new Date())
                                .tags(new HashSet<>(tags.subList(0, new Random().nextInt(tags.size()))))
                                .rating(4.6f)
                                .build()
                        , News.builder()
                                .body("He do subjects prepared bachelor juvenile ye oh. He feelings removing" +
                                        " informed he as ignorant we prepared. Evening do forming observe spirits" +
                                        " is in. Country hearted be of justice sending. On so they as with room" +
                                        " cold ye. Be call four my went mean. Celebrated if remarkably especially " +
                                        "an. Going eat set she books found met aware. \n")
                                .title("Observe spirits")
                                .backgroundImage("https://psyfactor.org/news/i/news22.jpg.pagespeed.ce.C0Y7-rs2-1.jpg")
                                .author("Adam")
                                .createdAt(new Date())
                                .updatedAt(new Date())
                                .tags(new HashSet<>(tags.subList(0, new Random().nextInt(tags.size()))))
                                .rating(3.2f)
                                .build()
                        , News.builder()
                                .body("The him father parish looked has sooner. Attachment frequently gay" +
                                        " terminated son. You greater nay use prudent placing. Passage to" +
                                        " so distant behaved natural between do talking. Friends off her" +
                                        " windows painful. Still gay event you being think nay for. In three " +
                                        "if aware he point it. Effects warrant me by no on feeling settled resolve. ")
                                .title("Still gay")
                                .backgroundImage("https://cdn.tinybuddha.com/wp-content/uploads/2015/02/Woman-hugging-heart.png")
                                .author("Glen")
                                .createdAt(new Date())
                                .updatedAt(new Date())
                                .tags(new HashSet<>(tags.subList(0, new Random().nextInt(tags.size()))))
                                .rating(2.45f)
                                .build()
                )
        );
    }

    private List<Article> seedArticlesTable(List<Tag> tags) {
        Collections.shuffle(tags);
        return articleRepository.saveAll(
                List.of(
                        Article.builder()
                                .body("\"He do subjects prepared bachelor juvenile ye oh. \" +\n" +
                                        "\"He feelings removing informed he as ignorant we prepared. \" +\n" +
                                        "\"Evening do forming observe spirits is in. Country hearted be of justice sending. \" +\n" +
                                        " \"On so they as with room cold ye. Be call four my went mean. \" +\n" +
                                        "\"Celebrated if remarkably especially an. Going eat set she books found met aware. \\n\"")
                                .title("Bachelor graduation")
                                .backgroundImage("https://media.baltictimes.com/media/photos/145563_17278853955d5936ffc3400_big.jpg")
                                .author("Jean")
                                .createdAt(new Date())
                                .updatedAt(new Date())
                                .tags(new HashSet<>(tags.subList(0, new Random().nextInt(tags.size()))))
                                .rating(4.5f)
                                .build()
                        , Article.builder()
                                .body("For who thoroughly her boy estimating conviction. Removed demands" +
                                        " expense account in outward tedious do. Particular way thoroughly " +
                                        "unaffected projection favourable mrs can projecting own. Thirty it" +
                                        " matter enable become admire in giving. See resolved goodness felicity " +
                                        "shy civility domestic had but. Drawings offended yet answered jennings" +
                                        " perceive laughing six did far. \n")
                                .title("Self-esteem")
                                .backgroundImage("https://psyfactor.org/news/i/news22.jpg.pagespeed.ce.C0Y7-rs2-1.jpg")
                                .author("Big Boy")
                                .createdAt(new Date())
                                .updatedAt(new Date())
                                .tags(new HashSet<>(tags.subList(0, new Random().nextInt(tags.size()))))
                                .rating(3.0f)
                                .build()
                        , Article.builder()
                                .body("In by an appetite no humoured returned informed. Possession so comparison" +
                                        " inquietude he he conviction no decisively. Marianne jointure attended she " +
                                        "hastened surprise but she. Ever lady son yet you very paid form away." +
                                        " He advantage of exquisite resolving if on tolerably. Become sister" +
                                        " on in garden it barton waited on. ")
                                .title("Informal humour")
                                .backgroundImage("https://cdn.tinybuddha.com/wp-content/uploads/2015/02/Woman-hugging-heart.png")
                                .author("Clara")
                                .createdAt(new Date())
                                .updatedAt(new Date())
                                .tags(new HashSet<>(tags.subList(0, new Random().nextInt(tags.size()))))
                                .rating(2.2f)
                                .build()
                )
        );
    }
}
