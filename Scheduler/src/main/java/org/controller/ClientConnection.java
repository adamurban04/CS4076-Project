package org.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection {
    private static ClientConnection instance;
    private Socket socket;
    private PrintWriter out;
    private Scanner in;

    private ClientConnection() throws IOException {
        socket = new Socket("localhost", 1234); // Connect to server
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new Scanner(socket.getInputStream());
    }

    public static ClientConnection getInstance() throws IOException {
        if (instance == null) {
            instance = new ClientConnection();
        }
        return instance;
    }

    public String sendRequest(String request) {
        out.println(request); // Send request
        return in.nextLine(); // Read response
    }

    public void closeConnection() throws IOException {
        if (socket != null) {
            socket.close();
            instance = null; // Allow reconnection if needed
        }
    }
}
