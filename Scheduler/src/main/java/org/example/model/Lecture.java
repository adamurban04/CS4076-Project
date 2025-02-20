package org.example.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


// NOTES FOR ADAM
// ALWAYS PUSH BEFORE YOU FINISH FOR THE DAY
public class Lecture {
    private static ArrayList<String> modules = new ArrayList<>();

    private String module;
    private LocalDate date;    // e.g., "2025-02-14"
    private LocalTime time;    // e.g., "10:00"
    private String room;

    public Lecture() {
    }

    public Lecture(String module, LocalDate date, LocalTime time, String room) {
        if (modules.size() >= 5) {  // Prevents adding more than 5 modules
            throw new IllegalArgumentException("Maximum of 5 modules allowed.");
        }

        if (!modules.contains(module)) {  // If the module is new
            modules.add(module);  // Add the module to the list
            System.out.println(module + " added!");
            System.out.println("Current module count: " + modules.size());  // Debugging line
        }
        this.module = module;
        this.date = date;
        this.time = time;
        this.room = room;

        System.out.println(module + " lecture scheduled for " + date + " at " + time + " in " + room);
        System.out.println("Current module count: " + modules.size());  // Debugging line
    }


    public String getModule() {
        return module;
    }



    public void setModule(String module) {
        this.module = module;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }



    @Override
    public String toString() {
        return "Lecture{" +
                "module='" + module + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", room='" + room + '\'' +
                '}';
    }
}