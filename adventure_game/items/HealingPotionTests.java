package adventure_game.items;
import adventure_game.Character;
import adventure_game.Player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class HealingPotionTests {

    private Character c;
    @BeforeEach
    void setup(){
        // TO-DO 
        // Implement this
        c = new Player("Hero", 100, 9, 7, 0, 1);
    }

    @Test
    void testHealingPotion(){
        // TO-DO
        // Implement this
        c.modifyHealth(-13);
        int initialHealth = c.getHealth();
        int expectedHealing =new HealingPotion().calculateHealing();
        c.modifyHealth(expectedHealing);
        if (expectedHealing <= 13) {
            int actualHealth = c.getHealth();
            int actualHealing = actualHealth - initialHealth;
            assertEquals(expectedHealing, actualHealing);
        }
        else {
            expectedHealing = 13;
            int actualHealth = c.getMaxHealth();
            int actualHealing = actualHealth - initialHealth;
            assertEquals(expectedHealing, actualHealing);
        }
        

    }
}