package org.scribe.model;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class ResponseTest {

    private Response response;
    private ConnectionStub connection;

    @Before
    public void setup() throws Exception {
        connection = new ConnectionStub();
        connection.addResponseHeader("one", "one");
        connection.addResponseHeader("two", "two");
        response = new ResponseHttpImpl(connection);
    }

    @Test
    public void shouldPopulateResponseHeaders() {
        assertEquals(2, response.getHeaders().size());
        assertEquals("one", response.getHeader("one"));
    }

    @Test
    public void shouldParseBodyContents() {
        assertEquals("contents", response.getBody());
        assertEquals(1, connection.getTimesCalledInpuStream());
    }

    @Test
    public void shouldParseBodyContentsOnlyOnce() {
        assertEquals("contents", response.getBody());
        assertEquals("contents", response.getBody());
        assertEquals("contents", response.getBody());
        assertEquals(1, connection.getTimesCalledInpuStream());
    }

    @Test
    public void shouldHandleAConnectionWithErrors() throws Exception {
        final Response errResponse = new ResponseHttpImpl(new FaultyConnection());
        assertEquals(400, errResponse.getCode());
        assertEquals("errors", errResponse.getBody());
    }

    private static class FaultyConnection extends ConnectionStub {

        public FaultyConnection() throws Exception {
            super();
        }

        public InputStream getErrorStream() {
            return new ByteArrayInputStream("errors".getBytes());
        }

        public int getResponseCode() throws IOException {
            return 400;
        }
    }
}
