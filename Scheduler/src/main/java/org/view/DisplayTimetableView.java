package org.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controller.ClientConnection;

public class DisplayTimetableView {
    private final Stage stage;
    private final Runnable onBack;
    private GridPane timetableGrid;

    public DisplayTimetableView(Stage stage, Runnable onBack) {
        this.stage = stage;
        this.onBack = onBack;
        showDisplayTimetableScreen();
    }

    private void showDisplayTimetableScreen() {
        Label instructionLabel = new Label("Weekly Lecture Timetable:");

        timetableGrid = new GridPane();
        timetableGrid.setPadding(new Insets(10));
        timetableGrid.setHgap(10);
        timetableGrid.setVgap(10);
        timetableGrid.setAlignment(Pos.CENTER);

        Button refreshButton = new Button("Refresh");
        Button backButton = new Button("Back");

        refreshButton.setOnAction(e -> updateTimetable());
        backButton.setOnAction(e -> onBack.run());

        VBox layout = new VBox(10, instructionLabel, timetableGrid, refreshButton, backButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Display Timetable");

        updateTimetable(); // Load timetable on start
    }

    private void updateTimetable() {
        try {
            String response = ClientConnection.getInstance().sendRequest("Display$details");

            timetableGrid.getChildren().clear(); // Clear previous timetable data
            displayTimetable(response);
        } catch (Exception e) {
            timetableGrid.getChildren().clear();
            timetableGrid.add(new Label("Error: Unable to retrieve timetable."), 0, 0);
        }
    }

    private void displayTimetable(String timetableData) {
        String[] parts = timetableData.split("\\|");

        if (parts.length < 6) { // Expecting "Scheduled Lectures | Monday | ... | Friday |"
            timetableGrid.add(new Label("Invalid timetable format."), 0, 0);
            return;
        }

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        // Set column headers
        for (int i = 0; i < days.length; i++) {
            timetableGrid.add(new Label(days[i]), i + 1, 0);
        }

        // Set row headers for time slots (09:00 - 18:00)
        for (int hour = 9; hour <= 18; hour++) {
            timetableGrid.add(new Label(hour + ":00"), 0, hour - 8);
        }

        for (int i = 1; i < parts.length; i++) { // Skip "Scheduled Lectures" at index 0
            String day = days[i - 1]; // Map index to actual day
            String lectures = parts[i].trim();

            if (lectures.startsWith("{") && lectures.endsWith("}")) {
                lectures = lectures.substring(1, lectures.length() - 1); // Remove `{}`

                String[] lectureDetails = lectures.split(",");

                String module = null, time = null, room = null;

                for (String detail : lectureDetails) {
                    String[] keyValue = detail.split("=");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        switch (key) {
                            case "module":
                                module = value;
                                break;
                            case "time":
                                time = value;
                                break;
                            case "room":
                                room = value;
                                break;
                        }
                    }
                }

                if (module != null && time != null) {
                    try {
                        int hour = Integer.parseInt(time.split(":")[0]); // Extract hour
                        timetableGrid.add(new Label(module + " (" + room + ")"), i, hour - 8);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
    }


    private int getDayIndex(String day) {
        switch (day) {
            case "Monday":
                return 0;
            case "Tuesday":
                return 1;
            case "Wednesday":
                return 2;
            case "Thursday":
                return 3;
            case "Friday":
                return 4;
            default:
                return -1;
        }
    }
}
