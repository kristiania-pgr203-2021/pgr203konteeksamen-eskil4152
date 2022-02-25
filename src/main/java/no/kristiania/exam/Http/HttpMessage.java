package no.kristiania.exam.Http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {
    public String startLine;
    public final Map<String, String> headerFields = new HashMap<>();
    public String messageBody;
    public String location;

    public HttpMessage(Socket socket) throws IOException {
        startLine = HttpMessage.readLine(socket);
        readInputHeaders(socket);
        if (headerFields.containsKey("Content-Length")) {
            messageBody = HttpMessage.readBytes(socket, getContentLength());
        }
    }

    public HttpMessage(String startLine, String messageBody) {
        this.startLine = startLine;
        this.messageBody = messageBody;
    }

    public HttpMessage(String startLine, String messageBody, String location){
        this.messageBody = messageBody;
        this.startLine = startLine;
        this.location = location;
    }

    static String readBytes(Socket socket, int contentLength) throws IOException {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            buffer.append((char)socket.getInputStream().read());
        }
        return buffer.toString();
    }

    static String readLine(Socket socket) throws IOException {
        StringBuilder buffer = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            buffer.append((char)c);
        }
        int expectedNewline = socket.getInputStream().read();
        assert expectedNewline == '\n';
        return buffer.toString();
    }

    public void write(Socket socket) throws IOException {
        String response = startLine + "\r\n" +
                "Content-Length: " + messageBody.getBytes().length + "\r\n" +
                "Connection: close\r\n" +
                "Content-Type: text/html; charset=utf-8" + "\r\n" +
                "Location: " + location + "\r\n" +
                "\r\n" +
                messageBody;
        socket.getOutputStream().write(response.getBytes());
    }

    public static Map<String, String> parseRequestParameters(String query) {
        Map<String, String> queryMap = new HashMap<>();
        for (String queryParameter : query.split("&")) {
            int equalPos = queryParameter.indexOf('=');
            String parameterName = queryParameter.substring(0, equalPos);
            String parameterValue = queryParameter.substring(equalPos+1);
            queryMap.put(parameterName, parameterValue);
        }
        return queryMap;
    }

    public static String getContentType(String path) {
        String contentType;
        contentType = "text/plain";
        if (path.endsWith(".html")){
            contentType = "text/html";
        } else if (path.endsWith(".css")){
            contentType = "text/css";
        }
        return contentType;
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader("Content-Length"));
    }

    public String getHeader (String headerName) {
        return headerFields.get(headerName);
    }

    public static HashMap<String, String> readInputHeaders(Socket socket) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        String[] split;
        String line = readLine(socket), key, value;

        while (!line.isEmpty()) {
            split = line.split(":");
            key = split[0];
            value = split[1].trim();
            headers.put(key, value);
            line = readLine(socket);
        }
        return headers;
    }


}