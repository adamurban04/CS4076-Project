package org.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controller.ClientConnection;

public class DisplayTimetableView {
    private final Stage stage;
    private final Runnable onBack;

    public DisplayTimetableView(Stage stage, Runnable onBack) {
        this.stage = stage;
        this.onBack = onBack;
        showDisplayTimetableScreen();
    }

    private void showDisplayTimetableScreen() {
        Label instructionLabel = new Label("Current Timetable:");
        TextArea timetableArea = new TextArea();
        timetableArea.setEditable(false);

        Button refreshButton = new Button("Refresh");
        Button backButton = new Button("Back");

        refreshButton.setOnAction(e -> timetableArea.setText(sendDisplayTimetableRequest()));
        backButton.setOnAction(e -> onBack.run());

        VBox layout = new VBox(10, instructionLabel, timetableArea, refreshButton, backButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Display Timetable");

        // Load timetable on start
        timetableArea.setText(sendDisplayTimetableRequest());
    }

    private String sendDisplayTimetableRequest() {
        try {
            return ClientConnection.getInstance().sendRequest("Display$ ");
        } catch (Exception e) {
            return "Error: Unable to retrieve timetable.";
        }
    }
}
