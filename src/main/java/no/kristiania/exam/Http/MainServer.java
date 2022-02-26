package no.kristiania.exam.Http;

import no.kristiania.exam.Controllers.*;
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

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
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

        //httpServer.addController("/api/filterBook", new FilterBooksController(bookDao, authorDao));

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
        return dataSource;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

}
