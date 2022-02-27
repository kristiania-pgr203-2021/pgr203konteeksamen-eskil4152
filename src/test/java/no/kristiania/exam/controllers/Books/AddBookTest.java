package no.kristiania.exam.controllers.Books;

import no.kristiania.exam.Objects.Book;
import no.kristiania.exam.TestData;
import no.kristiania.exam.dao.BookDao;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddBookTest {

    BookDao bookDao = new BookDao(TestData.testDataSource());

    @Test
    void shouldAddBookToDb() throws SQLException, UnsupportedEncodingException {
        Book book = new Book();
        String name = "Test book";
        String genre = "Horror";
        String description = "Junit";
        String author = "ÆØÅ";

        book.setBookName(URLDecoder.decode(name, StandardCharsets.UTF_8.name()));
        book.setBookGenre(URLDecoder.decode(genre, StandardCharsets.UTF_8.name()));
        book.setBookDesc(URLDecoder.decode(description, StandardCharsets.UTF_8.name()));
        book.setBook_authors(URLDecoder.decode(author, StandardCharsets.UTF_8.name()));

        bookDao.saveForTest(book);

        Assertions.assertAll(
                () -> assertEquals(bookDao.listBooks().get(0).getBookName(), name),
                () -> assertEquals(bookDao.listBooks().get(0).getBookGenre(), genre),
                () -> assertEquals(bookDao.listBooks().get(0).getBookDesc(), description),
                () -> assertEquals(bookDao.listBooks().get(0).getBook_authors(), author)
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
