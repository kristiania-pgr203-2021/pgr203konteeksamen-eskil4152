package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.dao.AuthorDao;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class AddAuthorController implements HttpControllerInterface {

    private final AuthorDao authorDao;

    public AddAuthorController (AuthorDao authorDao){
        this.authorDao = authorDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Author author = new Author();

        author.setName(URLDecoder.decode(queryMap.get("name"), StandardCharsets.UTF_8.name()));
        author.setAge(Integer.parseInt(URLDecoder.decode(queryMap.get("age"), StandardCharsets.UTF_8.name())));

        authorDao.save(author);
        return new HttpMessage("HTTP/1.1 303 Redirect", "", "/index.html");
    }
}
