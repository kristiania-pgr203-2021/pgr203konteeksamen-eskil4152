package no.kristiania.exam.Controllers.Books;

import no.kristiania.exam.Controllers.HttpControllerInterface;
import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.dao.BookDao;
import no.kristiania.exam.Objects.Book;

import java.io.IOException;
import java.sql.SQLException;

public class BooksSelectController implements HttpControllerInterface {

    private final BookDao bookDao;

    public BooksSelectController(BookDao bookDao) {
        this.bookDao = bookDao;
    }


    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        StringBuilder res = new StringBuilder();
        int value = 0;
        for (Book book :
                bookDao.listBooks()) {
            res.append("<option value=" + (value++) + ">" + book.getBookName() + "</option>");
        }
        return new HttpMessage("HTTP/1.1 200 OK", res.toString());
    }
}
