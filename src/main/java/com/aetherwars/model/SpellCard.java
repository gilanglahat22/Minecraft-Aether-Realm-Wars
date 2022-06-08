package com.aetherwars.model;

import static com.aetherwars.model.SpellCard.CharacterSpell.UNDEFINED;

public class SpellCard extends Card{
    public enum CharacterSpell {
        PERMANENT, TEMPORER, UNDEFINED
    };
    public enum TypeSpell {
        POTION, LEVEL, SWAP, MORPH, UNDEFINED
    };    
    protected TypeSpell type;
    protected int mana;
    protected CharacterSpell characterSpell;
    private Spell spell;

    // Constructor
    SpellCard(){
        super();
        this.type = TypeSpell.UNDEFINED;
        this.mana = -1;
        this.characterSpell = UNDEFINED;
    }

    public SpellCard(int id, String name, String description, String imgPath, Spell spell, int mana){
        super(id, name, description, imgPath);
        this.spell = spell;
        this.mana = mana;
    }

    public Spell getSpell() {
        return spell;
    }

    @Override
    public int getMana() {
        return this.mana;
    }

    @Override
    public String getManaCost() {
        if(this.mana == -1){
            return "";
        }
        return "MANA " + this.mana;
    }

    @Override
    public String getHeadline() {
        if(this.spell instanceof PotionSpell){
            PotionSpell ps = (PotionSpell) spell;
            String atk, hp;
            if(ps.getAttackModifier() >= 0)
                atk = "+" + ps.getAttackModifier();
            else
                atk = "" + ps.getAttackModifier();
            if(ps.getHealthModifier() >= 0)
                hp = "+" + ps.getHealthModifier();
            else
                hp = "" + ps.getHealthModifier();
            return String.format("ATK%s/HP%s", atk, hp);
        } else if(this.spell instanceof MorphSpell){
            return "MORPH";
        } else if(this.spell instanceof SwapSpell){
            SwapSpell ss = (SwapSpell) spell;
            return "SWAP " + ss.durationInitial + " TURNS";
        } else if(this.spell instanceof LevelSpell){
            return this.name;
        }
        return "";
    }
}
