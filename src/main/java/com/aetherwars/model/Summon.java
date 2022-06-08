package com.aetherwars.model;

import com.aetherwars.model.CharacterCard.TypeCharacter;
import java.util.ArrayList;
import java.util.List;

public class Summon {
    protected CharacterCard card;
    protected Integer attack;
    protected Integer health;
    protected Integer exp;
    protected Integer level;

    // attribute?
    protected List<Spell> activeSpells;

    public Summon(){
        this.card = null;
        this.health = 0;
        this.attack = 0;
        this.exp = 0;
        this.level = 0;
        this.activeSpells = new ArrayList<>();
    }

    public Summon(CharacterCard card){
        this.card = card;
        this.attack = card.baseAttack;
        this.health = card.baseHealth;
        this.exp = 0;
        this.level = 1;
        this.activeSpells = new ArrayList<>();
    }

    public Summon(Summon summon){
        this.card = summon.card;
        this.attack = summon.attack;
        this.health = summon.health;
        this.exp = summon.exp;
        this.level = summon.level;
        this.activeSpells = summon.activeSpells;
    }

    public void ReduceHealthEfficiencyByModifier(Summon summonAttacker, Summon summonDefender){
        if (summonAttacker.card.getType() == TypeCharacter.OVERWORLD && summonDefender.card.getType() == TypeCharacter.END){
            summonDefender.reduceHealthEff((int)(2 * summonAttacker.getAttackEff()));
        }
        else if (summonDefender.card.getType() == TypeCharacter.OVERWORLD && summonAttacker.card.getType() == TypeCharacter.END){
            summonDefender.reduceHealthEff((int)(0.5 * summonAttacker.getAttackEff()));
        }
        else if (summonDefender.card.getType() == TypeCharacter.NETHER && summonAttacker.card.getType() == TypeCharacter.END){
            summonDefender.reduceHealthEff((int)(2 * summonAttacker.getAttackEff()));
        }
        else if (summonAttacker.card.getType() == TypeCharacter.NETHER && summonDefender.card.getType() == TypeCharacter.END){
            summonDefender.reduceHealthEff((int)(0.5 * summonAttacker.getAttackEff()));
        }
        else if (summonAttacker.card.getType() == TypeCharacter.NETHER && summonDefender.card.getType() == TypeCharacter.OVERWORLD){
            summonDefender.reduceHealthEff((int)(2 * summonAttacker.getAttackEff()));
        }
        else if (summonDefender.card.getType() == TypeCharacter.NETHER && summonAttacker.card.getType() == TypeCharacter.OVERWORLD){
            summonDefender.reduceHealthEff((int)(0.5 * summonAttacker.getAttackEff()));
        }
    }

    public void attack(Summon opponent){
        int ourLevel = this.level, opponentLevel = opponent.level;
        this.ReduceHealthEfficiencyByModifier(this, opponent);
        this.ReduceHealthEfficiencyByModifier(opponent, this);

        if(this.getHealthEff() <= 0 && opponent.getHealthEff() <= 0){
        } else if(opponent.getHealthEff() <= 0){
            this.addExp(opponentLevel);
        } else if(this.getHealthEff() <= 0){
            opponent.addExp(ourLevel);
        }
    }
    public void apply(Spell spell){
        if(spell instanceof TemporarySpell){
            this.activeSpells.add(spell);
        }
    }

    /** Mendapatkan nilai attack efektif, yaitu attack dengan memperhatikan efek PotionSpell */
    public Integer getAttackEff(){
        int total = attack;
        for(Spell spell : this.activeSpells){
            if(spell instanceof PotionSpell){
                total += ((PotionSpell) spell).getAttackModifier();
            }
        }
        return total;
    }

    /** Mendapatkan nilai health efektif, yaitu health dengan memperhatikan efek PotionSpell */
    public Integer getHealthEff(){
        int total = health;
        for(Spell spell : this.activeSpells){
            if(spell instanceof PotionSpell){
                total += ((PotionSpell) spell).getHealthModifier();
            }
        }
        return total;
    }

    /** Mengurangi nilai health dari PotionSpell terbaru terlebih dahulu */
    public void reduceHealthEff(int amount){
        for(int i = this.activeSpells.size()-1; i >= 0; i--){
            if(amount == 0) break;
            if(this.activeSpells.get(i) instanceof PotionSpell){
                PotionSpell sp = (PotionSpell) this.activeSpells.get(i);
                if(sp.getHealthModifier() > 0){
                    int reduction = Math.min(amount, sp.getHealthModifier());
                    amount -= reduction;
                    sp.setHealthModifier(sp.getHealthModifier() - reduction);
                }
            }
        }
        this.health -= amount;
    }

    public void addExp(Integer expAdd){
        this.exp += expAdd;
        while(this.exp >= this.getNextLevelExp()){
            this.exp -= this.getNextLevelExp();
            this.levelUp();
        }
    }
    public void levelUp(){
        this.level = Math.min(10, this.level+1);
        this.attack = this.card.baseAttack + (this.level-1)*this.card.attackUp;
        this.health = this.card.baseHealth + (this.level-1)*this.card.healthUp;
    }
    public void levelDown(){
        this.level = Math.max(1, this.level-1);
        this.attack = this.card.baseAttack + (this.level-1)*this.card.attackUp;
        this.health = Math.min(this.health, this.card.baseHealth + (this.level-1)*this.card.healthUp);
    }

    public CharacterCard getCard() {
        return card;
    }
    public void setCard(CharacterCard card) {
        this.card = card;
    }
    public Integer getAttack() {
        return attack;
    }
    public void setAttack(Integer attack) {
        this.attack = attack;
    }
    public Integer getHealth() {
        return health;
    }
    public void setHealth(Integer health) {
        this.health = health;
    }
    public Integer getExp() {
        return exp;
    }
    public Integer getNextLevelExp(){
        return 1 + (this.level-1)*2;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public List<Spell> getActiveSpells() {
        return activeSpells;
    }
}
