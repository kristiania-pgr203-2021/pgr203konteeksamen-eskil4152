package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.dao.AuthorDao;
import no.kristiania.exam.dao.BookDao;

import java.io.IOException;
import java.sql.SQLException;

public class FilterBooksController implements HttpControllerInterface {
    private final BookDao bookDao;
    private final AuthorDao authorDao;

    public FilterBooksController(BookDao bookDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        StringBuilder res = new StringBuilder();

        Author a = new Author();
        a.setName(String.valueOf(HttpMessage.parseRequestParameters("filter")));
        res.append(authorDao.listBooksFiltered(a));

        return new HttpMessage("HTTP/1.1 200 OK", res.toString());
    }
}
