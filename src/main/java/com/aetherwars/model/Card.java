package com.aetherwars.model;

public class Card implements HoverableItem{
    protected int id;
    protected String name;
    protected String description;
    protected String imgPath;

    Card(){
        this.id = -1;
        this.name = "";
        this.description = "";
        this.imgPath = "";
    }
    
    public Card(int id, String name, String description, String imgPath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
    }

    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public String getImgPath() {
        return imgPath;
    }

    public int getMana(){
        return -1;
    }
    public String getManaCost(){
        return "Mana X";
    }
    public String getHeadline(){
        return "ATK X/HP X";
    }

    @Override
    public String getHoverImagePath() {
        return this.imgPath;
    }

    @Override
    public String getHoverTitle() {
        return this.name;
    }

    @Override
    public String getHoverDetail() {
        return this.name;
    }

    @Override
    public String getHoverDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("id: %d\nname: %s\ndescription: %s\nimgPath: %s", this.id, this.name, this.description, this.imgPath);
    }
}