package server;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ServerMetrics {
    public static final AtomicLong accepted = new AtomicLong();
    public static final AtomicLong queued = new AtomicLong();
    public static final AtomicLong dequeued = new AtomicLong();
    public static final AtomicLong completed = new AtomicLong();
    public static final AtomicLong errors = new AtomicLong();
    public static final AtomicInteger activeWorkers = new AtomicInteger();
}
