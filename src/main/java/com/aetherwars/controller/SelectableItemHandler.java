package com.aetherwars.controller;

import com.aetherwars.model.CharacterCard;
import com.aetherwars.model.Game;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SelectableItemHandler implements EventHandler<MouseEvent> {
    private SelectableItem selectableItem;
    private GameLayoutController gameLayoutController;

    public SelectableItemHandler(SelectableItem selectableItem, GameLayoutController gameLayoutController){
        this.selectableItem = selectableItem;
        this.gameLayoutController = gameLayoutController;
    }

    @Override
    public void handle(MouseEvent event) {
        if(gameLayoutController.getSelectedItem1() == null){
            if(!selectableItem.isEmptySelection()){
                if(selectableItem instanceof SummonController && ((SummonController)selectableItem).getPlayer() != Game.getInstance().getPlayerIndex())
                    return;
                gameLayoutController.setSelectedItem1(selectableItem);
            }
        } else if(gameLayoutController.getSelectedItem2() == null){
            if(gameLayoutController.getSelectedItem1() == selectableItem){
                gameLayoutController.setSelectedItem1(null);
            } else{
                gameLayoutController.getSelectedItem1().doAction(selectableItem);
                gameLayoutController.setSelectedItem1(null);
            }
        }
        event.consume();
    }
}
