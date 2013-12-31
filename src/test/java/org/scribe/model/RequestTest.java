package org.scribe.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class RequestTest {
    private RequestHttpImpl getRequest;
    private RequestHttpImpl postRequest;
    private ConnectionStub connection;

    @Before
    public void setup() throws Exception {
        connection = new ConnectionStub();
        postRequest = new RequestHttpImpl(Verb.POST, "http://example.com");
        postRequest.addBodyParameter("param", "value");
        postRequest.addBodyParameter("param with spaces", "value with spaces");
        //        postRequest.setConnection(connection);
        getRequest = new RequestHttpImpl(Verb.GET,
                "http://example.com?qsparam=value&other+param=value+with+spaces");
        //        getRequest.setConnection(connection);
    }

    @Test
    public void shouldSetRequestVerb() throws IOException {
        getRequest.send();
        assertEquals("GET", connection.getRequestMethod());
    }

    @Test
    public void shouldGetQueryStringParameters() {
        assertEquals(2, getRequest.getQueryStringParams().size());
        assertEquals(0, postRequest.getQueryStringParams().size());
        assertTrue(getRequest.getQueryStringParams().contains(new Parameter("qsparam", "value")));
    }

    @Test
    public void shouldAddRequestHeaders() throws IOException {
        getRequest.addHeader("Header", "1");
        getRequest.addHeader("Header2", "2");
        getRequest.send();
        assertEquals(2, getRequest.getHeaders().size());
        assertEquals(2, connection.getHeaders().size());
    }

    @Test
    public void shouldSetBodyParamsAndAddContentLength() throws IOException {
        assertEquals("param=value&param%20with%20spaces=value%20with%20spaces",
                postRequest.getBodyContents());
        postRequest.send();
        assertTrue(connection.getHeaders().containsKey("Content-Length"));
    }

    @Test
    public void shouldSetPayloadAndHeaders() throws IOException {
        postRequest.addPayload("PAYLOAD");
        postRequest.send();
        assertEquals("PAYLOAD", postRequest.getBodyContents());
        assertTrue(connection.getHeaders().containsKey("Content-Length"));
    }

    @Test
    public void shouldAllowAddingQuerystringParametersAfterCreation() {
        final Request request = new RequestHttpImpl(Verb.GET, "http://example.com?one=val");
        request.addQuerystringParameter("two", "other val");
        request.addQuerystringParameter("more", "params");
        assertEquals(3, request.getQueryStringParams().size());
    }

    @Test
    public void shouldReturnTheCompleteUrl() {
        final RequestHttpImpl request = new RequestHttpImpl(Verb.GET, "http://example.com?one=val");
        request.addQuerystringParameter("two", "other val");
        request.addQuerystringParameter("more", "params");
        assertEquals("http://example.com?one=val&two=other%20val&more=params",
                request.getCompleteUrl());
    }

    @Test
    public void shouldHandleQueryStringSpaceEncodingProperly() {
        assertTrue(getRequest.getQueryStringParams().contains(
                new Parameter("other param", "value with spaces")));
    }

    @Test
    public void shouldAutomaticallyAddContentTypeForPostRequestsWithBytePayload()
            throws IOException {
        postRequest.addPayload("PAYLOAD".getBytes());
        postRequest.send();
        assertEquals(Request.DEFAULT_CONTENT_TYPE, connection.getHeaders().get("Content-Type"));
    }

    @Test
    public void shouldAutomaticallyAddContentTypeForPostRequestsWithStringPayload()
            throws IOException {
        postRequest.addPayload("PAYLOAD");
        postRequest.send();
        assertEquals(Request.DEFAULT_CONTENT_TYPE, connection.getHeaders().get("Content-Type"));
    }

    @Test
    public void shouldAutomaticallyAddContentTypeForPostRequestsWithBodyParameters()
            throws IOException {
        postRequest.send();
        assertEquals(Request.DEFAULT_CONTENT_TYPE, connection.getHeaders().get("Content-Type"));
    }

    @Test
    public void shouldBeAbleToOverrideItsContentType() throws IOException {
        postRequest.addHeader("Content-Type", "my-content-type");
        postRequest.send();
        assertEquals("my-content-type", connection.getHeaders().get("Content-Type"));
    }

    @Test
    public void shouldNotAddContentTypeForGetRequests() throws IOException {
        getRequest.send();
        assertFalse(connection.getHeaders().containsKey("Content-Type"));
    }
}