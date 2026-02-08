package client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Clients {

    public static void main(String[] args) {
        int clients = Integer.parseInt(args[0]);
        ExecutorService pool = Executors.newFixedThreadPool(clients);
        for (int i = 0; i < clients; i++) {
            pool.submit(() -> {
                try (Socket socket = new Socket("localhost", 8087)) {

                    OutputStream out = socket.getOutputStream();
                    InputStream in = socket.getInputStream();

                    out.write("PING\n".getBytes());
                    out.flush();

                    byte[] buf = new byte[1024];
                    int read = in.read(buf);

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " <- " + new String(buf, 0, read));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        pool.shutdown();
    }
}
