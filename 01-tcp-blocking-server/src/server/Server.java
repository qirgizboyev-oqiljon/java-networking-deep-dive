package server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
public class Server {
    public static void main(String[] args) throws IOException {
        int workers = Integer.parseInt(System.getProperty("workers", "1"));
        int backlog = Integer.parseInt(System.getProperty("backlog", "50"));
        int blockingQueueCapacity = Integer.parseInt(System.getProperty("blockingQueueCapacity", "50"));
        ServerSocket serverSocket = new ServerSocket(8087,  backlog);
        System.out.println("Server started");
        BlockingQueue<Socket> queue = new ArrayBlockingQueue<>(blockingQueueCapacity);

        ServerMonitor.start(queue);
        // start acceptor
        new Thread(new Acceptor(serverSocket, queue), "acceptor").start();

        // start workers
        for (int i = 0; i < workers; i++) {
            new Thread(new Worker(queue), "worker-" + i).start();
        }
    }
}
