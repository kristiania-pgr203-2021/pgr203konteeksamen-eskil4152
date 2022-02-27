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
        Book book = new Book();
        book.setBookName("Book one");

        Book book1 = new Book();
        book1.setBookName("Book two");

        Book book2 = new Book();
        book2.setBookName("Book three");

        bookDao.saveForTest(book);
        bookDao.saveForTest(book1);
        bookDao.saveForTest(book2);

        server.addController("/api/booksSelect", new BooksSelectController(bookDao));

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/booksSelect");

        assertEquals(
                "<option value=0>Book one</option><option value=1>Book two</option><option value=2>Book three</option>",
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
