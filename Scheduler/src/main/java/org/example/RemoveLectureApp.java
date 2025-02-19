package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.example.model.Timetable;

import java.time.LocalDate;
import java.time.LocalTime;

public class RemoveLectureApp extends Application {
    private Timetable timetable = new Timetable(); // Use existing timetable

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // UI Components
        TextField moduleField = new TextField();
        moduleField.setPromptText("Module (e.g., CS101)");

        DatePicker datePicker = new DatePicker();

        TextField timeField = new TextField();
        timeField.setPromptText("Time (HH:mm) E.g 12:00");

        TextField roomField = new TextField();
        roomField.setPromptText("Room (e.g., Room 101)");

        Button removeButton = new Button("Remove Lecture");
        Label statusLabel = new Label();
        TextArea scheduleDisplay = new TextArea();
        scheduleDisplay.setEditable(false);

        // Event Handler: Remove Lecture
        removeButton.setOnAction(e -> {
            try {
                String module = moduleField.getText();
                LocalDate date = datePicker.getValue();
                LocalTime time = LocalTime.parse(timeField.getText());
                String room = roomField.getText();

                if (module.isEmpty() || date == null || room.isEmpty()) {
                    statusLabel.setText("Please fill all fields.");
                    return;
                }

                boolean removed = timetable.removeLecture(module, date, time, room);

                if (removed) {
                    statusLabel.setText("Lecture removed successfully!");
                } else {
                      statusLabel.setText("No matching lecture found. Schedule for that day:");
                    scheduleDisplay.setText(timetable.getDaySchedule(date));
                }

                // Clear input fields
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
                new Label("Enter Lecture Details to Remove:"),
                moduleField, datePicker, timeField, roomField,
                removeButton, statusLabel, scheduleDisplay
        );

        Scene scene = new Scene(layout, 350, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Remove Lecture");
        primaryStage.show();
    }
}
