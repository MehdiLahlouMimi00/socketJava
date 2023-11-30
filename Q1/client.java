package Q1;

import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class client {

    // driver code
    public static void main(String[] args) {
        // establish a connection by providing host and port number
        try (Socket socket = new Socket("localhost", 1234)) {

            // writing to server
            OutputStream out = socket.getOutputStream();

            // reading from server
            InputStream in = socket.getInputStream();

            // object of scanner class
            Scanner sc = new Scanner(System.in);
            String line = null;

            while (!"exit".equalsIgnoreCase(line)) {

                // reading from user
                line = sc.nextLine();

                // sending the user input to server
                byte[] bytes = line.getBytes();
                out.write(bytes);
                out.flush();

                // displaying server reply
                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                System.out.println("Server replied "
                        + new String(buffer, 0, bytesRead));
            }

            // closing the scanner object
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}