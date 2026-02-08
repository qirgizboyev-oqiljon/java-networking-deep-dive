package server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Acceptor implements Runnable{
    private final ServerSocket serverSocket;
    private final BlockingQueue<Socket> queue;

    Acceptor(ServerSocket serverSocket, BlockingQueue<Socket> queue) {
        this.serverSocket = serverSocket;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept(); // BLOCKING
                long a = ServerMetrics.accepted.incrementAndGet();

                queue.put(socket); // worker'ga uzatamiz
                long q = ServerMetrics.queued.incrementAndGet();
                System.out.printf(
                        "[ACCEPTOR] accepted=%d queued=%d queueSize=%d%n",
                        a, q, queue.size()
                );
            } catch (Exception e) {
                ServerMetrics.errors.incrementAndGet();
                e.printStackTrace();
            }
        }
    }
}
