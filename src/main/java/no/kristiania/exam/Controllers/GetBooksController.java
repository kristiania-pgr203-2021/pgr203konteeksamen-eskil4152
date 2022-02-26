package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.dao.BookDao;
import no.kristiania.exam.Objects.Book;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

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
            res.append("<p><div>")
                    .append("Book title: ")
                    .append(book.getBookName())
                    .append(", Book genre: ")
                    .append(book.getBookGenre())
                    .append(", Description: ")
                    .append(book.getBookDesc())
                    .append(", Author(s): ")
                    .append(book.getBook_authors())
                    .append("</p></div>");
        }
        return new HttpMessage("HTTP/1.1 200 OK", res.toString());
    }
}
