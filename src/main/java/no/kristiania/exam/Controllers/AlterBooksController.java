package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.Objects.BookDao;
import no.kristiania.exam.dao.Book;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class AlterBooksController implements HttpControllerInterface {

    public BookDao bookDao;
    public AlterBooksController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Book book = new Book();

        String title = queryMap.get("bookTitle");
        String titleToString = "";

        for (int i = 0; i < bookDao.listBooks().size(); i++) {
            if (i == Integer.parseInt(title)){
                titleToString = String.valueOf(bookDao.listBooks().get(i));
            }
        }

        book.setBook_name(URLDecoder.decode(titleToString, StandardCharsets.UTF_8.name()));

        book.setNew_name(URLDecoder.decode(queryMap.get("newBookTitle"), StandardCharsets.UTF_8.name()));
        book.setNew_desc(URLDecoder.decode(queryMap.get("newBookDescription"), StandardCharsets.UTF_8.name()));
        book.setNew_author(URLDecoder.decode(queryMap.get("newBookAuthor"), StandardCharsets.UTF_8.name()));

        bookDao.alter(book);

        return new HttpMessage("HTTP/1.1 303 Redirect", "", "/index.html");
    }
}
