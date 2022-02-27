package no.kristiania.exam.Http;

import no.kristiania.exam.Controllers.Author.AuthorSelectController;
import no.kristiania.exam.Controllers.Author.GetAuthorsController;
import no.kristiania.exam.Controllers.Books.BooksSelectController;
import no.kristiania.exam.Controllers.Books.GetBooksController;
import no.kristiania.exam.Controllers.EmptyTargetController;
import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.Objects.Book;
import no.kristiania.exam.TestData;
import no.kristiania.exam.dao.AuthorDao;
import no.kristiania.exam.dao.BookDao;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {
    private final HttpServer server = new HttpServer(0);

    public HttpServerTest() throws IOException {
    }

    @Test
    void shouldReturn404() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/non-existing");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReturnRequestTargetFor404() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/non-existing");
        assertEquals("File not found: /non-existing", client.getMessageBody());
    }

    @Test
    void shouldReturn200() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/index.html");
        assertEquals(200, client.getStatusCode());
    }

    @Test
    void shouldHandleMoreThanOneRequest() throws IOException {
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html")
                .getStatusCode());
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html")
                .getStatusCode());
    }

    @Test
    void shouldServeFiles() throws IOException {
        Paths.get("target/test-classes");

        String fileContent = "File made at " + LocalTime.now();
        Files.write(Paths.get("target/test-classes/test-file.txt"), fileContent.getBytes());

        HttpClient client = new HttpClient("localhost", server.getPort(), "/test-file.txt");
        assertEquals(fileContent, client.getMessageBody());
        assertEquals("text/plain", client.getHeader("Content-Type"));
    }

    @Test
    void shouldLetFileExtensionsDefineContentTypes() throws IOException {
        Paths.get("target/test-classes");

        String fileContent = "<p>Hello</p>";
        Files.write(Paths.get("target/test-classes/test-file.html"), fileContent.getBytes());

        HttpClient client = new HttpClient("localhost", server.getPort(), "/test-file.html");
        assertEquals("text/html", client.getHeader("Content-Type"));
    }

    @Test
    void shouldHandleMultipleRequests() throws IOException {
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html").
                getStatusCode());
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html").
                getStatusCode());
    }

    @Test
    void shouldReturnIndex() throws IOException {
        server.addController("/", new EmptyTargetController());
        HttpClient client = new HttpClient("localhost", server.getPort(), "/");

        assertThat(client.getMessageBody())
                .contains("Redirecting to index");
    }

    @Test
    void shouldListBooksAsSelect() throws IOException, SQLException {
        BookDao bookDao = new BookDao(TestData.testDataSource());

        Book book = new Book();
        book.setBookName("Book one");

        Book book1 = new Book();
        book1.setBookName("Book two");

        Book book2 = new Book();
        book2.setBookName("Book three");

        bookDao.saveForTest(book);
        bookDao.saveForTest(book1);
        bookDao.saveForTest(book2);

        server.addController("/api/booksSelect", new BooksSelectController(bookDao));

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/booksSelect");

        assertEquals(
                "<option value=0>Book one</option><option value=1>Book two</option><option value=2>Book three</option>",
                client.getMessageBody()
        );
    }

    @Test
    void shouldGetBooks() throws SQLException, IOException {
        BookDao bookDao = new BookDao(TestData.testDataSource());

        Book book = new Book();
        book.setBookName("Book");

        Book book1 = new Book();
        book1.setBookName("Book 1");

        bookDao.saveForTest(book);
        bookDao.saveForTest(book1);

        server.addController("/api/getBooks", new GetBooksController(bookDao));

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/getBooks");

        assertThat(client.getMessageBody())
                .contains(book.getBookName())
                .contains(book1.getBookName());
    }

    @Test
    void shouldGetAuthors() throws SQLException, IOException {
        AuthorDao authorDao = new AuthorDao(TestData.testDataSource());
        BookDao bookDao = new BookDao(TestData.testDataSource());

        Author author = new Author();
        author.setName(URLDecoder.decode("Erik", StandardCharsets.UTF_8));
        author.setAge(25);
        authorDao.save(author);

        Author author1 = new Author();
        author1.setName(URLDecoder.decode("Lasse", StandardCharsets.UTF_8));
        author1.setAge(21);
        authorDao.save(author1);

        server.addController("/api/getAuthors", new GetAuthorsController(authorDao, bookDao));
        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/getAuthors");

        assertThat(client.getMessageBody())
                .contains(author.getName())
                .contains(author1.getName());
    }

    @Test
    void ListAuthorsAsSelect() throws IOException, SQLException {
        AuthorDao authorDao = new AuthorDao(TestData.testDataSource());

        Author author = new Author();
        author.setName("Author one");

        Author author1 = new Author();
        author1.setName("Author two");

        authorDao.save(author);
        authorDao.save(author1);

        server.addController("/api/authorSelect", new AuthorSelectController(authorDao));

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/authorSelect");

        assertEquals(
                // Had to add value 0-2 as other people than test, as V003 adds three people to table when table is created
                "<option value=0>Lars Bjornbak</option><option value=1>Benedict Cumberbatch</option><option value=2>Batman the First</option>" +
                        "<option value=3>Author one</option><option value=4>Author two</option>",
                client.getMessageBody()
        );
    }

    @AfterEach
    public void clean(){
        try {
            TestData.cleanDataSource(TestData.testDataSource());
        } catch (Exception e) {
            Fail.fail(e.getMessage());
        }
    }
}
