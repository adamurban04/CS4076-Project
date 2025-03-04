package org.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controller.ClientConnection;
import org.model.Timetable;

public class TimetableApp extends Application {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false); //fixed window size
        showMainScreen();
    }

    private void showMainScreen() {
        Button addButton = new Button("Add Lecture");
        Button removeButton = new Button("Remove Lecture");
        Button displayButton = new Button("Display Timetable");
        Button otherButton = new Button("Other");

        addButton.setOnAction(e -> new AddLectureView(primaryStage, this::showMainScreen));
        removeButton.setOnAction(e -> new RemoveLectureView(primaryStage, this::showMainScreen));
        displayButton.setOnAction(e -> new DisplayTimetableView(primaryStage, this::showMainScreen));
        otherButton.setOnAction(e -> new OtherView(primaryStage, this::showMainScreen));

        VBox buttonLayout = new VBox(10, addButton, removeButton, displayButton, otherButton);
        buttonLayout.setPadding(new Insets(20));
        buttonLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(buttonLayout, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lecture Timetable");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        ClientConnection.getInstance().closeConnection(); // Close connection when app exits
        super.stop();
    }

}