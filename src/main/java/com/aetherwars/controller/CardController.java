package com.aetherwars.controller;

import com.aetherwars.AetherWars;
import com.aetherwars.model.*;
import javafx.beans.property.*;
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
import java.util.ResourceBundle;


public class CardController implements Initializable, SelectableItem {
    @FXML private VBox root;
    @FXML private ImageView cardImage;
    @FXML private Label cardMana;
    @FXML private Label cardAbility;
    private ObjectProperty<Card> cardProperty;
    private BooleanProperty isHovered = new SimpleBooleanProperty(false);

    public void setCard(Card card){
        this.cardProperty.set(card);
    }
    public Card getCard(){
        return this.cardProperty.get();
    }

    public BooleanProperty getIsHovered(){
        return this.isHovered;
    }
    public void setHovered(boolean isHovered){
        this.isHovered.set(isHovered);
    }
    public void setHovered(MouseEvent event){
        event.consume();
        if(this.cardProperty.get() != null)
            this.isHovered.set(true);
    }
    public void setUnhovered(MouseEvent event){
        event.consume();
        if(this.cardProperty.get() != null)
            this.isHovered.set(false);
    }

    @Override
    public boolean isEmptySelection() {
        return this.cardProperty.get() == null;
    }

    @Override
    public void doAction(SelectableItem target) {
        Card thisCard = this.cardProperty.get();
        if(thisCard == null) return;
        if(Game.getInstance().getPhase() != Game.Phase.PLAN) return;

        Player player = Game.getInstance().getListPlayer().get(Game.getInstance().getPlayerIndex()-1);
        if(target instanceof DiscardHereController){
            for(int i = 0; i < player.getHand().size(); i++){
                if(player.getHand().get(i) == thisCard){
                    player.getHand().remove(i);
                }
            }
            this.setCard(null);
            return;
        }

        int manaAvailable = player.getMana();
        int cardMana = this.cardProperty.get().getMana();
        if(cardMana == -1 && target instanceof SummonController){
            SummonController sc = (SummonController)target;
            cardMana = (int) Math.ceil(sc.getSummon().getLevel()*1.0/2);
        }

        if(manaAvailable < cardMana) return;

        if(target instanceof SummonController) {
            SummonController targetCon = (SummonController) target;
            Summon summon = targetCon.getSummon();

            if(summon != null && thisCard instanceof SpellCard){
                Spell spell = ((SpellCard) thisCard).getSpell();
                spell.execute(summon);
                summon.apply(spell);
                if(summon.getHealthEff() <= 0) summon = null;

                targetCon.setSummon(summon);
                player.setMana(manaAvailable - cardMana);

                for(int i = 0; i < player.getHand().size(); i++){
                    if(player.getHand().get(i) == thisCard){
                        player.getHand().remove(i);
                    }
                }
                this.setCard(null);
            }
            else if(summon != null && this.cardProperty.get() instanceof CharacterCard)
            {
                System.out.println("Target summmon slot harus kosong");
            }
            else if(summon == null && this.cardProperty.get() instanceof CharacterCard)
            {
                if(targetCon.getPlayer() != Game.getInstance().getPlayerIndex()) return;    // tidak bisa spawn di board lawan

                targetCon.setSummon(new Summon((CharacterCard) this.cardProperty.get()));
                player.setMana(manaAvailable - cardMana);
                for(int i = 0; i < player.getHand().size(); i++){
                    if(player.getHand().get(i) == thisCard){
                        player.getHand().remove(i);
                    }
                }
                targetCon.setHovered(true);
                this.setCard(null);
            }
        }
    }

    @Override
    public void onSelected() {
        this.root.setStyle("-fx-border-color: orange; -fx-border-width: 4;");
    }

    @Override
    public void onDeselected() {
        this.root.setStyle("-fx-border-color: black; -fx-border-width: 2;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardImage.setImage(null);
        cardMana.setText("EMPTY");
        cardAbility.setText("");

        cardProperty = new SimpleObjectProperty<>();
        cardProperty.addListener(new ChangeListener<Card>() {
            @Override
            public void changed(ObservableValue<? extends Card> observable, Card oldValue, Card newValue) {
                if(newValue == null){
                    cardImage.setImage(null);
                    cardMana.setText("EMPTY");
                    cardAbility.setText("");
                } else{
                    String strPath = AetherWars.class.getResource(newValue.getImgPath()).toExternalForm();
                    cardImage.setImage(new Image(strPath));
                    cardMana.setText(newValue.getManaCost());
                    cardAbility.setText(newValue.getHeadline());
                }

            }
        });
    }
}
