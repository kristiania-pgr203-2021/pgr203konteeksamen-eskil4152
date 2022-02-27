package no.kristiania.exam.controllers.Books;

import no.kristiania.exam.Controllers.Books.FilterBooksController;
import no.kristiania.exam.Http.HttpClient;
import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.Http.HttpServer;
import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.Objects.Book;
import no.kristiania.exam.TestData;
import no.kristiania.exam.dao.AuthorDao;
import no.kristiania.exam.dao.BookDao;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterBooksControllerTest {

    private final AuthorDao authorDao = new AuthorDao(TestData.testDataSource());
    private final BookDao bookDao = new BookDao(TestData.testDataSource());

    HttpServer server = new HttpServer(0);

    public FilterBooksControllerTest() throws IOException {
    }

    @Test
    void shouldFilterBooks() throws SQLException, IOException {
        Book book = new Book();
        book.setBookName("How to write Java");
        book.setBookGenre("Learning");
        book.setBookDesc("Java for pros");
        book.setBook_authors("Lars Bjornbak");

        Book book1 = new Book();
        book.setBookName("How to write C#");
        book.setBookGenre("Comedy");
        book.setBookDesc("C# for beginners");
        book.setBook_authors("Lars Bjornbak");

        Book book2 = new Book();
        book.setBookName("How to write JS");
        book.setBookGenre("Horror");
        book.setBookDesc("JS for beginners");
        book.setBook_authors("Johannes Brodwall");

        bookDao.saveForTest(book);
        //bookDao.saveForTest(book1);
        //bookDao.saveForTest(book2);

        String query = "?";

        server.addController("/api/booksFilter", new FilterBooksController(bookDao, authorDao));

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/booksFilter");

        assertEquals(
                "[How to write Java]",
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
}
