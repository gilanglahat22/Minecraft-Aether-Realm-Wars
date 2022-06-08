package com.aetherwars.model;

import java.util.List;

public class SwapSpell extends TemporarySpell {
    public SwapSpell(int duration){
        this.durationInitial = duration;
        this.duration = duration;
    }

    @Override
    public void execute(Summon s) {
        List<Spell> listSpell = s.getActiveSpells();
        boolean swapExists = false;
        for(Spell spell : listSpell){
            if(spell instanceof SwapSpell && spell != this){
                swapExists = true;
                break;
            }
        }
        // Jika belum ada spell, swap attack dan health dari tiap PotionSpell
        if(!swapExists){
            int temp = s.attack;
            s.attack = s.health;
            s.health = temp;
            for(Spell spell : listSpell){
                if(spell instanceof PotionSpell){
                    PotionSpell ps = (PotionSpell) spell;
                    temp = ps.getAttackModifier();
                    ps.setAttackModifier(ps.getHealthModifier());
                    ps.setHealthModifier(temp);
                }
            }
        } // kalau sudah ada spell swap do nothing, hanya durasinya akan bertambah
    }

    @Override
    public void onExpired(Summon s) {
        List<Spell> listSpell = s.getActiveSpells();
        boolean swapExists = false;
        for(Spell spell : listSpell){
            if(spell instanceof SwapSpell && spell != this){
                swapExists = true;
                break;
            }
        }
        if(!swapExists){
            int temp = s.attack;
            s.attack = s.health;
            s.health = temp;
            for(Spell spell : listSpell){
                if(spell instanceof PotionSpell){
                    PotionSpell ps = (PotionSpell) spell;
                    temp = ps.getAttackModifier();
                    ps.setAttackModifier(ps.getHealthModifier());
                    ps.setHealthModifier(temp);
                }
            }
        }
    }
}
