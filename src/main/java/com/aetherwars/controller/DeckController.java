package com.aetherwars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class DeckController{
    // kartu tampak tertutup
    @FXML private ImageView deckImage;
    @FXML private Label cardCount;

    public void updateDeckCount(int count){
        cardCount.setText(String.valueOf(count));
    }
}
