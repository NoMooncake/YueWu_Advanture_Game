package adventure_game.items;
import adventure_game.Character;
import adventure_game.Player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ManaPotionTest {

    private Character c;
    @BeforeEach
    void setup(){
        c = new Player("Hero", 100, 9, 7, 0, 1);
    }

    @Test
    void testManaPotion(){
        c.modifyMana(-3);
        int initialMana = c.getMana();
        int expectedAddMana =new ManaPotion().calculateAddMana();
        c.modifyMana(expectedAddMana);
        if (expectedAddMana <= 3) {
            int actualMana = c.getMana();
            int actualAddMana = actualMana - initialMana;
            assertEquals(expectedAddMana, actualAddMana);
        }
        else {
            expectedAddMana = 3;
            int actualMana = c.getMaxMana();
            int actualAddMana = actualMana - initialMana;
            assertEquals(expectedAddMana, actualAddMana);
        }
        

    }
}