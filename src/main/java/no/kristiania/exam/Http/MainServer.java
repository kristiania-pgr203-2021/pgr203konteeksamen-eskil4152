package no.kristiania.exam.Http;

import no.kristiania.exam.Controllers.Author.AddAuthorController;
import no.kristiania.exam.Controllers.Author.AuthorSelectController;
import no.kristiania.exam.Controllers.Author.EditAuthorController;
import no.kristiania.exam.Controllers.Author.GetAuthorsController;
import no.kristiania.exam.Controllers.Books.*;
import no.kristiania.exam.dao.AuthorDao;
import no.kristiania.exam.dao.BookDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainServer {

    public static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private static DataSource dataSource;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(1962);

        dataSource = createDataSource();
        AuthorDao authorDao = new AuthorDao(dataSource);
        BookDao bookDao = new BookDao(dataSource);

        httpServer.addController("/api/books", new AddBookController(bookDao, authorDao));
        httpServer.addController("/api/getBooks", new GetBooksController(bookDao));
        httpServer.addController("/api/booksSelect", new BooksSelectController(bookDao));
        httpServer.addController("/api/alterBook", new AlterBooksController(bookDao));
        httpServer.addController("/api/getAuthors", new GetAuthorsController(authorDao, bookDao));
        httpServer.addController("/api/createAuthor", new AddAuthorController(authorDao));
        httpServer.addController("/api/authorSelect", new AuthorSelectController(authorDao));
        httpServer.addController("/api/alterAuthor", new EditAuthorController(authorDao));
        httpServer.addController("/api/booksFilter", new FilterBooksController(bookDao, authorDao));
        httpServer.addController("/api/updateBook", new addAuthorToBookController(authorDao, bookDao));
        httpServer.addController("/api/booksFilter", new FilterBooksController(bookDao, authorDao));
        httpServer.addController("/api/updateBook", new addAuthorToBookController(authorDao, bookDao));

        logger.info("Starting http://localhost:{}/index.html", httpServer.getPort());
    }

    private static DataSource createDataSource() throws IOException {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader("pgr203.properties")) {
            properties.load(reader);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url",
                "jdbc:postgresql://localhost:5432/kontexamdb"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword("dataSource.password");
        Flyway.configure().dataSource(dataSource).load().baseline();
        return dataSource;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

}
