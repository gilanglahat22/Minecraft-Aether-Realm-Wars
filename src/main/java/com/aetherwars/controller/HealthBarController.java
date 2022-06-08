package com.aetherwars.controller;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class HealthBarController {

    @FXML private Rectangle healthBar; // full width: 540.0
    public void setHealthBar(int health) {
        // max health = 80
        double healthDouble = health;
        double rectWidth = healthDouble * 6.75;
        this.healthBar.setWidth(rectWidth);
    }

}
