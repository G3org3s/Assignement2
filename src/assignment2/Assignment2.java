/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment2;

import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

        Scene scene = new Scene(root, 700, 500);

        // Load CSS
        scene.getStylesheets().add(
                getClass().getResource("stylesheet.css").toExternalForm()
        );

        // Creating runners
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

        // Music
        String musicPath = Paths.get("")
                .toAbsolutePath()
                .resolve("MenuMusic.mp3")
                .toUri()
                .toString();

        Media media = new Media(musicPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        // Action once the start button is pressed
        playBtn.setOnAction(e -> {
            // Layout to show runners
            VBox presentationBox = new VBox(20);
            presentationBox.setAlignment(Pos.CENTER);

            // Runner Image
            ImageView imgView = new ImageView();
            imgView.setFitWidth(200);
            imgView.setPreserveRatio(true);

            // Runner Name
            Text nameText = new Text();
            nameText.setStyle("-fx-font-size: 48px; -fx-fill: white; -fx-font-weight: bold; -fx-font-family: Geo;");

            // Title for the section
            Label presenting = new Label("Presenting The Runners:");
            presenting.setStyle("-fx-font-size: 56px; -fx-text-fill: white; -fx-font-weight: bold;");
            presenting.setLayoutX(350);
            presenting.setLayoutY(10);

            presentationBox.getChildren().addAll(presenting, imgView, nameText);

            // Replace menu with presentation
            root.getChildren().setAll(presentationBox);

            // Animation sequence for presenting runners
            SequentialTransition seq = new SequentialTransition();

            for (Marathoner m : runners) {
                // Updating the image and text to fade. Needs PauseTransition to work
                PauseTransition updateContent = new PauseTransition(Duration.millis(10));
                updateContent.setOnFinished(ev -> {
                    imgView.setImage(m.getIntroImage());
                    nameText.setText(m.getName());
                    imgView.setOpacity(0);
                    nameText.setOpacity(0);
                });

                // Fade ins
                FadeTransition fadeImg = new FadeTransition(Duration.seconds(1), imgView);
                fadeImg.setFromValue(0);
                fadeImg.setToValue(1);

                FadeTransition fadeTxt = new FadeTransition(Duration.seconds(1), nameText);
                fadeTxt.setFromValue(0);
                fadeTxt.setToValue(1);

                // Play both fade ins at the same time
                ParallelTransition fadeBoth = new ParallelTransition(fadeImg, fadeTxt);

                // Fade outs
                FadeTransition fadeImgOut = new FadeTransition(Duration.seconds(1), imgView);
                fadeImgOut.setFromValue(1);
                fadeImgOut.setToValue(0);

                FadeTransition fadeTxtOut = new FadeTransition(Duration.seconds(1), nameText);
                fadeTxtOut.setFromValue(1);
                fadeTxtOut.setToValue(0);

                // Play both fade outs at the same time
                ParallelTransition fadeOutBoth = new ParallelTransition(fadeImgOut, fadeTxtOut);

                // Create the sequence
                seq.getChildren().addAll(updateContent, fadeBoth, fadeOutBoth);
            }

            // After presenting the runners, set up the race UI
            seq.setOnFinished(ev -> {
                // Clear the root and Create BorderPane
                root.getChildren().clear();
                BorderPane bp = new BorderPane();
                bp.setPrefSize(700, 500);

                Pane centerPane = new Pane(); //Midle Area with runners
                centerPane.setPrefSize(700, 440);
                centerPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f3f5b, #1e1e2f)"); // backround Styling
                bp.setCenter(centerPane);

                // Visual parameters
                double sceneWidth = 700;         // Scene width
                double startX = 80;              // Starting x for runners & names
                double endX = sceneWidth - 80;   // Finishing x
                int n = runners.size();          // Number of runners
                double topMargin = 60;           // Distance from top
                double laneSpacing = 70;

                // Creating tracks and name labels on Pane
                for (int i = 0; i < n; i++) {
                    double laneY = topMargin + i * laneSpacing; // Y of  each lane, so each runner is separated

                    // Track as a thin rectangle (line)
                    Rectangle track = new Rectangle(startX - 30, laneY + 24, endX - startX + 60, 3);
                    track.setFill(Color.DARKGRAY);
                    centerPane.getChildren().add(track);

                    // Name at left of track
                    Label nameLabel = new Label(runners.get(i).getName());
                    nameLabel.setTextFill(Color.WHITE);
                    nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
                    nameLabel.setLayoutX(10);
                    nameLabel.setLayoutY(laneY);
                    centerPane.getChildren().add(nameLabel);
                }

                // List storing the transition of each runner
                ArrayList<TranslateTransition> tts = new ArrayList<>();

                // Placing the Runners and making their transitions
                for (int i = 0; i < n; i++) {
                    Marathoner m = runners.get(i);
                    m.setSpeed(0.5 + Math.random()); // random speed. Uses default bounds [0.0 ; 1.0[
                    double laneY = topMargin + i * laneSpacing; // where to place the runner

                    ImageView iv = new ImageView(m.getRaceImage()); // Image 
                    iv.setFitWidth(60);
                    iv.setPreserveRatio(true); // Preserve Aspect Ratio

                    // Place the image at the starting X inside the Pane
                    iv.setLayoutX(startX);
                    iv.setLayoutY(laneY - 4);
                    centerPane.getChildren().add(iv);

                    // Translate Transition
                    double xToTravel = endX - startX;
                    double durationSeconds = 5.0 / m.getSpeed(); // faster speed leads to smaller duration
                    TranslateTransition tt = new TranslateTransition(Duration.seconds(durationSeconds), iv);
                    tt.setByX(xToTravel);
                    tts.add(tt);

                    // Particle trail
                    tt.currentTimeProperty().addListener((obs, oldT, newT) -> {
                        // Create particle as a square
                        Rectangle particle = new Rectangle(6, 6, Color.GRAY);
                        particle.setArcWidth(3);
                        particle.setArcHeight(3);

                        // Get the runners' current position
                        double runnerX = iv.getLayoutX() + iv.getTranslateX() + 5;
                        double runnerY = iv.getLayoutY() + iv.getTranslateY() + 50;

                        // Place particle behind runner
                        particle.setLayoutX(runnerX - 8);
                        particle.setLayoutY(runnerY);

                        centerPane.getChildren().add(particle);

                        // Make particle move
                        TranslateTransition drift = new TranslateTransition(Duration.seconds(0.5), particle);
                        drift.setByX(-20 + Math.random() * -10); // Go to the left
                        drift.setByY(-10 + Math.random() * -10); // Go upward
                        // Make partcle fade
                        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), particle);
                        fade.setFromValue(1);
                        fade.setToValue(0);

                        // Make the particle move and fade away at the same time
                        ParallelTransition trail = new ParallelTransition(drift, fade);
                        trail.setOnFinished(z -> centerPane.getChildren().remove(particle));
                        trail.play();
                    });
                }

                // Creating Controls and Message
                Button startBtn = new Button("Start");
                Button pauseBtn = new Button("Pause");
                Button exitBtn = new Button("Exit");

                startBtn.setDisable(false);
                pauseBtn.setDisable(true);  // disabled until race started

                Text message = new Text("Marathon Status: Ongoing");
                message.setStyle("-fx-font-size: 12px; -fx-fill: white; -fx-font-weight: bold;");

                HBox controls = new HBox(12, startBtn, pauseBtn, exitBtn, message);
                controls.setAlignment(Pos.CENTER_LEFT);
                controls.setStyle("-fx-padding: 12;");
                bp.setBottom(controls);

                // Start action -> play all transitions
                startBtn.setOnAction(btnEv -> {
                    startBtn.setDisable(true); // Disable start
                    pauseBtn.setDisable(false); // Enable pause

                    // Pre-Compute the winner and second place for message and alert at the end
                    int winnerIdx = 0;
                    int secondIdx = -1;
                    for (int i = 0; i < tts.size(); i++) {
                        if (tts.get(i).getDuration().lessThan(tts.get(winnerIdx).getDuration())) {
                            secondIdx = winnerIdx;
                            winnerIdx = i;
                        } else if (secondIdx == -1 || tts.get(i).getDuration().lessThan(tts.get(secondIdx).getDuration())) {
                            if (i != winnerIdx) {
                            secondIdx = i; 
                            }
                        }
                    }
                    // Store them in final variable
                    final int[] winnersArr = {winnerIdx, secondIdx};

                    final boolean[] ended = {false};

                    // Loop that makes runners start racing
                    for (int i = 0; i < tts.size(); i++) {
                        TranslateTransition tt = tts.get(i);

                        // Handling the ending of the race
                        tt.setOnFinished(ev2 -> {

                            startBtn.setDisable(true); //Disable buttons
                            pauseBtn.setDisable(true);
                            // Making sure pop-up only happens at first runner
                            if (!ended[0]) {
                                ended[0] = true;
                                // Change message
                                message.setText("Marathon Status: " + runners.get(winnersArr[0]).getName() + " won the race!"
                                        + "\n" + runners.get(winnersArr[1]).getName() + " was right behind him!");

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Race Result");
                                alert.setHeaderText(null);
                                alert.setContentText(runners.get(winnersArr[0]).getName() + " won the race!");
                                alert.show();
                            }
                        });
                        // Start the transition for each runner
                        tt.play();
                    }

                }
                );

                // Pause and Resume toggle
                final boolean[] paused = {false};
                pauseBtn.setOnAction(btnEv -> {
                    if (!paused[0]) {
                        // Pause all
                        for (TranslateTransition tt : tts) {
                            tt.pause();
                        }
                        pauseBtn.setText("Resume");
                        paused[0] = true;
                    } else {
                        for (TranslateTransition tt : tts) {
                            tt.play();
                        }
                        pauseBtn.setText("Pause");
                        paused[0] = false;
                    }
                });

                // Exit -> stop transitions and go back to main menu
                exitBtn.setOnAction(btnEv -> {
                    for (TranslateTransition tt : tts) {
                        tt.stop();
                    }
                    // Setting back to main menu
                    root.getChildren().setAll(playBtn);
                });

                // Add the borderpane to root
                root.getChildren().add(bp);
            }
            );
            // Place the presentation of runners
            seq.play();
        }
        );

        // Setting the stage
        stage.setTitle(
                "Main Menu");
        stage.setScene(scene);

        stage.show();
    }
}
