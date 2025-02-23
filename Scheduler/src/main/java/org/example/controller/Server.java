package org.example.controller;
import java.io.*;
import java.net.*;

//Server side for TCP Connection between clients and the server

public class Server {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            ServerSocket servSock = new ServerSocket(PORT); // Create server socket
            System.out.println("Server is running on port: " + PORT);

            while (true) {
                Socket link = servSock.accept(); // Accept client connection
                System.out.println("Client connected: " + link.getInetAddress());

                // Create streams for input and output
                BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                PrintWriter out = new PrintWriter(link.getOutputStream(), true);

                // Read client message
                String clientMessage = in.readLine();
                if (clientMessage != null) {  // Ensure the message isn't null
                    System.out.println("Received: " + clientMessage);

                    // Send response to client
                    out.println("Message received: (" + clientMessage + ")");
                }

                link.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
