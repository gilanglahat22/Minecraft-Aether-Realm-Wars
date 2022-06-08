package com.aetherwars.controller;

public class DiscardHereController implements SelectableItem{

    @Override
    public void doAction(SelectableItem target) {
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onDeselected() {

    }

    @Override
    public boolean isEmptySelection() {
        return true;
    }
}
