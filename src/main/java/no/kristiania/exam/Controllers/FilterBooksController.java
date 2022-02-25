package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.Objects.Book;
import no.kristiania.exam.dao.BookDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class FilterBooksController implements HttpControllerInterface {
    private final BookDao bookDao;

    public FilterBooksController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        StringBuilder res = new StringBuilder();
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);

        String name = queryMap.get("filter");

        for (Book book:
                bookDao.listBooksFiltered()) {
            res.append("<div><p>Book title: " + book.getBookName() + ", Book genre: " + book.getBookGenre() + ", Description: " + book.getBookDesc() + "</p></div>");
        }
        return new HttpMessage("HTTP/1.1 200 OK", res.toString());
    }
}
