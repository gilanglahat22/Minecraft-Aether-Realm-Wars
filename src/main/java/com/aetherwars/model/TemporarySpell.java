package com.aetherwars.model;

public abstract class TemporarySpell implements Spell, Comparable {
    protected int durationInitial;
    protected int duration;

    public abstract void execute(Summon s); // dari interface Spell
    /** Yang dilakukan ketika spell ini expired */
    public abstract void onExpired(Summon s);

    /** Menurunkan durasi sebanyak 1, tidak melakukan apa-apa jika durasi initialnya 0 (berarti spell temporary berlaku sepanjang permainan) */
    public void decreaseDuration() {
        if(this.durationInitial == 0) return;
        this.duration--;
    }

    public boolean isExpired() {
        if(this.durationInitial == 0) return false;
        if(this.duration <= 0) return true;
        return false;
    }

    /** Membandingkan berdasarkan waktu durasi sisa, duration1 < duration2 maka return -1
     * Mempertimbangkan juga jika durasi initial objek ini 0 maka return -1, kecuali jika durasi initial pembandingnya juga 0 */
    @Override
    public int compareTo(Object o) {
        TemporarySpell ts = (TemporarySpell) o;
        if(this.durationInitial == 0 && ts.durationInitial > 0) return -1;
        if(this.durationInitial == 0 && ts.durationInitial == 0) return 0;
        if(this.durationInitial > 0 && ts.durationInitial == 0) return 1;

        if(this.duration < ts.duration) return -1;
        if(this.duration > ts.duration) return 1;
        return 0;

    }
}
