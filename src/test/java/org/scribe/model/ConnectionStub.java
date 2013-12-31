package org.scribe.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionStub extends HttpURLConnection {

    private final Map<String, String> headers = new HashMap<String, String>();
    private final Map<String, List<String>> responseHeaders = new HashMap<String, List<String>>();
    private int inputStreamCalled = 0;

    public ConnectionStub() throws Exception {
        super(new URL("http://example.com"));
    }

    public void setRequestProperty(final String key, final String value) {
        headers.put(key, value);
    }

    public String getRequestProperty(final String s) {
        return headers.get(s);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getResponseCode() throws IOException {
        return 200;
    }

    public InputStream getInputStream() throws IOException {
        inputStreamCalled++;
        return new ByteArrayInputStream("contents".getBytes());
    }

    public int getTimesCalledInpuStream() {
        return inputStreamCalled;
    }

    public OutputStream getOutputStream() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write("contents".getBytes());
        return baos;
    }

    public Map<String, List<String>> getHeaderFields() {
        return responseHeaders;
    }

    public void addResponseHeader(final String key, final String value) {
        responseHeaders.put(key, Arrays.asList(value));
    }

    public void connect() throws IOException {
    }

    public void disconnect() {
    }

    public boolean usingProxy() {
        return false;
    }

}
