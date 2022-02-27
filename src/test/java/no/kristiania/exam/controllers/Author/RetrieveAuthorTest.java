package no.kristiania.exam.controllers.Author;

import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.TestData;
import no.kristiania.exam.dao.AuthorDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class RetrieveAuthorTest {
    AuthorDao authorDao = new AuthorDao(TestData.testDataSource());

    @Test
    void shouldRetrieveAuthor() throws SQLException {
        Author author = new Author();
        author.setName("Lars Bjornbak");
        authorDao.save(author);
        Author author1 = new Author();
        author1.setName("Eskil Blikeng");
        authorDao.save(author1);

        assertThat(authorDao.listAll())
                .extracting(Author::getName)
                .contains(author.getName(), author1.getName());
    }
}
