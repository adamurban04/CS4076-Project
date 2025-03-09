package org.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controller.ClientConnection;
import org.controller.ClientHandler;

import java.io.IOException;

public class OtherView {
    private final Stage stage;
    private final Runnable onBack;

    public OtherView(Stage stage, Runnable onBack) {
        this.stage = stage;
        this.onBack = onBack;
        showOtherScreen();
    }

    private void showOtherScreen() {
        Label titleLabel = new Label("Test Invalid Action");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        Label instructionLabel = new Label("Click the button to send an invalid request:");
        instructionLabel.setStyle("-fx-font-size: 14px;");

        Button sendButton = createButton("Send Invalid Request", "#6f3deb");
        Button quitButton = createButton("STOP", "#E63946");
        Button backButton = createButton("Back", "#e27e3d");

        Label responseLabel = new Label();
        responseLabel.setWrapText(true);
        responseLabel.setStyle("-fx-padding: 10px; -fx-text-fill: #D32F2F; -fx-border-radius: 5px;");

        sendButton.setOnAction(e -> responseLabel.setText(sendInvalidRequest()));

        quitButton.setOnAction(e -> {
            try {
                String response = ClientConnection.getInstance().sendRequest("STOP$Close");

                if ("TERMINATE".equals(response)) {
                    System.out.println("Client received TERMINATE, shutting down...");
                    ClientConnection.getInstance().closeConnection();
                    Platform.exit();
                    System.exit(0);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        backButton.setOnAction(e -> onBack.run());

        VBox layout = new VBox(15, titleLabel, instructionLabel, sendButton, quitButton, backButton, responseLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #F0F4F8; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Scene scene = new Scene(layout, 700, 500);
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

    private Button createButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px;"));
        return button;
    }
}
