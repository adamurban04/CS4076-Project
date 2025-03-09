package org.view;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
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
        Label titleLabel = new Label("Weekly Lecture Timetable");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        timetableGrid = new GridPane();
        timetableGrid.setPadding(new Insets(10));
        timetableGrid.setHgap(50);
        timetableGrid.setVgap(10);
        timetableGrid.setAlignment(Pos.CENTER);

        Button refreshButton = createButton("Refresh", "#2C7A7B");
        Button backButton = createButton("Back", "#e27e3d");

        refreshButton.setOnAction(e -> updateTimetable());
        backButton.setOnAction(e -> onBack.run());

        VBox layout = new VBox(10, titleLabel, timetableGrid, refreshButton, backButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #F0F4F8; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Scene scene = new Scene(layout, 700, 500);
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

    private void displayTimetable(String timetableData)  {
        String[] parts = timetableData.split("\\|");

        if (parts.length < 6) { // Expecting "Scheduled Lectures | Monday | ... | Friday |"
            timetableGrid.add(new Label("Invalid timetable format."), 0, 0);
            return;
        }

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        // Set column headers (days)
        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setStyle("-fx-font-weight: bold;");
            timetableGrid.add(dayLabel, i + 1, 0);
        }

        // Set row headers (09:00 - 18:00)
        for (int hour = 9; hour <= 18; hour++) {
            Label timeLabel = new Label(hour + ":00");
            timeLabel.setStyle("-fx-font-weight: bold;");
            timetableGrid.add(timeLabel, 0, hour - 8);
        }

        // Process each day's lectures
        for (int i = 1; i < parts.length; i++) { // skip "Scheduled Lectures" at index 0
            String dayLectures = parts[i].trim();

            // Skip if no lectures for this day
            if (!dayLectures.contains("{")) {
                Label noLecturesLabel = new Label("No lectures");
                noLecturesLabel.setStyle("-fx-text-fill: #808080;"); // Grayish color
                timetableGrid.add(noLecturesLabel, i, 1);
                continue;
            }

            // Split individual lectures for the day
            String[] lectures = dayLectures.split("\\{");
            for (String lecture : lectures) {
                if (lecture.trim().isEmpty()) {
                    continue; // Skip empty entries
                }

                // Remove trailing '}' and extract lecture details
                lecture = lecture.replace("}", "").trim();
                String[] lectureDetails = lecture.split(",");

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

                        // Create a label for the module
                        Label moduleLabel = new Label(module);
                        moduleLabel.setStyle("-fx-text-fill: #2C3E50;");

                        // Add hover effect with animations
                        if (room != null) {

                            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), moduleLabel);
                            fadeOut.setFromValue(1.0);
                            fadeOut.setToValue(0.0);

                            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), moduleLabel);
                            fadeIn.setFromValue(0.0);
                            fadeIn.setToValue(1.0);

                            // On hover: change to room code
                            String finalRoom = room;
                            moduleLabel.setOnMouseEntered(e -> {
                                fadeOut.setOnFinished(event -> {
                                    moduleLabel.setText(finalRoom);
                                    fadeIn.play();
                                });
                                fadeOut.play();
                            });

                            // On exit: change to module code
                            String finalModule = module;
                            moduleLabel.setOnMouseExited(e -> {
                                fadeOut.setOnFinished(event -> {
                                    moduleLabel.setText(finalModule);
                                    fadeIn.play();
                                });
                                fadeOut.play();
                            });
                        }

                        // add the module label to the grid
                        timetableGrid.add(moduleLabel, i, hour - 8);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
    }

    private Button createButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;"));

        return button;
    }
}
