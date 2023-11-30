package Q3;


import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class client {

    // driver code
    public static void main(String[] args) {
        // establish a connection by providing the host and port number
        try (Socket socket = new Socket("localhost", 1234)) {

            // writing to the server
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // reading from the server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // object of scanner class
            Scanner sc = new Scanner(System.in);
            Object object = null;

            while (true) {
                // reading the object from user
                System.out.print("Enter an object (or type 'exit' to quit): ");
                String input = sc.nextLine();

                if ("exit".equalsIgnoreCase(input)) {
                    break;
                }

                // assuming the user input is a String object, you can modify this part based on your actual objects
                object = input;

                // sending the object to the server
                out.writeObject(object);
                out.flush();

                // displaying server reply
                Object serverReply = in.readObject();
                System.out.println("Server replied: " + serverReply);
            }

            // closing the scanner object
            sc.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
