package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

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
        if (!modules.contains(module)) { // Check if module is unique
            this.module = module;
            modules.add(module);
            System.out.println(module + " added!");
            System.out.println("Current module count: " + modules.size());  // Debugging line

        } else {
            throw new IllegalArgumentException("Module already exists.");
        }
        this.date = date;
        this.time = time;
        this.room = room;
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

    public boolean conflictsWith(Lecture other) {
        //allows for checking of conflicting lectures (Gonna be useful)
        return this.date.equals(other.date) && this.time.equals(other.time) && this.room.equals(other.room);
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