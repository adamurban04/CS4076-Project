package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimetableApp extends Application {
    private Timetable timetable = new Timetable(); // Instance of the timetable

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // UI Components
        TextField moduleField = new TextField();
        moduleField.setPromptText("Module (e.g., CS101)");

        DatePicker datePicker = new DatePicker(); // For selecting the lecture date

        TextField timeField = new TextField();
        timeField.setPromptText("Time (HH:mm)");

        TextField roomField = new TextField();
        roomField.setPromptText("Room (e.g., Room 101)");

        Button addButton = new Button("Add Lecture");
        Label statusLabel = new Label();

        // Event Handler: Add Lecture to Timetable
        addButton.setOnAction(e -> {
            try {
                String module = moduleField.getText();
                LocalDate date = datePicker.getValue();
                LocalTime time = LocalTime.parse(timeField.getText());
                String room = roomField.getText();

                if (module.isEmpty() || date == null || room.isEmpty()) {
                    statusLabel.setText("Please fill all fields.");
                    return;
                }

                Lecture newLecture = new Lecture(module, date, time, room);
                timetable.addLecture(newLecture); // Add to timetable
                statusLabel.setText("Lecture added successfully!");

                // Clear fields after adding
                moduleField.clear();
                timeField.clear();
                roomField.clear();
                datePicker.setValue(null);

            } catch (Exception ex) {
                statusLabel.setText("Invalid input format. Use HH:mm for time.");
            }
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Enter Lecture Details:"),
                moduleField, datePicker, timeField, roomField,
                addButton, statusLabel
        );

        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lecture Timetable");
        primaryStage.show();
    }


}
