package org.example.model;

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

    // Method to add a lecture to a specific day
    public void addLecture(Lecture lecture) {
        // Get the day of the week index (0 = Monday, 4 = Friday)
        int dayIndex = lecture.getDate().getDayOfWeek().getValue() - 1; // 1 = Monday, 7 = Sunday

        // Ensure the index is valid (0 to 4 for Monday to Friday)
        if (dayIndex >= 0 && dayIndex <= 4) {
            weeklyTimetable.get(dayIndex).add(lecture);  // Add the lecture to the respective day
        }
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

    // Method to display the full week's timetable (Monday to Friday)
   /* public void displayWeeklySchedule() {
        for (int i = 0; i < 5; i++) {
            displayDaySchedule(i);  // Display the schedule for each day (index 0-4)
        }
    } */

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



    public static void main(String[] args) {
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
// Will go to Tuesday (index 1)

        // Display the full weekly schedule
        //timetable.displayWeeklySchedule();


    }
}
