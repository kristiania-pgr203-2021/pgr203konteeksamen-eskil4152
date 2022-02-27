package no.kristiania.exam.Http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpClientTest {

    @Test
    void shouldReturnStatusCode() throws IOException {
        HttpClient httpClient = new HttpClient("httpbin.org", 80, "/html");
        assertEquals(200, httpClient.getStatusCode());
    }

    @Test
    void shouldReturnHeaders() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type"));
    }

    @Test
    void shouldReadBody() throws IOException {
        HttpClient httpClient = new HttpClient("httpbin.org", 80, "/html");
        assertTrue(httpClient.getMessageBody().startsWith("<!DOCTYPE html>"),
                "Expected HTML: " + httpClient.getMessageBody());
    }
}