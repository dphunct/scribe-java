package org.scribe.services;

import java.util.Random;

/**
 * Implementation of {@link TimestampService} using plain java classes.
 * 
 * @author Pablo Fernandez
 */
public class TimestampServiceImpl implements TimestampService {
    private Timer timer;

    /**
     * Default constructor. 
     */
    public TimestampServiceImpl() {
        timer = new Timer();
    }

    /**
     * {@inheritDoc}
     */
    public String getNonce() {
        final long ts = getTs();
        return String.valueOf(ts + timer.getRandomInteger());
    }

    /**
     * {@inheritDoc}
     */
    public String getTimestampInSeconds() {
        return String.valueOf(getTs());
    }

    private long getTs() {
        return timer.getMilis() / 1000;
    }

    void setTimer(final Timer timer) {
        this.timer = timer;
    }

    /**
     * Inner class that uses {@link System} for generating the timestamps.
     * 
     * @author Pablo Fernandez
     */
    static class Timer {
        private final Random rand = new Random();

        long getMilis() {
            return System.currentTimeMillis();
        }

        int getRandomInteger() {
            return rand.nextInt();
        }
    }

}
