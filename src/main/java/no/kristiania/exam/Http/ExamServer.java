package no.kristiania.exam.Http;

import no.kristiania.exam.Controllers.AddBookController;
import no.kristiania.exam.Controllers.AlterBooksController;
import no.kristiania.exam.Controllers.BooksSelectController;
import no.kristiania.exam.Controllers.GetBooksController;
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

public class ExamServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private static DataSource dataSource;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(1962);

        dataSource = createDataSource();
        AuthorDao authorDao = new AuthorDao(dataSource);
        BookDao bookDao = new BookDao(dataSource);

        httpServer.addController("/api/books", new AddBookController(bookDao));
        httpServer.addController("/api/getBooks", new GetBooksController(bookDao));
        httpServer.addController("/api/booksSelect", new BooksSelectController(bookDao));
        httpServer.addController("/api/alterBook", new AlterBooksController(bookDao));
        /*httpServer.addController("/api/listQuestions", new ListQuestionsController(questionDao));
        httpServer.addController("/api/questionSelect", new QuestionSelectController(questionDao));
        httpServer.addController("/api/answer", new AnswerQuestionController(answerDao, questionDao));
        httpServer.addController("/api/newQuestion", new NewQuestionController(questionDao));
        httpServer.addController("/api/viewAnswers", new ViewAnswersController(answerDao));
        httpServer.addController("/api/alterQuestion", new AlterQuestionController(questionDao));
        httpServer.addController("/api/cookieAPI", new CookieCrumbController(userDao));*/

        logger.info("Starting http://localhost:{}/index.html", httpServer.getPort());
    }

    private static DataSource createDataSource() throws IOException {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader("pgr203.properties")) {
            properties.load(reader);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url",
                "jdbc:postgresql://localhost:5432/index"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword("dataSource.password");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

}
