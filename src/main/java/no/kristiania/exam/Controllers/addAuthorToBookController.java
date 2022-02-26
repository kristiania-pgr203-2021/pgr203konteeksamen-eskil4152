package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.Objects.Book;
import no.kristiania.exam.dao.AuthorDao;
import no.kristiania.exam.dao.BookDao;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class addAuthorToBookController implements HttpControllerInterface {
    private AuthorDao authorDao;
    private BookDao bookDao;

    public addAuthorToBookController(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Book book = new Book();

        String name = queryMap.get("authorName");
        String nameToString = "";

        for (int i = 0; i < authorDao.listAll().size(); i++) {
            if (i == Integer.parseInt(name)) {
                nameToString = String.valueOf(authorDao.listAll().get(i));
            }
        }

        String title = queryMap.get("bookTitle");
        String titleToString = "";

        for (int i = 0; i < bookDao.listBooks().size(); i++) {
            if (i == Integer.parseInt(title)){
                titleToString = String.valueOf((bookDao.listBooks().get(i)));
            }
        }

        book.setBookName(URLDecoder.decode(titleToString, StandardCharsets.UTF_8.name()));
        book.setNewAuthor(URLDecoder.decode(nameToString, StandardCharsets.UTF_8.name()));

        bookDao.addAuthorToBook(book);

        return new HttpMessage("HTTP/1.1 303 Redirect", "", "/books.html");
    }
}
