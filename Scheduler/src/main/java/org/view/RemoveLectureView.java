package org.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controller.ClientConnection;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class RemoveLectureView {
    private final Stage stage;
    private final Runnable onBack;
    private TextField moduleField;
    private DatePicker datePicker;
    private TextField timeField;
    private TextField roomField;
    private Label responseLabel;

    public RemoveLectureView(Stage stage, Runnable onBack) {
        this.stage = stage;
        this.onBack = onBack;
        showRemoveLectureScreen();
    }

    private void showRemoveLectureScreen() {
        Label moduleLabel = new Label("Module:");
        moduleField = new TextField();
        moduleField.setPromptText("LM051");

        Label dateLabel = new Label("Date:");
        datePicker = new DatePicker();

        Label timeLabel = new Label("Time:");
        timeField = new TextField();
        timeField.setPromptText("HH:mm");

        Label roomLabel = new Label("Room:");
        roomField = new TextField();
        roomField.setPromptText("CG001");

        Button removeLectureButton = new Button("Remove Lecture");
        Button backButton = new Button("Back");
        responseLabel = new Label();

        datePicker.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now()) ||
                        date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    setDisable(true);
                    setStyle("-fx-background-color: #FF9999;"); // Red for weekends and past dates
                }
            }
        });

        removeLectureButton.setOnAction(e -> {
            String response = sendRemoveLectureRequest();
            responseLabel.setText(response);
        });

        backButton.setOnAction(e -> onBack.run());

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
        grid.add(backButton, 1, 5);
        grid.add(responseLabel, 1, 6);

        Scene scene = new Scene(grid, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Remove Lecture");
    }

    private String sendRemoveLectureRequest() {
        try {
            String module = moduleField.getText().trim();
            LocalDate date = datePicker.getValue();
            String time = timeField.getText().trim();
            String room = roomField.getText().trim();

            if (module.isEmpty() || date == null || time.isEmpty() || room.isEmpty()) {
                return "Please fill all fields.";
            }

            // Ensure the time is at a full hour
            LocalTime parsedTime = LocalTime.parse(time);
            if (parsedTime.getMinute() != 0) {
                return "Invalid time. Must be a full hour (e.g., 10:00, 15:00).";
            }

            String request = "Remove$" + module + "," + date + "," + time + "," + room;
            return ClientConnection.getInstance().sendRequest(request); // Use persistent connection

        } catch (Exception e) {
            return "Error: Unable to send request.";
        }
    }
}