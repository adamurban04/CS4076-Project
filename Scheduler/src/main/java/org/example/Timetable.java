package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
    public void displayDaySchedule(int dayIndex) {
        if (dayIndex >= 0 && dayIndex < 5) {
            ArrayList<Lecture> lectures = weeklyTimetable.get(dayIndex);
            if (lectures.isEmpty()) {
                System.out.println("No lectures scheduled for day " + (dayIndex + 1));
            } else {
                System.out.println("Schedule for Day " + (dayIndex + 1) + ":");
                for (Lecture lecture : lectures) {
                    System.out.println(lecture);
                }
            }
        } else {
            System.out.println("Invalid day index.");
        }
    }

    // Method to display the full week's timetable (Monday to Friday)
    public void displayWeeklySchedule() {
        for (int i = 0; i < 5; i++) {
            displayDaySchedule(i);  // Display the schedule for each day (index 0-4)
        }
    }

    public static void main(String[] args) {
        // Create a Timetable instance
        Timetable timetable = new Timetable();

        // Create some sample lectures (make sure the dates fall on Monday to Friday)
        Lecture lecture1 = new Lecture("CS101", LocalDate.of(2025, 2, 16), LocalTime.of(9, 0), "Room 101"); // Sunday (day 7), will be skipped
        Lecture lecture2 = new Lecture("CS102", LocalDate.of(2025, 2, 17), LocalTime.of(10, 0), "Room 102"); // Monday
        Lecture lecture3 = new Lecture("CS103", LocalDate.of(2025, 2, 18), LocalTime.of(14, 0), "Room 103"); // Tuesday

        timetable.addLecture(new Lecture("CS101",LocalDate.of(2025, 2, 19), LocalTime.of(11, 0), "Room 101"));

        timetable.addLecture(lecture1);  // Will be skipped as it is on Sunday
        timetable.addLecture(lecture2);  // Will go to Monday (index 0)
        timetable.addLecture(lecture3);  // Will go to Tuesday (index 1)

        // Display the full weekly schedule
        timetable.displayWeeklySchedule();
    }
}
