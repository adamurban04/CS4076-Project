package org.controller;

import org.exceptions.IncorrectActionException;
import org.model.Timetable;

import java.net.Socket;
import java.io.*;

public class ClientHandler implements Runnable{
    private final Socket socket;
    private final Timetable sharedTimetable;  // final for objects means the reference cannot change

    public ClientHandler(Socket socket, Timetable timetable) {  //each client has socket and timetable
        // timetable could be static, however in future for scalability we might want to have more timetables for more courses etc.
        this.socket = socket;
        this.sharedTimetable = timetable;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String request; // important String variable for handling requests
            while ((request = in.readLine()) != null) {
                System.out.println("Received Message: " + request);

                // Process request
                String response;
                try {
                    response = RequestProcessor.processRequest(request, sharedTimetable);
                } catch (IncorrectActionException e) {
                    response = "ERROR: " + e.getMessage();
                }

                out.println(response); // Send response to client
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
