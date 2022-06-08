package com.aetherwars.model;

public class PotionSpell extends TemporarySpell{
    private int attackModifier;
    private int healthModifier;

    public PotionSpell(int attackModifier, int healthModifier, int duration){
        this.attackModifier = attackModifier;
        this.healthModifier = healthModifier;

        this.durationInitial = duration;
        this.duration = duration;
    }

    public int getAttackModifier(){
        return this.attackModifier;
    }
    public void setAttackModifier(int attackModifier) {
        this.attackModifier = attackModifier;
    }
    public int getHealthModifier(){
        return this.healthModifier;
    }
    public void setHealthModifier(int healthModifier) {
        this.healthModifier = healthModifier;
    }

    /** Hanya memodifikasi attack dan health
     * Pembunuhan karakter jika health < 0 diserahkan sepenuhnya ke pemanggil
     * TODO: sementara ini pengurangan health masih dari base health, perlu dijadikan berdasarkan potion terakhir */
    @Override
    public void execute(Summon s) {
        // DO NOTHING: potion cukup ditambahkan ke List of active spells
    }

    @Override
    public void onExpired(Summon s) {
        // DO NOTHING: potion cukup dihapus dari List of active spells
    }
}
