package no.kristiania.exam.controllers.Author;

import no.kristiania.exam.Controllers.Author.AuthorSelectController;
import no.kristiania.exam.Http.HttpClient;
import no.kristiania.exam.Http.HttpServer;
import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.TestData;
import no.kristiania.exam.dao.AuthorDao;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorSelectControllerTest {
    HttpServer server = new HttpServer(0);

    private final AuthorDao authorDao = new AuthorDao(TestData.testDataSource());

    public AuthorSelectControllerTest() throws IOException {
    }

    @Test
    void shouldListAuthorsInASelect() throws SQLException, IOException {
        Author author = exampleAuthor();
        Author author2 = exampleAuthor2();
        Author author3 = exampleAuthor3();

        authorDao.save(author);
        authorDao.save(author2);
        authorDao.save(author3);

        server.addController("/api/authorSelect", new AuthorSelectController(authorDao));

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/authorSelect");

        assertEquals(
                // Had to add value 0-2 as other people than test, as V003 adds three people to table when table is created
                "<option value=0>Lars Bjornbak</option><option value=1>Benedict Cumberbatch</option><option value=2>Batman the First</option>" +
                        "<option value=3>Howsa Mibals</option><option value=4>Tasti Miknuts</option><option value=5>Big Brain</option>",
                client.getMessageBody()
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

    private Author exampleAuthor2() {
        Author author = new Author();
        author.setName("Tasti Miknuts");
        author.setAge(64);
        author.setBooks("Rick Rolled");

        return author;
    }
    private Author exampleAuthor3() {
        Author author = new Author();
        author.setName("Big Brain");
        author.setAge(420);
        author.setBooks("Bigger Heart");

        return author;
    }
}
