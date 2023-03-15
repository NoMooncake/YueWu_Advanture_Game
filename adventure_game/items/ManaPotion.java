package adventure_game.items;

import adventure_game.Character;
import adventure_game.Game;

public class ManaPotion implements Consumable {
    public void consume(Character owner){
        int hitPoints = calculateAddMana();
        int hitPointsFromMax = owner.getMaxMana() - owner.getMana();

        if(hitPoints > hitPointsFromMax){
            hitPoints = hitPointsFromMax;
        }
        System.out.printf("You add for %d points, back up to %d/%d.\n", hitPoints, owner.getMana(), owner.getMaxMana());
        owner.modifyMana(hitPoints);
        System.out.println(owner.getMana());
    }

    public int calculateAddMana(){
        int points = Game.rand.nextInt(3)+1;
        points += Game.rand.nextInt(3)+1;
        return points;
    }
}