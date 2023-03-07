package adventure_game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;



public class CharacterTests{

    private Character c,d;
    @BeforeEach
    void setup(){
        c = new Player("Hero1", 100, 9, 4);
        d = new Player("Hero2", 100, 9, 4);
    }

    @Test
    void testModifyHealth(){
        assertTrue(c.getHealth() == 100);
        c.modifyHealth(-10);
        assertTrue(c.getHealth() == 90);
    }

    @Test
    public void testAttack() {
        assertTrue(c.getHealth() == 100);
        d.attack(c);
        assertTrue(c.getHealth() >= 100 - d.getBaseDamage());
    }

    @Test
    public void testInvincibleAttack() {
        c.setAsInvincible(1);
        d.attack(c);
        assertTrue(c.getHealth() == 100);
    }

    @Test
    public void testVulnerableAttack() {
        c.setAsVulnerable(1);
        d.attack(c);
        assertTrue(c.getHealth() <= 100 - d.getBaseDamage());
    }

    @Test
    public void testDefend() {
        c.defend(c);
        if (c.isInvincible() == true ) {
            c.attack(d);
            assertTrue(d.getHealth() < d.getMaxHealth() - c.getBaseDamage());
        }
        else {
            assertTrue(c.isVulnerable());
        }
    }
    

}