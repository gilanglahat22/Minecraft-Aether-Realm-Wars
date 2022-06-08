package com.aetherwars.model;

import java.util.ArrayList;

public class MorphSpell implements Spell{
    private int targetId;

    public MorphSpell(int targetId){
        this.targetId = targetId;
    }

    @Override
    public void execute(Summon s) {
        s.exp = 0;
        s.level = 1;

        CharacterCard cc = (CharacterCard) CardMap.getInstance().get(this.targetId);
        s.card = cc;
        s.attack = cc.baseAttack;
        s.health = cc.baseHealth;
        s.activeSpells = new ArrayList<>();
    }
}
