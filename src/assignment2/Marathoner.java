/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import javafx.scene.image.Image;

/**
 *
 * @author Cheero
 */
public class Marathoner {
    double speed;
    Image introImage;
    Image raceImage;
    String name; 

    public Marathoner(Image introImage, Image raceImage, String name) {
        this.introImage = introImage;
        this.raceImage = raceImage;
        this.name = name;
        this.speed = 1;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Image getIntroImage() {
        return introImage;
    }

    public void setIntroImage(Image introImage) {
        this.introImage = introImage;
    }

    public Image getRaceImage() {
        return raceImage;
    }

    public void setRaceImage(Image raceImage) {
        this.raceImage = raceImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
