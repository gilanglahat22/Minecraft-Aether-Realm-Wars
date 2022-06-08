import com.aetherwars.model.*;
import org.junit.Test;
import org.junit.Assert;

public class LevelSpellTest {
    @Test
    public void testLevelUp(){
        CharacterCard cc = new CharacterCard(1, "Zombie", "Mob luar biasa", "",
                CharacterCard.TypeCharacter.NETHER, 3, 4, 3, 2, 4);
        Summon summon = new Summon(cc);
        Spell spell = new LevelSpell(true);

        summon.setHealth(2);
        spell.execute(summon);
        Assert.assertEquals(4 + 4, (long)summon.getHealth());
        Assert.assertEquals(3 + 2, (long)summon.getAttack());

        summon.setLevel(10);
        spell.execute(summon);
        Assert.assertEquals(4 + 9*4, (long) summon.getHealth());
        Assert.assertEquals(3 + 9*2, (long)summon.getAttack());
    }

    @Test
    public void testLevelDown(){
        CharacterCard cc = new CharacterCard(1, "Zombie", "Mob luar biasa", "",
                CharacterCard.TypeCharacter.NETHER, 3, 4, 3, 2, 4);
        Summon summon = new Summon(cc);
        Spell spell = new LevelSpell(false);

        summon.setHealth(2);
        spell.execute(summon);
        Assert.assertEquals(2, (long)summon.getHealth());
        Assert.assertEquals(3, (long)summon.getAttack());

        summon.setLevel(10);
        spell.execute(summon);
        Assert.assertEquals(2, (long) summon.getHealth());
        Assert.assertEquals(3 + 8*2, (long)summon.getAttack());

        summon.setLevel(9);
        summon.setHealth(4 + 9*4);
        spell.execute(summon);
        Assert.assertEquals(4 + (8-1)*4, (long)summon.getHealth());
    }
}
