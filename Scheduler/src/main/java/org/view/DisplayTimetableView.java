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
            String response = ClientConnection.getInstance().sendRequest("Display$details");    //details have to be there

            timetableGrid.getChildren().clear(); // Clear previous timetable data
            displayTimetable(response);
        } catch (Exception e) {
            timetableGrid.getChildren().clear();
            timetableGrid.add(new Label("Error: Unable to retrieve timetable."), 0, 0);
        }
    }

    private void displayTimetable(String timetableData) {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        System.out.println(timetableData);

        // Set column headers for weekdays
        for (int i = 0; i < days.length; i++) {
            timetableGrid.add(new Label(days[i]), i + 1, 0); // Column headers
        }

        // Set row headers for time slots (09:00 - 17:00)
        for (int hour = 9; hour <= 18; hour++) {
            timetableGrid.add(new Label(hour + ":00"), 0, hour - 8); // Row headers
        }

        // Parse response and add lectures to the correct positions
        String[] lines = timetableData.split("\n");
        int currentDayIndex = -1;

        for (String line : lines) {
            if (line.endsWith(":")) { // Detecting a new day section (e.g., "Monday:")
                currentDayIndex = getDayIndex(line.replace(":", "").trim());
            } else if (currentDayIndex != -1 && line.trim().length() > 0) {
                // Extracting lecture details
                String[] lectureParts = line.trim().split(",");
                if (lectureParts.length >= 3) {
                    String module = lectureParts[0].trim();
                    String time = lectureParts[1].trim();

                    try {
                        int hour = Integer.parseInt(time.split(":")[0]); // Extract hour
                        timetableGrid.add(new Label(module), currentDayIndex + 1, hour - 8);
                    } catch (Exception ignored) {
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
