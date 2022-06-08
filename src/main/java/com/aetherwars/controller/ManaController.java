package com.aetherwars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ManaController {
    @FXML public Label manaCount;

    public void updateManaCount(int manaCount){
        this.manaCount.setText(String.valueOf(manaCount));
    }
}
