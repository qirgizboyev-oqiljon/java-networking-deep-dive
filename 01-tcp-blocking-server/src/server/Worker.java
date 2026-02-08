package server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

class Worker implements Runnable {
    private final BlockingQueue<Socket> queue;

    Worker(BlockingQueue<Socket> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try (Socket socket = queue.take()) { // BLOCKING
                ServerMetrics.dequeued.incrementAndGet();
                int active = ServerMetrics.activeWorkers.incrementAndGet();
                System.out.printf(
                        "[WORKER-START] activeWorkers=%d queueSize=%d%n",
                        active, queue.size()
                );
                // ==== SIMULYATSIYA UCHUN ====
                Thread.sleep(1000); // ? shu joy BIO'ni "sindiradi"

                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                byte[] buf = new byte[1024];
                int read = in.read(buf); // BLOCKING

                // business logic
                out.write(("OK\n").getBytes());
                out.flush();
            } catch (Exception e) {
                ServerMetrics.errors.incrementAndGet();
                e.printStackTrace();
            } finally {
                ServerMetrics.activeWorkers.decrementAndGet();
                ServerMetrics.completed.incrementAndGet();

                System.out.printf(
                        "[WORKER-END] activeWorkers=%d completed=%d%n",
                        ServerMetrics.activeWorkers.get(),
                        ServerMetrics.completed.get()
                );
            }
        }
    }
}
