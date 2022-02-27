package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class EmptyTargetController implements HttpControllerInterface {
    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        return new HttpMessage("HTTP/1.1 303 Redirect", "", "/index.html");
    }
}
