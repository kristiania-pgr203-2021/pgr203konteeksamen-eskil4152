package no.kristiania.exam.Controllers.Author;

import no.kristiania.exam.Controllers.HttpControllerInterface;
import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.Objects.Book;
import no.kristiania.exam.dao.AuthorDao;
import no.kristiania.exam.dao.BookDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetAuthorsController implements HttpControllerInterface {

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    public GetAuthorsController(AuthorDao authorDao, BookDao bookDao){
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        StringBuilder res = new StringBuilder();

        for (Author a :
                authorDao.listAll()) {

            ArrayList<Book> arrayList = new ArrayList<>();
            for (Book b :
                    bookDao.booksByAuthor(a)) {
                arrayList.add(b);
            }

            res.append("<div><p>Name: ")
                .append(a.getName())
                .append(", Age: ")
                .append(a.getAge())
                .append(", Books: ");
                res.append(arrayList);
                res.append("</p></div>");
        }
        return new HttpMessage("HTTP/1.1 200 OK", res.toString());
    }
}
