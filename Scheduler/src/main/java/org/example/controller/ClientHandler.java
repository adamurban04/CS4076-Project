package org.example.controller;

import org.example.model.Timetable;

import java.net.Socket;
import java.io.*;

public class ClientHandler implements Runnable{
    private Socket socket;
    private Timetable timetable;

    public ClientHandler(Socket socket, Timetable timetable) {  //each client has socket and timetable
        this.socket = socket;
        this.timetable = timetable;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String request; // important String variable for handling requests
            while ((request = in.readLine()) != null) {
                System.out.println("Received Message: " + request);

                // Process request
                String response = RequestProcessor.processRequest(request, timetable);
                out.println(response); // Send response to client
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
