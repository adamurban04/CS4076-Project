package org.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.model.Timetable;

import java.time.LocalDate;

public class AddLectureView {
    private final Timetable timetable;
    private final Stage stage;
    private final Runnable onBack;

    public AddLectureView(Stage stage, Timetable timetable, Runnable onBack) {
        this.stage = stage;
        this.timetable = timetable;
        this.onBack = onBack;
        showAddLectureScreen();
    }

    private void showAddLectureScreen() {
        Label moduleLabel = new Label("Module:");
        TextField moduleField = new TextField();
        moduleField.setPromptText("e.g., CS101");

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();

        Label timeLabel = new Label("Time:");
        TextField timeField = new TextField();
        timeField.setPromptText("HH:mm");

        Label roomLabel = new Label("Room:");
        TextField roomField = new TextField();
        roomField.setPromptText("Room 101");

        Button addLectureButton = new Button("Add Lecture");
        Button backButton = new Button("Back");
        Label statusLabel = new Label();

        // Disable past dates
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

        addLectureButton.setOnAction(e -> {
            try {
                String module = moduleField.getText();
                LocalDate date = datePicker.getValue();
                String time = timeField.getText();
                String room = roomField.getText();

                if (module.isEmpty() || date == null || time.isEmpty() || room.isEmpty()) {
                    statusLabel.setText("Please fill all fields.");
                    return;
                }

                String lectureDetails = module + ", " + date + ", " + time + ", " + room;
                String result = timetable.addLecture(lectureDetails);
                statusLabel.setText(result);

                moduleField.clear();
                timeField.clear();
                roomField.clear();
                datePicker.setValue(null);
            } catch (Exception ex) {
                statusLabel.setText("Invalid input format.");
            }
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
        grid.add(addLectureButton, 1, 4);
        grid.add(backButton, 1, 5);
        grid.add(statusLabel, 1, 6);

        Scene scene = new Scene(grid, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Add Lecture");
    }
}
