package no.kristiania.exam.controllers.Books;

import no.kristiania.exam.Objects.Book;
import no.kristiania.exam.TestData;
import no.kristiania.exam.dao.BookDao;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBooksTest {

    BookDao bookDao = new BookDao(TestData.testDataSource());

    @Test
    void shouldRetrieveAuthor() throws SQLException {
        Book book = new Book();
        book.setBookName("Book 101");
        bookDao.saveForTest(book);
        Book book1 = new Book();
        book1.setBookName("Book 202");
        bookDao.saveForTest(book1);

        assertThat(bookDao.listBooks())
                .extracting(Book::getBookName)
                .contains(book.getBookName(), book1.getBookName());
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
