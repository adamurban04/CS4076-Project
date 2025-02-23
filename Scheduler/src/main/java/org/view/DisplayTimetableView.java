package org.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.model.Timetable;

public class DisplayTimetableView {
    private final Timetable timetable;
    private final Stage stage;
    private final Runnable onBack;

    public DisplayTimetableView(Stage stage, Timetable timetable, Runnable onBack) {
        this.stage = stage;
        this.timetable = timetable;
        this.onBack = onBack;
        showDisplayTimetableScreen();
    }

    private void showDisplayTimetableScreen() {
        TextArea timetableArea = new TextArea();
        timetableArea.setEditable(false);
        timetableArea.setText(timetable.getSchedule());

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> onBack.run());

        VBox layout = new VBox(10, timetableArea, backButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Display Timetable");
    }
}