package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerMonitor {
    static void start(BlockingQueue<?> queue) {

        ScheduledExecutorService monitor = Executors.newSingleThreadScheduledExecutor();

        monitor.scheduleAtFixedRate(() -> System.out.printf(
                "[%s][STATS] accepted=%d queued=%d dequeued=%d completed=%d " +
                        "activeWorkers=%d queueSize=%d errors=%d%n",
                ts(),
                ServerMetrics.accepted.get(),
                ServerMetrics.queued.get(),
                ServerMetrics.dequeued.get(),
                ServerMetrics.completed.get(),
                ServerMetrics.activeWorkers.get(),
                queue.size(),
                ServerMetrics.errors.get()
        ), 1, 1, TimeUnit.SECONDS);
    }
    static String ts() {
        return java.time.LocalTime.now().toString();
    }
}
