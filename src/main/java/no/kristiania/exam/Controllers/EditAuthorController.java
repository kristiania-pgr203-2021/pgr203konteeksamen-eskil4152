package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;
import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.dao.AuthorDao;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class EditAuthorController implements HttpControllerInterface {
    public AuthorDao authorDao;
    public EditAuthorController(AuthorDao authorDao){
        this.authorDao = authorDao;
    }


    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Author author = new Author();
        String name = queryMap.get("authorName");
        String nameToString = "";

        for (int i = 0; i < authorDao.listAll().size(); i++) {
            if (i == Integer.parseInt(name)) {
                nameToString = String.valueOf(authorDao.listAll().get(i));
            }
        }
        author.setName(URLDecoder.decode(nameToString, StandardCharsets.UTF_8.name()));
        author.setNewName(URLDecoder.decode(queryMap.get("newAuthorName"), StandardCharsets.UTF_8.name()));
        author.setNewAge(Integer.parseInt(URLDecoder.decode(queryMap.get("newAuthorAge"), StandardCharsets.UTF_8.name())));

        authorDao.alter(author);

        return new HttpMessage("HTTP/1.1 303 Redirect", "", "/index.html");
    }
}
