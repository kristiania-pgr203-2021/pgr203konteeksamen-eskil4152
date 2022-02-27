package no.kristiania.exam.Controllers.Books;

import no.kristiania.exam.Controllers.HttpControllerInterface;
import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.dao.AuthorDao;
import no.kristiania.exam.dao.BookDao;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class FilterBooksController implements HttpControllerInterface {
    private final BookDao bookDao;
    private final AuthorDao authorDao;

    public FilterBooksController(BookDao bookDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        StringBuilder res = new StringBuilder();

        String title = queryMap.get("authorName");
        String titleToString = URLDecoder.decode(title, StandardCharsets.UTF_8);

        Author author = new Author();
        author.setName(titleToString);

        for (int i = 0; i < authorDao.listAll().size(); i++) {
            if (author.getName().equalsIgnoreCase(authorDao.listAll().get(i).getName())) {
                if (bookDao.booksByAuthor(author).size() == 0 || bookDao.booksByAuthor(author).get(i) == null) {
                    res.append("Author has no books");
                } else {
                    res.append(bookDao.booksByAuthor(authorDao.listAll().get(i)));
                }
            }
        }

        return new HttpMessage("HTTP/1.1 200 OK", res.toString());
    }
}
