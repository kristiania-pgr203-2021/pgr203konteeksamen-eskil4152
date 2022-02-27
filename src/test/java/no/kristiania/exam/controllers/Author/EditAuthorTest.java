package no.kristiania.exam.controllers.Author;

import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.TestData;
import no.kristiania.exam.dao.AuthorDao;
import org.assertj.core.api.Fail;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditAuthorTest {

    private final AuthorDao authorDao = new AuthorDao(TestData.testDataSource());

    @Test
    void shouldAlterAuthor() throws SQLException {
        Author author = exampleAuthor();

        author.setNewName("Eskil Bilkeng");
        author.getName();
        author.setNewAge(23);
        author.getAge();
        author.setNewBooks("Hvordan bli ekstrovert");
        author.getBooks();

        authorDao.alter(author);

        assertAll(
                () -> assertEquals(author.getNewName(), "Eskil Bilkeng"),
                () -> assertEquals(author.getNewAge(), 23),
                () -> assertEquals(author.getNewBooks(), "Hvordan bli ekstrovert")
        );
    }

    @AfterAll
    public static void clean(){
        try {
            TestData.cleanDataSource(TestData.testDataSource());
        } catch (Exception e) {
            Fail.fail(e.getMessage());
        }
    }

    private Author exampleAuthor() {
        Author author = new Author();
        author.setName("Howsa Mibals");
        author.setAge(34);
        author.setBooks("hah, gottem");

        return author;
    }
}
