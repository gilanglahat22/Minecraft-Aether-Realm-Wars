package com.aetherwars.model;

public class CharacterCard extends Card{
    public enum TypeCharacter{
        OVERWORLD, NETHER, END, UNDEFINED
      };
      
    protected TypeCharacter type;
    protected int baseAttack;
    protected int baseHealth;
    protected int mana;
    protected int attackUp;
    protected int healthUp;

    CharacterCard(){
        super();
        this.type = TypeCharacter.UNDEFINED;
        this.baseAttack = -1;
        this.baseHealth = -1;
        this.mana = -1;
        this.attackUp = -1;
        this.healthUp = -1;
    }

    public CharacterCard(int id, String name, String description, String imgPath,
                  TypeCharacter type, int baseAttack, int baseHealth, int mana,
                  int attackUp, int healthUp){
        super(id, name, description, imgPath);
        this.type = type;
        this.baseAttack = baseAttack;
        this.baseHealth = baseHealth;
        this.mana = mana;
        this.attackUp = attackUp;
        this.healthUp = healthUp;
    }

    @Override
    public int getMana() {
        return this.mana;
    }

    @Override
    public String getManaCost() {
        return "MANA " + this.mana;
    }

    @Override
    public String getHeadline() {
        return String.format("ATK %d/HP %d", this.baseAttack, this.baseHealth);
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nDescription: " + this.description + "\nType: " + this.type;
    }

    public TypeCharacter getType(){
        return this.type;
    }
}
