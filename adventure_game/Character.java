package adventure_game;
import java.util.ArrayList;


import adventure_game.items.Consumable;
/**
* Abstract class representing a character in the game.
*/
abstract public class Character{
    private int maxHealth;
    private int health;

    private int maxMana;
    private int mana;

    private int baseDamage;

    private String name;

    private ArrayList<Consumable> items;

    // Character Conditions:
    private int turnsVulnerable; // number of turns Character is vulnerable
    private int turnsInvincible; // number of turns Character takes no damage
    private int turnsStunned; // number of turns Character gets no actions
    // buffer factor for next attack
    // E.g, if 2.0, the next attack will do double damage
    private double tempDamageBuff;

    /**
    * Constructor for the Character class.
    * @param name The name of the character.
    * @param health The maximum health points of the character.
    * @param mana The maximum mana points of the character.
    * @param damage The base damage of the character's attacks.
    */
    public Character(String name, int health, int mana, int damage){
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.maxMana = mana;
        this.mana = mana;
        this.baseDamage = damage;
        this.tempDamageBuff = 1.0;
        items = new ArrayList<Consumable>();
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString(){
        String output;
        output = "";
        output += "Name " + getName() + "\n";
        output += "hp " + getHealth() + "\n";
        output += "mana " + getMana() + "\n";
        output += "damage " + getBaseDamage() + "\n";
        return output;
    }

    /**
     * Get the name of this Character
     * @return the name of this Character
     */
    public String getName(){
        return this.name;
    }

    /**
    * Get the current health points of the character.
    * @return The current health points of the character.
    */
    public int getHealth(){
        return this.health;
    }

    /**
    * Get the maximum health points of the character.
    * @return The maximum health points of the character.
    */
    public int getMaxHealth(){
        return this.maxHealth;
    }

    /**
    * Get the maximum mana points of the character.
    * @return The maximum mana points of the character.
    */
    public int getMaxMana(){
        return this.maxMana;
    }

    /**
    * Get the current mana points of the character.
    * @return The current mana points of the character.
    */
    public int getMana(){
        return this.mana;
    }

    /**
    * Get the base damage of the character's attacks.
    * @return The base damage of the character's attacks.
    */
    public int getBaseDamage(){
        return this.baseDamage;
    }

    /**
    * Check if the character is alive.
    * @return true if the character is alive, false otherwise.
    */
    public boolean isAlive(){
        return this.health > 0;
    }

    /**
    * Method for the character to take its turn in combat.
    * @param other The opponent of the character in combat.
    */
    abstract void takeTurn(Character other);
    
    /**
    * Attack the opponent character.
    * @param other The opponent character.
    */
    public void attack(Character other){
        if(other.isInvincible()){
            System.out.printf("%S is unable to attack %S!\n", 
                                this.getName(), 
                                other.getName());
            other.decreaseTurnsInvincible();
            return;
        }
        double modifier = Game.rand.nextDouble();
        modifier = (modifier*0.4) + 0.8;
        int damage = (int)(this.baseDamage * modifier);
        // apply temporary damage buff, then reset it back to 1.0
        damage *= this.tempDamageBuff;
        this.tempDamageBuff = 1.0;

        if(other.isVulnerable()){
            damage *= 1.5;
            other.decreaseTurnsVulnerable();
        }

        System.out.printf("%s dealt %d damage to %s\n", 
                            this.getName(), 
                            damage, 
                            other.getName());
        other.modifyHealth(-damage);
    }

    /**
    * Defend attack form the opponent character.
    * @param other The opponent character.
    */
    public void defend(Character other){
        double chance = Game.rand.nextDouble();
        if(chance <=0.75){
            System.out.printf("%s enters a defensive posture and charges up their next attack!\n", this.getName());
            this.setAsInvincible(1);
            this.setTempDamageBuff(2.0);
        } else {
            System.out.printf("%s stumbles. They are vulnerable for the next turn!\n", this.getName());
            this.setAsVulnerable(1);
        }
    }
    
    /**
     * Modify character's health.
     * @param modifier amount of health to add or deduct.
     */
    public void modifyHealth(int modifier) {
        this.health += modifier;
        if(this.health < 0){
            this.health = 0;
        }
        if(this.health > this.getMaxHealth()){
            this.health = this.getMaxHealth();
        }
    }

    /* CONDITIONS */

    /**
     * Set the character's state to vulnerable.
     * @param numTurns turns that the character become vulnerable.
     */
    public void setAsVulnerable(int numTurns){
        this.turnsVulnerable = numTurns;
    }

    /**
     * Check if the character is vulnerable.
     * @return ture if the character is vulnerable.
     */
    public boolean isVulnerable(){
        return this.turnsVulnerable > 0;
    }

    /**
     * Deduct turns of vulnerable.
     */
    public void decreaseTurnsVulnerable(){
        this.turnsVulnerable--;
    }

    /**
     * Set the character invincible.
     * @param numTurns turns that the character being invincible.
     */
    public void setAsInvincible(int numTurns){
        this.turnsInvincible = numTurns;
    }

    /**
     * Check if the character is invincible.
     * @return ture if the character is invincible
     */
    public boolean isInvincible(){
        return this.turnsInvincible > 0;
    }

    /**
     * Deduct turns that character be in invincible state.
     */
    public void decreaseTurnsInvincible(){
        this.turnsInvincible--;
    }


    /**
     * Set the state of character be stunned.
     * @param numTurns turns that the character be stunned.
     */
    public void setAsStunned(int numTurns){
        this.turnsStunned = numTurns;
    }

    /**
     * Check if the character is stunned.
     * @return true if the character is stunned.
     */
    public boolean isStunned(){
        return this.turnsStunned > 0;
    }

    /**
     * Deduct turns that the character be stunned.
     */
    public void decreaseTurnsStunned(){
        this.turnsStunned--;
    }

    /**
     * Set the temporary damage buff. 
     * 
     * This is a multiplicative factor which will modify the damage 
     * for the next attack made by this Character. After the next 
     * attack, it will get reset back to 1.0
     * 
     * @param buff the multiplicative factor for the next attack's
     * damage.
     */
    public void setTempDamageBuff(double buff){
        this.tempDamageBuff = buff;
    }

    /**
     * Set the character consume items.
     * @param item that be consume by the character.
     */
    public void obtain(Consumable item){
        items.add(item);
    }

    /**
     * Let the character use the item.
     * @param owner use item to themselves.
     * @param other use item to others.
     */
    public void useItem(Character owner, Character other){
        int i = 1;
        System.out.printf("  Do you want to use:\n");
        for(Consumable item : items){
            System.out.printf("    %d: %S\n", i, item.getClass().getName());
            i++;
        }
        System.out.print("  Enter your choice: ");
        int choice = Game.in.nextInt();
        items.get(choice-1).consume(owner);
        items.remove(choice-1);
    }

    /**
     * Check if the character has item.
     * @return true if character has item.
     */
    public boolean hasItems(){
        return !items.isEmpty();
    }
}