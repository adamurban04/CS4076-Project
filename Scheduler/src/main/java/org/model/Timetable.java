package org.model;

import org.exceptions.IncorrectActionException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Iterator;


public class Timetable {
    // ArrayList of ArrayLists, each holding lectures for a specific day
    private final ArrayList<ArrayList<Lecture>> weeklyTimetable; // 5 slots for Mon-Fri

    public Timetable() {
        weeklyTimetable = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            weeklyTimetable.add(new ArrayList<>());  // Create empty lists for each day
        }
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

            weeklyTimetable.get(date.getDayOfWeek().getValue() - 1).add(new Lecture(module, date, time, room));
            return "Lecture added: " + module + " on " + date + " at " + time + " in " + room;
        } catch (Exception e) {
            throw new IncorrectActionException("Invalid date/time format.");
        }
    }

    public synchronized String removeLecture(String details) throws IncorrectActionException {
        String[] parts = details.split(",");
        if (parts.length < 2) throw new IncorrectActionException("Invalid lecture format. Expected: module,date");

        try {
            String module = parts[0].trim();
            LocalDate date = LocalDate.parse(parts[1].trim());
            int dayIndex = date.getDayOfWeek().getValue() - 1;

            Iterator<Lecture> iterator = weeklyTimetable.get(dayIndex).iterator();
            while (iterator.hasNext()) {
                Lecture lecture = iterator.next();
                if (lecture.getModule().equalsIgnoreCase(module) && lecture.getDate().equals(date)) {
                    iterator.remove();
                    return "Lecture removed: " + module + " on " + date;
                }
            }
            return "ERROR: Lecture not found.";
        } catch (Exception e) {
            throw new IncorrectActionException("Invalid date format.");
        }
    }

    public synchronized String getSchedule() {
        StringBuilder schedule = new StringBuilder("Scheduled Lectures:\n");
        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        for (int i = 0; i < 5; i++) {
            schedule.append(weekdays[i]).append(":\n");
            if (weeklyTimetable.get(i).isEmpty()) {
                schedule.append("  No lectures.\n");
            } else {
                for (Lecture lecture : weeklyTimetable.get(i)) {
                    schedule.append("  ").append(lecture).append("\n");
                }
            }
        }
        return schedule.toString();
    }


    private boolean isTimeSlotOccupied(LocalDate date, LocalTime time, String room) {
        int dayIndex = date.getDayOfWeek().getValue() - 1;
        for (Lecture lecture : weeklyTimetable.get(dayIndex)) {
            if (lecture.getTime().equals(time) && lecture.getRoom().equals(room)) {
                return true;
            }
        }
        return false;
    }


}
