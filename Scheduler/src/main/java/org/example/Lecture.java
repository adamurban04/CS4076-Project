package org.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Lecture {
    private String module;
    private LocalDate date;    // e.g., "2025-02-14"
    private LocalTime time;    // e.g., "10:00"
    private String room;

    public Lecture() {
    }

    public Lecture(String module, LocalDate date, LocalTime time, String room) {
        this.module = module;
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