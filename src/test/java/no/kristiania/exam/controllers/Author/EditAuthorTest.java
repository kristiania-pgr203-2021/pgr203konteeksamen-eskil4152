package no.kristiania.exam.controllers.Author;

import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.TestData;
import no.kristiania.exam.dao.AuthorDao;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditAuthorTest {

    private final AuthorDao authorDao = new AuthorDao(TestData.testDataSource());

    @Test
    void shouldAlterAuthor() throws SQLException {
        Author author = exampleAuthor();

        author.setNewName("Eskil Blikeng");
        author.setNewAge(23);
        author.setNewBooks("Hvordan bli ekstrovert");

        authorDao.alter(author);

        assertAll(
                () -> assertEquals(author.getNewName(), "Eskil Blikeng"),
                () -> assertEquals(author.getNewAge(), 23),
                () -> assertEquals(author.getNewBooks(), "Hvordan bli ekstrovert")
        );
    }

    private Author exampleAuthor() {
        Author author = new Author();
        author.setName("Howsa Mibals");
        author.setAge(34);
        author.setBooks("hah, gottem");

        return author;
    }

    @AfterAll
    public static void clean(){
        try {
            TestData.cleanDataSource(TestData.testDataSource());
        } catch (Exception e) {
            Fail.fail(e.getMessage());
        }
    }
}
