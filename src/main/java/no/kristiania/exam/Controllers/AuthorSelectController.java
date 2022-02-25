package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.dao.AuthorDao;

import java.io.IOException;
import java.sql.SQLException;

public class AuthorSelectController implements HttpControllerInterface {

    private final AuthorDao authorDao;
    public AuthorSelectController(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        StringBuilder res = new StringBuilder();
        int value = 0;
        for (Author a :
                authorDao.listAll()) {
            res.append("<option value=" + (value++) + ">" + a.getName() + "</option>");
        }
        return new HttpMessage("HTTP/1.1 200 OK", res.toString());
    }
}
