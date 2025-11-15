/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Cheero
 */
public class Assignment2 extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Create Play button
        Button playBtn = new Button("Play");
        playBtn.setId("play-button"); // For CSS styling

        // Center layout
        StackPane root = new StackPane();
        root.getChildren().add(playBtn);

        Scene scene = new Scene(root, 600, 400);

        // Load CSS
        scene.getStylesheets().add(
            getClass().getResource("stylesheet.css").toExternalForm()
        );

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
