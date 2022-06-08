import com.aetherwars.model.*;
import org.junit.Assert;
import org.junit.Test;

public class MorphSpellTest {
    @Test
    public void morphSpellTest(){
        CharacterCard cc1 = new CharacterCard(1, "Creeper", "Bisa meledak", "",
                CharacterCard.TypeCharacter.OVERWORLD, 10, 2, 4, 1, 1);
        CharacterCard cc2 = new CharacterCard(5, "Enderman", "Mob mengerikan", "",
                CharacterCard.TypeCharacter.NETHER, 8, 6, 5, 4, 3);
        CardMap cm = CardMap.getInstance();
        cm.set(cc1.getId(), cc1);
        cm.set(cc2.getId(), cc2);
        Summon summon = new Summon(cc1);
        MorphSpell ms = new MorphSpell(5);

        ms.execute(summon);
        Assert.assertEquals(0, (long)summon.getExp());
        Assert.assertEquals(1, (long)summon.getLevel());
        Assert.assertEquals(5, summon.getCard().getId());
        Assert.assertEquals(8, (long)summon.getAttack());
        Assert.assertEquals(6, (long)summon.getHealth());
    }
}
