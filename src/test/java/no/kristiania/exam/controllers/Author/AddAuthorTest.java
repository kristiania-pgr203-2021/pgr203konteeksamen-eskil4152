package no.kristiania.exam.controllers.Author;


import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.TestData;
import no.kristiania.exam.dao.AuthorDao;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddAuthorTest {

    private final AuthorDao authorDao = new AuthorDao(TestData.testDataSource());

    @Test
    void shouldSaveAuthor() throws SQLException, UnsupportedEncodingException {
        Author author = new Author();
        String name = "Test Perssån";
        int age = 20;
        author.setName(URLDecoder.decode(name, StandardCharsets.UTF_8.name()));
        author.setAge(age);
        authorDao.save(author);

        Assertions.assertAll(
                //Checking author in position 2 as three authors are being added from the .sql files
                () -> assertEquals(authorDao.listAll().get(3).getName(), name),
                            () -> assertEquals(authorDao.listAll().get(3).getAge(), age)
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
}

