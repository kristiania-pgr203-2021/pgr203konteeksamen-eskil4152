package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.dao.AuthorDao;

import java.io.IOException;
import java.sql.SQLException;

public class GetAuthorsController implements HttpControllerInterface {

    private final AuthorDao authorDao;
    public GetAuthorsController(AuthorDao authorDao){
        this.authorDao = authorDao;
    }
    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        StringBuilder res = new StringBuilder();

        for (Author a :
                authorDao.listAll()) {
            res.append("<div><p>Name: " + a.getName() + ", Age: " + a.getAge() + ", Books: " + a.getBooks() + "</p></div>");
        }
        return new HttpMessage("HTTP/1.1 200 OK", res.toString());
    }
}
