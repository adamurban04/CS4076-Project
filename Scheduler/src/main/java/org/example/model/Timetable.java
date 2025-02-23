package org.example.model;

import org.example.exceptions.IncorrectActionException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Iterator;


public class Timetable {
    // ArrayList of ArrayLists, each holding lectures for a specific day
    private ArrayList<ArrayList<Lecture>> weeklyTimetable;

    public Timetable() {
        weeklyTimetable = new ArrayList<>(5);  // Only Monday to Friday
        for (int i = 0; i < 5; i++) {
            weeklyTimetable.add(new ArrayList<>());  // Initialize an empty list for each day
        }
    }

    public boolean isTimeSlotOccupied(LocalDate date, LocalTime time, String room) {
        int dayIndex = date.getDayOfWeek().getValue() - 1; // Get the day index (0=Monday, 4=Friday)
        if (dayIndex < 0 || dayIndex >= 5) {
            return false; // Invalid date (outside Monday-Friday)
        }

        ArrayList<Lecture> lecturesForDay = weeklyTimetable.get(dayIndex);
        for (Lecture lecture : lecturesForDay) {
            // Check if there's a conflict with both time and room
            if (lecture.getTime().equals(time) && lecture.getRoom().equals(room)) {
                return true; // Conflict found
            }
        }

        return false; // No conflict found
    }


    // Method to add a lecture to a specific day
    public boolean addLecture(Lecture lecture) throws IncorrectActionException {
        int dayIndex = lecture.getDate().getDayOfWeek().getValue() - 1;

        if (dayIndex < 0 || dayIndex >= 5) {
            throw new IncorrectActionException("Invalid date: " + lecture.getDate() + ". Only Monday to Friday allowed.");
        }

        if (isTimeSlotOccupied(lecture.getDate(), lecture.getTime(), lecture.getRoom())) {
            throw new IncorrectActionException("Error: A lecture is already scheduled at " + lecture.getTime() + " on " + lecture.getDate() + " in " + lecture.getRoom());
        }

        if (lecture.getTime().getMinute() != 0) {
            throw new IncorrectActionException("Error: Lecture time must be exactly on the hour (e.g., 12:00, 13:00).");
        }

        // No conflict, add the lecture
        weeklyTimetable.get(dayIndex).add(lecture);
        System.out.println("Lecture added: " + lecture.getModule() + " on " + lecture.getDate() + " at " + lecture.getTime() + " in " + lecture.getRoom());
        return true; // Successfully added
    }


    // Method to display lectures for a specific day
    public String getDaySchedule(LocalDate date) {
        int dayIndex = date.getDayOfWeek().getValue() - 1; // 0 = Monday, 4 = Friday

        if (dayIndex < 0 || dayIndex >= 5) {
            return "Invalid date: Only Monday to Friday allowed.";
        }

        List<Lecture> lectures = weeklyTimetable.get(dayIndex);
        if (lectures.isEmpty()) {
            return "No lectures scheduled for " + date + ".";
        }

        StringBuilder schedule = new StringBuilder("Schedule for " + date + ":\n");
        for (Lecture lecture : lectures) {
            schedule.append(lecture.getModule())
                    .append(" at ")
                    .append(lecture.getTime())
                    .append(" in ")
                    .append(lecture.getRoom())
                    .append("\n");
        }
        return schedule.toString();
    }

    public ArrayList<Lecture> getLecturesForDay(int dayIndex) {
        if (dayIndex < 0 || dayIndex >= 5) {
            throw new IllegalArgumentException("Invalid day index: " + dayIndex + ". Must be between 0 (Monday) and 4 (Friday).");
        }
        return weeklyTimetable.get(dayIndex); // This would return the list of lectures for that day
    }

    public boolean removeLecture(String module, LocalDate date, LocalTime time, String room) {
        // Iterator used in this method to avoid a ConcurrentModificationException
        int dayIndex = date.getDayOfWeek().getValue() - 1; // Convert date to index (0 = Monday, 4 = Friday)

        if (dayIndex < 0 || dayIndex >= 5) {
            System.out.println("Invalid date: " + date + ". Only Monday to Friday are allowed.");
            return false;
        }

        ArrayList<Lecture> lecturesForDay = weeklyTimetable.get(dayIndex);
        Iterator<Lecture> iterator = lecturesForDay.iterator();
        boolean lectureFound = false;

        while (iterator.hasNext()) {
            Lecture lec = iterator.next();
            if (lec.getModule().equals(module) && lec.getDate().equals(date) && lec.getTime().equals(time) && lec.getRoom().equals(room)) {
                iterator.remove(); // Remove lecture
                lectureFound = true;
                System.out.println("Lecture removed: " + module + " on " + date + " at " + time + " in " + room);
                break;
            }
        }

        if (!lectureFound) {
            System.out.println("Error: No lecture found matching the details provided.");
            System.out.println("Here are the currently scheduled lectures for " + date + ":");
            for (Lecture lec : lecturesForDay) {
                System.out.println("- " + lec.getModule() + " at " + lec.getTime() + " in " + lec.getRoom());
            }
        }
        return lectureFound;
    }


    public static void main(String[] args) throws IncorrectActionException {
        // Create a Timetable instance
        Timetable timetable = new Timetable();

        // Create some sample lectures (make sure the dates fall on Monday to Friday)
        Lecture lecture1 = new Lecture("CS101", LocalDate.of(2025, 2, 16), LocalTime.of(9, 0), "Room 101"); // Sunday (day 7), will be skipped
        Lecture lecture2 = new Lecture("CS102", LocalDate.of(2025, 2, 17), LocalTime.of(10, 0), "Room 102"); // Monday
        Lecture lecture3 = new Lecture("CS103", LocalDate.of(2025, 2, 18), LocalTime.of(14, 0), "Room 103"); // Tuesday
        Lecture lecture4 = new Lecture("CS104", LocalDate.of(2025, 2, 18), LocalTime.of(14, 0), "Room 103"); // Tuesday
        Lecture lecture5 = new Lecture("CS105", LocalDate.of(2025, 2, 18), LocalTime.of(14, 0), "Room 103"); // Tuesday
        Lecture lecture6 = new Lecture("CS106", LocalDate.of(2025, 2, 18), LocalTime.of(14, 0), "Room 103"); // Tuesday


        timetable.addLecture(lecture1);  // Will be skipped as it is on Sunday
        timetable.addLecture(lecture2);  // Will go to Monday (index 0)
        timetable.addLecture(lecture3);
        timetable.addLecture(lecture4);
        timetable.addLecture(lecture5);
        timetable.addLecture(lecture6);

        //timetable.displayWeeklySchedule();


    }


}
