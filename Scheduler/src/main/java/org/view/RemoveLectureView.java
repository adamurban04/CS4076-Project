package org.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.model.Timetable;

import java.time.LocalDate;

public class RemoveLectureView {
    private final Timetable timetable;
    private final Stage stage;
    private final Runnable onBack;

    public RemoveLectureView(Stage stage, Timetable timetable, Runnable onBack) {
        this.stage = stage;
        this.timetable = timetable;
        this.onBack = onBack;
        showRemoveLectureScreen();
    }

    private void showRemoveLectureScreen() {
        Label moduleLabel = new Label("Module:");
        TextField moduleField = new TextField();
        moduleField.setPromptText("e.g., CS101");

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();

        Button removeLectureButton = new Button("Remove Lecture");
        Button backButton = new Button("Back");
        Label statusLabel = new Label();

        removeLectureButton.setOnAction(e -> {
            try {
                String module = moduleField.getText();
                LocalDate date = datePicker.getValue();

                if (module.isEmpty() || date == null) {
                    statusLabel.setText("Please fill all fields.");
                    return;
                }

                String lectureDetails = module + ", " + date;
                String result = timetable.removeLecture(lectureDetails);
                statusLabel.setText(result);

                moduleField.clear();
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
        grid.add(removeLectureButton, 1, 2);
        grid.add(backButton, 1, 3);
        grid.add(statusLabel, 1, 4);

        Scene scene = new Scene(grid, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Remove Lecture");
    }
}