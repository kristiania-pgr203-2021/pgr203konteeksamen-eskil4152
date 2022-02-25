package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.Objects.Book;
import no.kristiania.exam.Objects.BookDao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class AddBookController implements HttpControllerInterface {
    private final BookDao bookDao;

    public AddBookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws UnsupportedEncodingException, SQLException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Book book = new Book();

        book.setBook_name(URLDecoder.decode(queryMap.get("title"), StandardCharsets.UTF_8.name()));
        book.setBook_desc(URLDecoder.decode(queryMap.get("description"), StandardCharsets.UTF_8.name()));
        book.setBook_authors(URLDecoder.decode(queryMap.get("authorId"), StandardCharsets.UTF_8.name()));

        bookDao.save(book);

        return new HttpMessage("HTTP/1.1 303 Redirect", "Done");
    }
}
