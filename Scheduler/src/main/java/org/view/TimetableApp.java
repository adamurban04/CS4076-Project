package org.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.model.Lecture;
import org.model.Timetable;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimetableApp extends Application {
    private Timetable timetable = new Timetable(); // Instance of the timetable
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMainScreen();
    }

    // Screen: Menu with options
    private void showMainScreen() {
        // UI Components for adding, removing, and displaying timetable
        Button addButton = new Button("Add Lecture");
        Button removeButton = new Button("Remove Lecture");
        Button displayButton = new Button("Display Timetable");
        Button otherButton = new Button("Other");

        // Event handlers for each button
        addButton.setOnAction(e -> showAddLectureScreen());
        removeButton.setOnAction(e -> showRemoveLectureScreen());
        displayButton.setOnAction(e -> showDisplayTimetableScreen());
        otherButton.setOnAction(e -> showOtherScreen());

        // Layout for the buttons
        VBox buttonLayout = new VBox(10);
        buttonLayout.setPadding(new Insets(20));
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(addButton, removeButton, displayButton, otherButton);

        Scene scene = new Scene(buttonLayout, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lecture Timetable");
        primaryStage.show();
    }

    // Screen: Adding lecture
    private void showAddLectureScreen() {
        // Data input fields for adding a lecture
        Label moduleLabel = new Label("Module:");
        TextField moduleField = new TextField();
        moduleField.setPromptText("e.g., CS101");

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();

        Label timeLabel = new Label("Time:");
        TextField timeField = new TextField();
        timeField.setPromptText("Time (HH:mm) E.g 12:00");

        Label roomLabel = new Label("Room:");
        TextField roomField = new TextField();
        roomField.setPromptText("Room (e.g., Room 101)");

        Button addLectureButton = new Button("Add Lecture");
        Button backButton = new Button("Back to Main Menu");
        Label statusLabel = new Label();

        // Disable past dates in DatePicker
        datePicker.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #CCCCCC;");
                }
            }
        });

        // Add lecture button action
        addLectureButton.setOnAction(e -> {
            try {
                String module = moduleField.getText();
                LocalDate date = datePicker.getValue();
                LocalTime time = LocalTime.parse(timeField.getText());
                String room = roomField.getText();

                // Validate input
                if (module.isEmpty() || date == null || room.isEmpty()) {
                    statusLabel.setText("Please fill all fields.");
                    return;
                }

                if (date.isBefore(LocalDate.now())) {
                    statusLabel.setText("Error: You cannot select a past date.");
                    return;
                }

                // Create the new lecture
                Lecture newLecture = new Lecture(module, date, time, room);
                boolean success = timetable.addLecture(newLecture);

                if (success) {
                    statusLabel.setText("Lecture added successfully!");
                } else {
                    statusLabel.setText("Error: Room already booked for that time.");
                }

                if (success) {
                    moduleField.clear();
                    timeField.clear();
                    roomField.clear();
                    datePicker.setValue(null);
                }

            } catch (Exception ex) {
                statusLabel.setText("Invalid input format. Use HH:mm for time.");
            }
        });

        // Back button action
        backButton.setOnAction(e -> showMainScreen());

        // Layout for the data input and status message
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(moduleLabel, 0, 0);
        grid.add(moduleField, 1, 0);
        grid.add(dateLabel, 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(timeLabel, 0, 2);
        grid.add(timeField, 1, 2);
        grid.add(roomLabel, 0, 3);
        grid.add(roomField, 1, 3);
        grid.add(addLectureButton, 1, 4);
        grid.add(backButton, 1, 5);  // Back Button
        grid.add(statusLabel, 1, 6);

        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Lecture");
        primaryStage.show();
    }

    private void showRemoveLectureScreen() {
        // Data input fields for removing a lecture
        Label moduleLabel = new Label("Module:");
        TextField moduleField = new TextField();
        moduleField.setPromptText("e.g., CS101");

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();

        Label timeLabel = new Label("Time:");
        TextField timeField = new TextField();
        timeField.setPromptText("Time (HH:mm) E.g 12:00");

        Label roomLabel = new Label("Room:");
        TextField roomField = new TextField();
        roomField.setPromptText("Room (e.g., Room 101)");

        Button removeLectureButton = new Button("Remove Lecture");
        Button backButton = new Button("Back to Main Menu");
        Label statusLabel = new Label();

        // Disable past dates in DatePicker
        datePicker.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #CCCCCC;");
                }
            }
        });

        // Remove lecture button action
        removeLectureButton.setOnAction(e -> {
            try {
                String module = moduleField.getText();
                LocalDate date = datePicker.getValue();
                LocalTime time = LocalTime.parse(timeField.getText());
                String room = roomField.getText();

                if (module.isEmpty() || date == null || room.isEmpty()) {
                    statusLabel.setText("Please fill all fields.");
                    return;
                }

                if (date.isBefore(LocalDate.now())) {
                    statusLabel.setText("Error: You cannot remove a lecture from a past date.");
                    return;
                }

                if (time.getMinute() != 0) {
                    statusLabel.setText("Invalid time. Only full-hour times allowed (e.g., 12:00, 14:00).");
                    return;
                }

                boolean removed = timetable.removeLecture(module, date, time, room);
                if (removed) {
                    statusLabel.setText("Lecture removed successfully!");
                } else {
                    statusLabel.setText("No matching lecture found.");
                }

                moduleField.clear();
                timeField.clear();
                roomField.clear();
                datePicker.setValue(null);

            } catch (Exception ex) {
                statusLabel.setText("Invalid input format. Use HH:mm for time.");
            }
        });

        // Back button action
        backButton.setOnAction(e -> showMainScreen());

        // Layout for the data input and status message
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(moduleLabel, 0, 0);
        grid.add(moduleField, 1, 0);
        grid.add(dateLabel, 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(timeLabel, 0, 2);
        grid.add(timeField, 1, 2);
        grid.add(roomLabel, 0, 3);
        grid.add(roomField, 1, 3);
        grid.add(removeLectureButton, 1, 4);
        grid.add(backButton, 1, 5);  // Back Button
        grid.add(statusLabel, 1, 6);

        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Remove Lecture");
        primaryStage.show();
    }




    private void showDisplayTimetableScreen() {
        // Logic for displaying the timetable
        System.out.println("Display Timetable screen (yet to be implemented)");
        // Needs to be implemented in a way that looks nice (possibly dynamic timetable)
    }

    private void showOtherScreen() {
        // Logic for other functionalities
        System.out.println("Other screen (yet to be implemented)");
        // Used to show our own exception class
        // You must define an exception called IncorrectActionException that  produces a message
        // error that will be  sent by the  server. This exception
        // will be thrown if the user provides an incorrect action format
        // Your application must then react to this exception
    }
}
