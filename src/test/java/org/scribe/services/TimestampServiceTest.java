package org.scribe.services;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TimestampServiceTest {

    private TimestampServiceImpl service;
    private TimestampServiceImpl.Timer timerStub;

    @Before
    public void setup() {
        service = new TimestampServiceImpl();
        timerStub = new TimerStub();
        service.setTimer(timerStub);
    }

    @Test
    public void shouldReturnTimestampInSeconds() {
        final String expected = "1000";
        assertEquals(expected, service.getTimestampInSeconds());
    }

    @Test
    public void shouldReturnNonce() {
        final String expected = "1042";
        assertEquals(expected, service.getNonce());
    }

    private static class TimerStub extends TimestampServiceImpl.Timer {

        public long getMilis() {
            return 1000000L;
        }

        public int getRandomInteger() {
            return 42;
        }
    }
}
