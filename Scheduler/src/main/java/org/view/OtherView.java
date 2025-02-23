package org.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controller.ClientConnection;

public class OtherView {
    private final Stage stage;
    private final Runnable onBack;

    public OtherView(Stage stage, Runnable onBack) {
        this.stage = stage;
        this.onBack = onBack;
        showOtherScreen();
    }

    private void showOtherScreen() {
        Label instructionLabel = new Label("Click the button to send an invalid request:");
        Button sendButton = new Button("Send Invalid Request");
        Button backButton = new Button("Back");
        Label responseLabel = new Label();

        sendButton.setOnAction(e -> {
            String response = sendInvalidRequest();
            responseLabel.setText(response);
        });

        backButton.setOnAction(e -> onBack.run());

        VBox layout = new VBox(10, instructionLabel, sendButton, backButton, responseLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500, 300);
        stage.setScene(scene);
        stage.setTitle("Test Incorrect Action");
    }

    private String sendInvalidRequest() {
        try {
            return ClientConnection.getInstance().sendRequest("InvalidCommand$test");
        } catch (Exception e) {
            return "Error: Unable to connect to the server.";
        }
    }
}
