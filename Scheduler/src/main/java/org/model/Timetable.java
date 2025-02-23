package org.model;

import org.exceptions.IncorrectActionException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Iterator;


public class Timetable {
    // ArrayList of ArrayLists, each holding lectures for a specific day
    private final ArrayList<Lecture> lectures;

    public Timetable() {
        this.lectures = new ArrayList<>();
    }

    // Method to add a lecture to a specific day
    public synchronized String addLecture(String details) throws IncorrectActionException {
        String[] parts = details.split(",");
        if (parts.length < 4) throw new IncorrectActionException("Invalid lecture format. Expected: module,date,time,room");

        try {
            String module = parts[0].trim();
            LocalDate date = LocalDate.parse(parts[1].trim());
            LocalTime time = LocalTime.parse(parts[2].trim());
            String room = parts[3].trim();

            if (isTimeSlotOccupied(date, time, room)) {
                return "ERROR: Time slot occupied for " + room + " at " + time;
            }

            lectures.add(new Lecture(module, date, time, room));
            return "Lecture added: " + module + " on " + date + " at " + time + " in " + room;
        } catch (Exception e) {
            throw new IncorrectActionException("Invalid date/time format.");
        }
    }



    public synchronized String removeLecture(String details) throws IncorrectActionException {
        Iterator<Lecture> iterator = lectures.iterator();
        while (iterator.hasNext()) {
            Lecture lecture = iterator.next();
            if (lecture.getModule().equalsIgnoreCase(details.trim())) {
                iterator.remove();
                return "Lecture removed: " + details;
            }
        }
        return "ERROR: Lecture not found.";
    }

    public synchronized String getSchedule() {
        if (lectures.isEmpty()) {
            return "No lectures scheduled.";
        }

        StringBuilder schedule = new StringBuilder("Scheduled Lectures:\n");
        for (Lecture lecture : lectures) {
            schedule.append(lecture).append("\n");
        }
        return schedule.toString();
    }


    private boolean isTimeSlotOccupied(LocalDate date, LocalTime time, String room) {
        for (Lecture lecture : lectures) {
            if (lecture.getDate().equals(date) && lecture.getTime().equals(time) && lecture.getRoom().equals(room)) {
                return true;
            }
        }
        return false;
    }


}
