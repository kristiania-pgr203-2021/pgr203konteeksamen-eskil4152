package no.kristiania.exam.Controllers;

import no.kristiania.exam.Http.HttpMessage;

import java.io.IOException;
import java.sql.SQLException;

public interface HttpControllerInterface {
    HttpMessage handle(HttpMessage request) throws SQLException, IOException;
}
