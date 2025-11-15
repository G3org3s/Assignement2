/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment2;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        Button playBtn = new Button("Race");
        playBtn.setId("play-button"); // For CSS styling

        // Center layout
        StackPane root = new StackPane();
        root.getChildren().add(playBtn);

        Scene scene = new Scene(root, 600, 400);

        // Load CSS
        scene.getStylesheets().add(
                getClass().getResource("stylesheet.css").toExternalForm()
        );

        ArrayList<Marathoner> runners = new ArrayList<>();

        runners.add(new Marathoner(
                new Image("file:Cube090.png"),
                new Image("file:Cube090.png"),
                "Bob"
        ));
        runners.add(new Marathoner(
                new Image("file:Cube098.png"),
                new Image("file:Cube098.png"),
                "Mike"
        ));
        runners.add(new Marathoner(
                new Image("file:Cube109.png"),
                new Image("file:Cube109.png"),
                "Finn"
        ));
        runners.add(new Marathoner(
                new Image("file:Cube118.png"),
                new Image("file:Cube118.png"),
                "Brad"
        ));
        runners.add(new Marathoner(
                new Image("file:Cube137.png"),
                new Image("file:Cube137.png"),
                "Jack"
        ));

        playBtn.setOnAction(e -> {
            // A fresh layout to show runners
            VBox presentationBox = new VBox(20);
            presentationBox.setAlignment(Pos.CENTER);

            ImageView imgView = new ImageView();
            imgView.setFitWidth(200);
            imgView.setPreserveRatio(true);

            Text nameText = new Text();
            nameText.setStyle("-fx-font-size: 36px; -fx-fill: white; -fx-font-weight: bold;");

            presentationBox.getChildren().addAll(imgView, nameText);

            // Replace menu with presentation
            root.getChildren().setAll(presentationBox);

            // Animation loop (simple + inline)
            final int[] index = {0};

            SequentialTransition seq = new SequentialTransition();

            for (Marathoner m : runners) {
                
                // Updating the image and text to fade. Needs transition to work
                PauseTransition updateContent = new PauseTransition(Duration.millis(10));
                updateContent.setOnFinished(ev -> {
                    imgView.setImage(m.getIntroImage());
                    nameText.setText(m.getName());
                    imgView.setOpacity(0);
                    nameText.setOpacity(0);
                });

                // Fade in for each runner
                FadeTransition fadeImg = new FadeTransition(Duration.seconds(1), imgView);
                fadeImg.setFromValue(0);
                fadeImg.setToValue(1);

                FadeTransition fadeTxt = new FadeTransition(Duration.seconds(1), nameText);
                fadeTxt.setFromValue(0);
                fadeTxt.setToValue(1);

                // Play both at the same time
                ParallelTransition fadeBoth = new ParallelTransition(fadeImg, fadeTxt);

                // Add the fade to the sequence
                seq.getChildren().addAll(updateContent, fadeBoth);
            }
            seq.play();
        }
        );

        stage.setTitle(
                "Main Menu");
        stage.setScene(scene);

        stage.show();
    }

}
