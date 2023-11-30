package Q4;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

// Server class
class server {
    public static void main(String[] args) {
        ServerSocket server = null;

        // Using a ThreadPoolExecutor for handling multiple clients concurrently
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {

            // server is listening on port 1234
            server = new ServerSocket(1234);
            server.setReuseAddress(true);

            // running an infinite loop for getting client requests
            while (true) {

                // creating a socket object to receive incoming client requests
                Socket client = server.accept();

                // Displaying that a new client is connected to the server
                System.out.println("New client connected: "
                        + client.getInetAddress().getHostAddress());

                // create a new thread object for the client and submit it to the executor service
                executorService.submit(new ClientHandler(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Shutting down the executor service gracefully
            executorService.shutdown();
        }
    }

    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        // Constructor
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            ObjectOutputStream out = null;
            ObjectInputStream in = null;
            try {

                // get the output stream of the client
                out = new ObjectOutputStream(clientSocket.getOutputStream());

                // get the input stream of the client
                in = new ObjectInputStream(clientSocket.getInputStream());

                // reading the object from the client
                Object receivedObject;
                while ((receivedObject = in.readObject()) != null) {
                    // handling the received object (you can replace this part with your logic)
                    System.out.println("Object received from client: " + receivedObject);

                    // sending an acknowledgement back to the client
                    out.writeObject("Server received the object: " + receivedObject);
                    out.flush();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
