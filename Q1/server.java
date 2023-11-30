package Q1;

import java.io.*;
import java.net.*;

// Server class
class server {
    public static void main(String[] args) {
        ServerSocket server = null;

        try {

            // server is listening on port 1234
            server = new ServerSocket(1234);
            server.setReuseAddress(true);

            // running infinite loop for getting client requests
            while (true) {

                // creating a socket object to receive incoming client requests
                Socket client = server.accept();

                // Displaying that a new client is connected to the server
                System.out.println("New client connected: "
                        + client.getInetAddress()
                        .getHostAddress());

                // create a new thread object
                ClientHandler clientSock
                        = new ClientHandler(client);

                // This thread will handle the client separately
                new Thread(clientSock).start();
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
            OutputStream out = null;
            InputStream in = null;
            try {

                // get the output stream of the client
                out = clientSocket.getOutputStream();

                // get the input stream of the client
                in = clientSocket.getInputStream();

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {

                    // writing the received message from the client
                    System.out.printf(
                            "Bytes received from the client: %s\n",
                            new String(buffer, 0, bytesRead));

                    // send the received bytes back to the client
                    out.write(buffer, 0, bytesRead);
                    out.flush();
                }
            } catch (IOException e) {
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