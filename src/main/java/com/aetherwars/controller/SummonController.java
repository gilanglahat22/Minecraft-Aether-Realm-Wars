package com.aetherwars.controller;

import com.aetherwars.AetherWars;
import com.aetherwars.model.Card;
import com.aetherwars.model.Game;
import com.aetherwars.model.HoverableItem;
import com.aetherwars.model.Summon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SummonController implements Initializable, SelectableItem, HoverableItem {
    @FXML private VBox root;
    @FXML private ImageView summonAttackIcon;
    @FXML private ImageView summonHealthIcon;
    @FXML private Label summonAttack;
    @FXML private Label summonHealth;
    @FXML private Label summonExpLevel;
    @FXML private ImageView summonImage;

    private ObjectProperty<Summon> summonProperty;
    private int player;
    private int boardNumber;
    private BooleanProperty alreadyAttacked = new SimpleBooleanProperty(false);
    private BooleanProperty isHovered = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.summonProperty = new SimpleObjectProperty<>();
        this.alreadyAttacked.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue.booleanValue()){
                    root.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: darkgrey;");
                } else{
                    root.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");
                }
            }
        });
        this.summonProperty.addListener(new ChangeListener<Summon>() {
            @Override
            public void changed(ObservableValue<? extends Summon> observable, Summon oldValue, Summon newValue) {
                if(newValue == null){
                    summonAttackIcon.setImage(null);
                    summonHealthIcon.setImage(null);
                    summonImage.setImage(null);
                    summonAttack.setText(null);
                    summonHealth.setText(null);
                    summonExpLevel.setText(null);
                    return;
                }
                if(oldValue == null){    // agar tidak meload gambar lagi
                    summonAttackIcon.setImage(new Image(AetherWars.class.getResource("static/sword.png").toExternalForm()));
                    summonHealthIcon.setImage(new Image(AetherWars.class.getResource("static/health.png").toExternalForm()));
                }
                Summon summon = summonProperty.get();
                summonImage.setImage(new Image(AetherWars.class.getResource(summon.getCard().getImgPath()).toExternalForm()));
                summonAttack.setText(""+summon.getAttackEff());
                summonHealth.setText(""+summon.getHealthEff());
                summonExpLevel.setText(String.format("%d/%d [%d]", summon.getExp(), summon.getNextLevelExp(), summon.getLevel()));
            }
        });
        this.summonAttackIcon.setImage(null);
        this.summonHealthIcon.setImage(null);
        this.summonAttack.setText(null);
        this.summonHealthIcon.setImage(null);
        this.summonHealth.setText(null);
        this.summonExpLevel.setText(null);
    }

    public void setSummon(Summon summon){
        if(summon != null){
            this.summonProperty.set(new Summon(summon));
            Game.getInstance().getListPlayer().get(player-1).setSummon(this.boardNumber, this.summonProperty.get());
        } else {
            this.summonProperty.set(null);
            Game.getInstance().getListPlayer().get(player-1).setSummon(this.boardNumber, this.summonProperty.get());
        }
    }
    public Summon getSummon(){
        return this.summonProperty.get();
    }

    // Interface SelectableItem
    @Override
    public void doAction(SelectableItem target) {
        if(target instanceof SummonController && !target.isEmptySelection()){
            SummonController lawanCon =(SummonController) target;
            if(this.player == lawanCon.player) return;  // tidak bisa menyerang summon sendiri
            if(Game.getInstance().getPhase() != Game.Phase.ATTACK) return;

            Summon lawan = lawanCon.getSummon();
            Summon kita = this.getSummon();
            kita.attack(lawan);

            if(kita.getHealthEff() <= 0){
                kita = null;
                this.isHovered.set(false);
            }
            if(lawan.getHealthEff() <= 0){
                lawan = null;
                lawanCon.isHovered.set(false);
            }
            //System.out.println("Attack!");
            this.setSummon(kita);   // update nilai GUI summon kita
            lawanCon.setSummon(lawan);  // update papan GUI summon lawan
            if(kita != null) this.setAlreadyAttacked(true);
        }
    }

    @Override
    public void onSelected() {
        this.root.setStyle("-fx-border-color: orange; -fx-border-width: 4; -fx-background-color: white;");
    }

    @Override
    public void onDeselected() {
        if(this.alreadyAttacked.get())
            this.root.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: darkgrey;");
        else
            this.root.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");
    }

    @Override
    public boolean isEmptySelection() {
        return this.summonProperty.get() == null || this.alreadyAttacked.get();
    }

    public void setSummonExpLevel(String summonExpLevel) {
        this.summonExpLevel.setText(summonExpLevel);
    }

    public void setPlayer(int player) {
        this.player = player;
    }
    public int getPlayer() {
        return player;
    }

    public BooleanProperty getIsHovered(){
        return this.isHovered;
    }

    public void setHovered(boolean isHovered){
        this.isHovered.set(isHovered);
    }
    public void setHovered(MouseEvent event){
        event.consume();
        if(this.summonProperty.get() != null)
            this.isHovered.set(true);
    }
    public void setUnhovered(MouseEvent event){
        event.consume();
        if(this.summonProperty.get() != null)
            this.isHovered.set(false);
    }

    public BooleanProperty getAlreadyAttackedProp(){
        return this.alreadyAttacked;
    }
    public boolean getIsAttacked(){
        return this.alreadyAttacked.get();
    }
    public void setAlreadyAttacked(boolean alreadyAttacked){
        this.alreadyAttacked.set(alreadyAttacked);
    }

    // Interface Hoverable
    @Override
    public String getHoverImagePath() {
        return this.summonProperty.get().getCard().getImgPath();
    }

    @Override
    public String getHoverTitle() {
        return this.summonProperty.get().getCard().getName();
    }

    @Override
    public String getHoverDetail() {
        return this.summonProperty.get().getCard().getName();
    }

    @Override
    public String getHoverDescription() {
        return this.summonProperty.get().getCard().getDescription();
    }

    public int getBoardNumber() {
        return boardNumber;
    }

    public void setBoardNumber(int boardNumber) {
        this.boardNumber = boardNumber;
    }
}
