package org.example;

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
import org.example.model.Lecture;
import org.example.model.Timetable;

import java.time.LocalDate;
import java.time.LocalTime;

// THE MAIN CLASS THAT INITIALIZES THE APP AND MANAGES SCENES

public class TimetableApp extends Application {
    private Timetable timetable = new Timetable(); // Instance of the timetable
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showWelcomeScreen();
    }

    private void showWelcomeScreen(){
        Label welcomeLabel = new Label("Welcome to the Lecture Timetable App");
        Button startButton = new Button("Start");

        startButton.setOnAction(e -> showMainScreen());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(welcomeLabel, startButton);

        Scene welcomeScene = new Scene(layout, 500, 400);
        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("Welcome");
        primaryStage.show();
    }


    private void showMainScreen(){
        // UI Components
        Label moduleLabel = new Label("Module:");
        TextField moduleField = new TextField();
        moduleField.setPromptText("e.g., CS101");

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker(); // For selecting the lecture date

        Label timeLabel = new Label("Time:");
        TextField timeField = new TextField();
        timeField.setPromptText("Time (HH:mm) E.g 12:00");

        Label roomLabel = new Label("Room:");
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

                // No need to handle incorrect time format here as it is handled separately
                if (module.isEmpty() || date == null || room.isEmpty()) {
                    statusLabel.setText("Please fill all fields.");
                    return;
                }

                Lecture newLecture = new Lecture(module, date, time, room);
                timetable.addLecture(newLecture); // Add to timetable
                statusLabel.setText("Lecture added successfully!");

                // Clear fields after adding
                // Allows for user to possibly add another lecture in succession
                moduleField.clear();
                timeField.clear();
                roomField.clear();
                datePicker.setValue(null);

            } catch (Exception ex) {
                statusLabel.setText("Invalid input format. Use HH:mm for time.");
            }
        });

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // GridPane Components
        grid.add(moduleLabel, 0, 0);
        grid.add(moduleField, 1, 0);
        grid.add(dateLabel, 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(timeLabel, 0, 2);
        grid.add(timeField, 1, 2);
        grid.add(roomLabel, 0, 3);
        grid.add(roomField, 1, 3);
        grid.add(addButton, 1, 4);
        grid.add(statusLabel, 1, 5);

        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lecture Timetable");
        primaryStage.show();
    }


}
