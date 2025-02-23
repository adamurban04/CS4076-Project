package org.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.model.Timetable;

public class TimetableApp extends Application {
    private final Timetable timetable = new Timetable();
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMainScreen();
    }

    private void showMainScreen() {
        Button addButton = new Button("Add Lecture");
        Button removeButton = new Button("Remove Lecture");
        Button displayButton = new Button("Display Timetable");
        Button otherButton = new Button("Test Incorrect Action");

        addButton.setOnAction(e -> new AddLectureView(primaryStage, timetable, this::showMainScreen));
        removeButton.setOnAction(e -> new RemoveLectureView(primaryStage, timetable, this::showMainScreen));
        displayButton.setOnAction(e -> new DisplayTimetableView(primaryStage, timetable, this::showMainScreen));
        otherButton.setOnAction(e -> new OtherView(primaryStage, this::showMainScreen));

        VBox buttonLayout = new VBox(10, addButton, removeButton, displayButton, otherButton);
        buttonLayout.setPadding(new Insets(20));
        buttonLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(buttonLayout, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lecture Timetable");
        primaryStage.show();
    }
}