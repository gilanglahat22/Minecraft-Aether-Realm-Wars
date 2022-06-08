package com.aetherwars.controller;

public interface SelectableItem {
    void doAction(SelectableItem target);
    void onSelected();
    void onDeselected();
    boolean isEmptySelection();
}
