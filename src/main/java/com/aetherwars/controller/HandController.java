package com.aetherwars.controller;

import com.aetherwars.model.Card;
import com.aetherwars.model.Summon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HandController implements Initializable {
    @FXML private VBox card1;
    @FXML private VBox card2;
    @FXML private VBox card3;
    @FXML private VBox card4;
    @FXML private VBox card5;
    @FXML private CardController card1Controller;
    @FXML private CardController card2Controller;
    @FXML private CardController card3Controller;
    @FXML private CardController card4Controller;
    @FXML private CardController card5Controller;

    private List<VBox> cards;
    private List<CardController> cardControllers;

    /** Mengeset card pada posisi idx, otomatis layout terupdate
     * Isi card dengan null untuk menghapus card pada posisi idx */
    public void setCard(Card card, int idx){
        this.cardControllers.get(idx).setCard(card);
    }
    public List<VBox> getCards() {
        return cards;
    }
    public List<CardController> getCardControllers() {
        return cardControllers;
    }

    public void updateHand(List<Card> cards){
        int i;
        for(i = 0; i < cards.size(); i++){
            this.setCard(cards.get(i), i);
        }
        for(; i < 5; i++){
            this.setCard(null, i);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.cards = new ArrayList<>();
        this.cardControllers = new ArrayList<>();
        this.cards.add(card1);
        this.cards.add(card2);
        this.cards.add(card3);
        this.cards.add(card4);
        this.cards.add(card5);
        this.cardControllers.add(card1Controller);
        this.cardControllers.add(card2Controller);
        this.cardControllers.add(card3Controller);
        this.cardControllers.add(card4Controller);
        this.cardControllers.add(card5Controller);
    }

    public int countCardInHand(){
        return this.cards.size();
    }
}
