package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

// WHEN RUNNING GUI, YOU HAVE TO HAVE THE JAVAFX LIBRARY AND IN THE CONFIGURATION ADD VM OPTIONS(EDIT PATH):
// --module-path /Users/adamurban/documents/javafx-sdk-23.0.1/lib --add-modules javafx.controls,javafx.fxml
//com.mycompany.payrollsystem.system.ui.GUI

//run through setup configuration

//GUI
//(Adam+James)

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Login.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Payroll System Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}