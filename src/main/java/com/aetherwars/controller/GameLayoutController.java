package com.aetherwars.controller;

import com.aetherwars.AetherWars;
import com.aetherwars.model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class GameLayoutController implements Initializable {
    @FXML public GridPane root;

    @FXML public GridPane steveBoard;
    @FXML public BoardController steveBoardController;
    @FXML public HBox alexHealth;
    @FXML public HealthBarController alexHealthController;
    @FXML public HBox steveHealth;
    @FXML public GridPane phases;
    @FXML public PhaseController phasesController;
    @FXML public HealthBarController steveHealthController;
    @FXML public Label turnLabel;
    @FXML public Label currentPlayer;
    @FXML public DeckController deckRemainingBoxController;

    @FXML public AnchorPane manaBox;
    @FXML public ManaController manaBoxController;
    @FXML public VBox discardHere;
    @FXML private ImageView steveImage;

    @FXML public GridPane alexBoard;
    @FXML public BoardController alexBoardController;
    @FXML private ImageView alexImage;

    @FXML public HBox hand;
    @FXML public HandController handController;

    @FXML private ImageView hoveredImage;
    @FXML private Label hoveredName;
    @FXML private Label hoveredAttribute;
    @FXML private Label hoveredDescription;
    private HoverableItem currentHoverItem;

    private SelectableItem selectedItem1;
    private SelectableItem selectedItem2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        steveImage.setImage(new Image(AetherWars.class.getResource("static/steve.png").toExternalForm()));
        alexImage.setImage(new Image(AetherWars.class.getResource("static/alex.jpg").toExternalForm()));
        for(SummonController sc : steveBoardController.summonControllers){
            sc.setPlayer(1);
        }
        for(SummonController sc : alexBoardController.summonControllers){
            sc.setPlayer(2);
        }
        steveImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(selectedItem1 != null && selectedItem1 instanceof SummonController){
                    SummonController sc = (SummonController) selectedItem1;
                    Summon s = sc.getSummon();
                    if(sc.getPlayer() == 1) return;

                    Player p = Game.getInstance().getListPlayer().get(0);
                    int health = p.getHealth();
                    p.attackThis(s);
                    if(p.getHealth() != health){
                        sc.setAlreadyAttacked(true);
                    }
                    steveHealthController.setHealthBar(Game.getInstance().getListPlayer().get(0).getHealth());
                }
                setSelectedItem1(null);
            }
        });
        alexImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(selectedItem1 != null && selectedItem1 instanceof SummonController){
                    SummonController sc = (SummonController) selectedItem1;
                    Summon s = sc.getSummon();
                    if(sc.getPlayer() == 2) return;

                    Player p = Game.getInstance().getListPlayer().get(1);
                    int health = p.getHealth();
                    p.attackThis(s);
                    if(p.getHealth() != health){
                        sc.setAlreadyAttacked(true);
                    }
                    alexHealthController.setHealthBar(p.getHealth());
                }
                setSelectedItem1(null);
            }
        });

        phasesController.nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game game = Game.getInstance();
                game.setPhase(Game.getInstance().nextPhase());
                Player player = game.getListPlayer().get(game.getPlayerIndex()-1);
                Game.Phase now = game.getPhase();

                turnLabel.setText("Turn " + game.getRound());
                currentPlayer.setText(game.getPlayerIndex() == 1 ? "STEVE" : "ALEX");
                if(now == Game.Phase.DRAW){
                    phasesController.endPhase.setFill(Color.WHITE);
                    phasesController.drawPhase.setFill(Color.color(0xff*1.0/0xff, 0xcc*1.0/0xff, 0x55*1.0/0xff));
                } else if(now == Game.Phase.PLAN){
                    phasesController.drawPhase.setFill(Color.WHITE);
                    phasesController.planPhase.setFill(Color.color(0xff*1.0/0xff, 0xcc*1.0/0xff, 0x55*1.0/0xff));
                } else if(now == Game.Phase.ATTACK){
                    phasesController.planPhase.setFill(Color.WHITE);
                    phasesController.attackPhase.setFill(Color.color(0xff*1.0/0xff, 0xcc*1.0/0xff, 0x55*1.0/0xff));
                } else if(now == Game.Phase.END){
                    phasesController.attackPhase.setFill(Color.WHITE);
                    phasesController.endPhase.setFill(Color.color(0xff*1.0/0xff, 0xcc*1.0/0xff, 0x55*1.0/0xff));
                }
                if(now == Game.Phase.DRAW){
                    if(game.getPlayerIndex() == 1){
                        for(SummonController sc : alexBoardController.summonControllers){
                            sc.setAlreadyAttacked(false);
                        }
                    } else{
                        for(SummonController sc : steveBoardController.summonControllers){
                            sc.setAlreadyAttacked(false);
                        }
                    }
                    /*for(int i = 0; i < 5; i++){
                        if(game.getPlayerIndex() == 1)
                            steveBoardController.setSummon(player.getSummon(i), i);
                        else
                            alexBoardController.setSummon(player.getSummon(i), i);
                    }*/
                    manaBoxController.updateManaCount(Game.getInstance().getListPlayer().get(Game.getInstance().getPlayerIndex()-1).getMana());
                    drawCardSelector((Stage) ((Node)event.getSource()).getScene().getWindow());
                    deckRemainingBoxController.updateDeckCount(player.getDeck().getCards().size());
                }
            }
        });
        discardHere.setOnMouseClicked(new SelectableItemHandler(new DiscardHereController(), this));
    }

    public void setHandCard(Card card, int idx){
        this.handController.setCard(card, idx);
    }

    public void drawCardSelector(Stage stage){
        Popup popup = new Popup();
        popup.setAutoHide(false);
        popup.centerOnScreen();
        popup.setOpacity(1);
        HBox bg = new HBox();
        bg.setAlignment(Pos.CENTER);
        bg.setSpacing(150);
        bg.setMinWidth(1280);
        bg.setMinHeight(670);
        bg.setStyle("-fx-background-color: black;");

        Game game = Game.getInstance();
        Player p = game.getListPlayer().get(game.getPlayerIndex()-1);
        List<Card> cards = p.getDeck().draw();

        try{
            for(Card c : cards){
                int i = 0;
                FXMLLoader loader = new FXMLLoader(AetherWars.class.getResource("ui/Card.fxml"));
                VBox cardBox = loader.load();
                CardController cardCon = loader.getController();
                cardBox.setStyle("-fx-background-color: white; -fx-border-color: black;");
                cardBox.setScaleX(2);
                cardBox.setScaleY(2);
                cardCon.setCard(c);
                int finalI = i;
                cardBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        cards.remove(finalI);
                        p.getDeck().putBackCards(cards);

                        if(p.getHand().size() == 5){
                            int removeIdx = new Random().nextInt(5);
                            p.getHand().remove(removeIdx);  // hapus secara random
                        }
                        p.getHand().add(c);
                        handController.updateHand(p.getHand()); // update layout

                        popup.hide();
                        root.setDisable(false);
                    }
                });
                bg.getChildren().add(cardBox);
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        popup.getContent().add(bg);

        this.root.setDisable(true);
        popup.show(stage);
    }

    public void setHoverItem(HoverableItem item){
        this.currentHoverItem = item;
        hoveredImage.setImage(new Image(AetherWars.class.getResource(item.getHoverImagePath()).toExternalForm()));
        hoveredName.setText(item.getHoverTitle());
        hoveredAttribute.setText(item.getHoverDetail());
        hoveredDescription.setText(item.getHoverDescription());
    }
    public void unsetHoverItem(HoverableItem item){
        if(this.currentHoverItem == item || item == null){
            this.currentHoverItem = null;
            hoveredImage.setImage(null);
            hoveredAttribute.setText("");
            hoveredName.setText("");
            hoveredDescription.setText("");
        }
    }

    public SelectableItem getSelectedItem1() {
        return selectedItem1;
    }
    public void setSelectedItem1(SelectableItem selectedItem1) {
        if(this.selectedItem1 != null) this.selectedItem1.onDeselected();
        this.selectedItem1 = selectedItem1;
        if(selectedItem1 != null) selectedItem1.onSelected();
        manaBoxController.updateManaCount(Game.getInstance().getListPlayer().get(Game.getInstance().getPlayerIndex()-1).getMana());
    }
    public SelectableItem getSelectedItem2() {
        return selectedItem2;
    }
    public void setSelectedItem2(SelectableItem selectedItem2) {
        if(this.selectedItem2 != null) this.selectedItem2.onDeselected();
        this.selectedItem2 = selectedItem2;
        if(selectedItem2 != null) selectedItem2.onSelected();
    }
}
