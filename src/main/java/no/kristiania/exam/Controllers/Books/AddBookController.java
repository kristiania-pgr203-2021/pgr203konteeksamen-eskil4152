package no.kristiania.exam.Controllers.Books;

import no.kristiania.exam.Controllers.HttpControllerInterface;
import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.dao.AuthorDao;
import no.kristiania.exam.dao.BookDao;
import no.kristiania.exam.Objects.Book;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class AddBookController implements HttpControllerInterface {
    private final BookDao bookDao;
    private final AuthorDao authorDao;

    public AddBookController(BookDao bookDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws UnsupportedEncodingException, SQLException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Book book = new Book();

        String author = queryMap.get("authorName");
        String authorString = "";

        for (int i = 0; i < authorDao.listAll().size(); i++) {
            if (i == Integer.parseInt(author)){
                authorString = String.valueOf((authorDao.listAll().get(i)));
            }
        }

        book.setBook_authors(URLDecoder.decode(authorString, StandardCharsets.UTF_8.name()));

        book.setBookName(URLDecoder.decode(queryMap.get("title"), StandardCharsets.UTF_8.name()));
        book.setBookDesc(URLDecoder.decode(queryMap.get("description"), StandardCharsets.UTF_8.name()));
        book.setBookGenre(URLDecoder.decode(queryMap.get("bookGenre"), StandardCharsets.UTF_8.name()));

        bookDao.save(book);

        return new HttpMessage("HTTP/1.1 303 Redirect", "", "/index.html");
    }
}
