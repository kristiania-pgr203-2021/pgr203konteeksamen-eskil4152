package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.dao.BookDao;
import no.kristiania.exam.Objects.Book;

import java.io.IOException;
import java.sql.SQLException;

public class GetBooksController implements HttpControllerInterface {
    private final BookDao bookDao;

    public GetBooksController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        StringBuilder res = new StringBuilder();
        for (Book book:
             bookDao.listBooks()) {
            res.append("<div><p>Author: " + book.getBook_authors() + ", Book title: " + book.getBookName() + ", Book genre: " + book.getBookGenre() + ", Description: " + book.getBookDesc() + "</p></div>");
        }
        return new HttpMessage("HTTP/1.1 200 OK", res.toString());
    }
}