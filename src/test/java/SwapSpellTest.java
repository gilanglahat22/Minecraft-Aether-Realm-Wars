import com.aetherwars.model.*;
import org.junit.Assert;
import org.junit.Test;

public class SwapSpellTest {
    @Test
    public void swapSpellSimple(){
        CharacterCard cc = new CharacterCard(1, "Creeper", "Bisa meledak", "",
                CharacterCard.TypeCharacter.OVERWORLD, 10, 2, 4, 1, 1);
        Summon s = new Summon(cc);
        SwapSpell swapSpell = new SwapSpell(4);

        swapSpell.execute(s);
        s.getActiveSpells().add(swapSpell);

        Assert.assertEquals(10, (long)s.getHealth());
        Assert.assertEquals(2, (long)s.getAttack());

        SwapSpell swapSpell2 = new SwapSpell(8);
        swapSpell2.execute(s);
        Assert.assertEquals(10, (long)s.getHealth());
        Assert.assertEquals(2, (long)s.getAttack());

        swapSpell.onExpired(s);
        Assert.assertEquals(2, (long)s.getHealth());
        Assert.assertEquals(10, (long)s.getAttack());
    }

    @Test
    public void swapSpellComplex(){
        CharacterCard cc = new CharacterCard(1, "Creeper", "Bisa meledak", "",
                CharacterCard.TypeCharacter.OVERWORLD, 10, 2, 4, 1, 1);
        Summon s = new Summon(cc);
        PotionSpell ps = new PotionSpell(2, 5, 5);
        s.getActiveSpells().add(ps);

        SwapSpell swapSpell = new SwapSpell(4);
        swapSpell.execute(s);

        Assert.assertEquals(12, (long)s.getHealthEff());
        Assert.assertEquals(7, (long)s.getAttackEff());

        s.reduceHealthEff(6);
        s.getActiveSpells().remove(0);

        Assert.assertEquals(6, (long)s.getHealthEff());
    }
}
