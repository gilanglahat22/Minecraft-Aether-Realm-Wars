package com.aetherwars;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.aetherwars.controller.*;
import com.aetherwars.model.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.aetherwars.util.CSVReader;

public class AetherWars extends Application {
  private static final String CHARACTER_CSV_FILE_PATH = "card/data/character.csv";

  public void loadCards() throws IOException, URISyntaxException {
    File characterCSVFile = new File(getClass().getResource(CHARACTER_CSV_FILE_PATH).toURI());
    CSVReader characterReader = new CSVReader(characterCSVFile, "\t");
    characterReader.setSkipHeader(true);
    List<String[]> characterRows = characterReader.read();

    CardMap cm = CardMap.getInstance();
    for (String[] row : characterRows) {
      CharacterCard cc = new CharacterCard(
              Integer.parseInt(row[0]), row[1], row[3], row[4], CharacterCard.TypeCharacter.valueOf(row[2]),
              Integer.parseInt(row[5]), Integer.parseInt(row[6]), Integer.parseInt(row[7]),
              Integer.parseInt(row[8]), Integer.parseInt(row[9]));
      cm.set(cc.getId(), cc);
      System.out.println(cc);
    }

    File spellLevelCSVFile = new File(getClass().getResource("card/data/spell_level.csv").toURI());
    CSVReader spellLevelReader = new CSVReader(spellLevelCSVFile, "\t");
    spellLevelReader.setSkipHeader(true);
    List<String[]> spellLevelRows = spellLevelReader.read();
    for(String[] row : spellLevelRows){
      LevelSpell ls = new LevelSpell(Boolean.parseBoolean(row[4]));
      SpellCard sc = new SpellCard(Integer.parseInt(row[0]), row[1], row[2], row[3], ls, -1);
      cm.set(sc.getId(), sc);
    }

    File spellMorphCSVFile = new File(getClass().getResource("card/data/spell_morph.csv").toURI());
    CSVReader spellMorphReader = new CSVReader(spellMorphCSVFile, "\t");
    spellMorphReader.setSkipHeader(true);
    List<String[]> spellMorphRows = spellMorphReader.read();
    for(String[] row : spellMorphRows){
      MorphSpell ms = new MorphSpell(Integer.parseInt(row[4]));
      SpellCard sc = new SpellCard(Integer.parseInt(row[0]), row[1], row[2], row[3], ms, Integer.parseInt(row[5]));
      cm.set(sc.getId(), sc);
    }

    File spellPtnCSVFile = new File(getClass().getResource("card/data/spell_ptn.csv").toURI());
    CSVReader spellPtnReader = new CSVReader(spellPtnCSVFile, "\t");
    spellPtnReader.setSkipHeader(true);
    List<String[]> spellPtnRows = spellPtnReader.read();
    for(String[] row : spellPtnRows){
      PotionSpell ps = new PotionSpell(Integer.parseInt(row[4]), Integer.parseInt(row[5]), Integer.parseInt(row[7]));
      SpellCard sc = new SpellCard(Integer.parseInt(row[0]), row[1], row[2], row[3], ps, Integer.parseInt(row[6]));
      cm.set(sc.getId(), sc);
    }

    File spellSwapCSVFile = new File(getClass().getResource("card/data/spell_swap.csv").toURI());
    CSVReader spellSwapReader = new CSVReader(spellSwapCSVFile, "\t");
    spellSwapReader.setSkipHeader(true);
    List<String[]> spellSwapRows = spellSwapReader.read();
    for(String[] row : spellSwapRows){
      SwapSpell ss = new SwapSpell(Integer.parseInt(row[4]));
      SpellCard sc = new SpellCard(Integer.parseInt(row[0]), row[1], row[2], row[3], ss, Integer.parseInt(row[5]));
      cm.set(sc.getId(), sc);
    }
  }

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/GameLayout.fxml"));
    GridPane root = loader.load();
    GameLayoutController gameLayoutController = loader.getController();

    Scene scene = new Scene(root, 1280, 720);

    stage.setTitle("Minecraft: Aether Wars");
    stage.setScene(scene);
    stage.show();

    gameLayoutController.unsetHoverItem(null);  // reset highlight hover
    // Reset pilihan jika mengklik sembarang di board
    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        gameLayoutController.setSelectedItem1(null);
        gameLayoutController.setSelectedItem2(null);
      }
    });

    // Set click listener untuk Summon pada steveBoard, serta flip alexBoard
    for(int i = 0; i < 5; i++){
      VBox item = gameLayoutController.steveBoardController.summonBoxes.get(i);
      SummonController summonController = gameLayoutController.steveBoardController.summonControllers.get(i);

      item.setOnMouseClicked(new SelectableItemHandler(summonController, gameLayoutController));
      summonController.getIsHovered().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
          if(newValue.equals(true)){
            gameLayoutController.setHoverItem(summonController);
          } else{
            gameLayoutController.unsetHoverItem(summonController);
          }
        }
      });
    }

    gameLayoutController.alexBoard.setScaleX(-1); // flip alexBoard
    for(int i = 0; i < 5; i++){
      VBox item = gameLayoutController.alexBoardController.summonBoxes.get(i);
      SummonController summonController = gameLayoutController.alexBoardController.summonControllers.get(i);

      item.setScaleX(-1); // flip balik tiap cell agar tulisan dan gambarnya tidak terbalik
      item.setOnMouseClicked(new SelectableItemHandler(summonController, gameLayoutController));
      summonController.setBoardNumber(i);
      summonController.getIsHovered().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
          if(newValue.equals(true)){
            gameLayoutController.setHoverItem(summonController);
          } else{
            gameLayoutController.unsetHoverItem(summonController);
          }
        }
      });
    }

    // Inisialisasi hand
    // TIDAK PERLU mengatur listener lagi misal mau mengganti kartu di hand
    // cukup langsung memanggil, gameLayoutController.setHandCard(card, idx);
    // Untuk mengganti Summon di Board, panggil gameLayoutController.<steve atau alex>BoardController.setSummon(summon, idx);
    try {
      this.loadCards();
      CardMap cm = CardMap.getInstance();

      // Inisialisasi data
      for(int i = 0; i < 5; i++){
        Card cc = cm.get(i+1);
        VBox card = gameLayoutController.handController.getCards().get(i);
        CardController cardController = gameLayoutController.handController.getCardControllers().get(i);

        cardController.setCard(cc);
        card.setOnMouseClicked(new SelectableItemHandler(cardController, gameLayoutController));
        cardController.getIsHovered().addListener(new ChangeListener<Boolean>() {
          @Override
          public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue.equals(true)){
              gameLayoutController.setHoverItem(cardController.getCard());
            } else{
              gameLayoutController.unsetHoverItem(cardController.getCard());
            }
          }
        });
      }
      gameLayoutController.handController.updateHand(Game.getInstance().getListPlayer().get(Game.getInstance().getPlayerIndex()-1).getHand());
      gameLayoutController.drawCardSelector(stage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch();
  }
}
