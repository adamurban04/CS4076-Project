package org.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controller.ClientConnection;

public class RemoveLectureView {
    private final Stage stage;
    private final Runnable onBack;
    private TextField moduleField;
    private DatePicker datePicker;
    private Label responseLabel;

    public RemoveLectureView(Stage stage, Runnable onBack) {
        this.stage = stage;
        this.onBack = onBack;
        showRemoveLectureScreen();
    }

    private void showRemoveLectureScreen() {
        Label moduleLabel = new Label("Module:");
        moduleField = new TextField();
        moduleField.setPromptText("CS101");

        Label dateLabel = new Label("Date:");
        datePicker = new DatePicker();

        Button removeLectureButton = new Button("Remove Lecture");
        Button backButton = new Button("Back");
        responseLabel = new Label();

        removeLectureButton.setOnAction(e -> {
            String response = sendRemoveLectureRequest();
            responseLabel.setText(response);
        });

        backButton.setOnAction(e -> onBack.run());

        VBox layout = new VBox(10, moduleLabel, moduleField, dateLabel, datePicker, removeLectureButton, backButton, responseLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500, 300);
        stage.setScene(scene);
        stage.setTitle("Remove Lecture");
    }

    private String sendRemoveLectureRequest() {
        try {
            String module = moduleField.getText().trim();
            String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "";

            if (module.isEmpty() || date.isEmpty()) {
                return "Please fill all fields.";
            }

            String request = "Remove$" + module + "," + date;
            return ClientConnection.getInstance().sendRequest(request);
        } catch (Exception e) {
            return "Error: Unable to send request.";
        }
    }
}