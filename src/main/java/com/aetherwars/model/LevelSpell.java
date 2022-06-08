package com.aetherwars.model;

public class LevelSpell implements Spell{
    boolean isLevelUp;

    public LevelSpell(boolean isLevelUp){
        this.isLevelUp = isLevelUp;
    }

    @Override
    public void execute(Summon s) {
        s.exp = 0;
        if(isLevelUp){
            s.levelUp();
        } else{
            s.levelDown();
        }
    }
}
