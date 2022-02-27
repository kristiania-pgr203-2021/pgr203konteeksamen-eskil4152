package no.kristiania.exam.controllers.Books;

import no.kristiania.exam.Controllers.Books.BooksSelectController;
import no.kristiania.exam.Http.HttpClient;
import no.kristiania.exam.Http.HttpServer;
import no.kristiania.exam.Objects.Book;
import no.kristiania.exam.TestData;
import no.kristiania.exam.dao.BookDao;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BooksSelectControllerTest  {
    HttpServer server = new HttpServer(0);

    private final BookDao bookDao = new BookDao(TestData.testDataSource());

    public BooksSelectControllerTest() throws IOException {
    }

    @Test
    void shouldListBooksInASelect() throws SQLException, IOException {
        assertEquals(2, 3);
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
