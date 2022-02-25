package no.kristiania.exam.Http;

import no.kristiania.exam.Controllers.AddBookController;
import no.kristiania.exam.Controllers.GetBooksController;
import no.kristiania.exam.Objects.BookDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;

public class ExamServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(1962);

        DataSource datasource = Datasource.createDataSource();
        BookDao bookDao = new BookDao(datasource);

        httpServer.addController("/api/books", new AddBookController(bookDao));
        httpServer.addController("/api/getBooks", new GetBooksController(bookDao));
        /*httpServer.addController("/api/listQuestions", new ListQuestionsController(questionDao));
        httpServer.addController("/api/questionSelect", new QuestionSelectController(questionDao));
        httpServer.addController("/api/answer", new AnswerQuestionController(answerDao, questionDao));
        httpServer.addController("/api/newQuestion", new NewQuestionController(questionDao));
        httpServer.addController("/api/viewAnswers", new ViewAnswersController(answerDao));
        httpServer.addController("/api/alterQuestion", new AlterQuestionController(questionDao));
        httpServer.addController("/api/cookieAPI", new CookieCrumbController(userDao));*/


        logger.info("Starting http://localhost:{}/index.html", httpServer.getPort());
    }

}
