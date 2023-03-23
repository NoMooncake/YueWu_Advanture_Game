package adventure_game;


public class Player extends Character{
    public Player(String name, int health, int mana, int baseDamage, int exp, int level){
        super(name, health, mana, baseDamage, exp, level);
    }

    @Override
    public void takeTurn(Character other){
        if(other.canlevelup()){
            other.levelup();
            this.modifyExp(-this.getexp());
        }

        if(this.isStunned()){
            this.decreaseTurnsStunned();
            System.out.printf("%S is unable to take any actions this turn!", this.getName());
            return;
        }
        

        System.out.println();
        System.out.printf("%s has %d of %d health.\n", this.getName(), this.getHealth(), this.getMaxHealth());
        System.out.printf("%s has %d health.\n", other.getName(), other.getHealth());
        System.out.printf("Do you want to...\n");
        System.out.printf("  1: Attack?\n");
        System.out.printf("  2: Defend?\n");
        if(this.hasItems())
            System.out.printf("  3: Use an item?\n");
        if(this.getMana() >= 3) 
            System.out.printf("  4: Cast a spell?\n");
        System.out.printf("  5:Charge up mana?\n");
        System.out.printf("Enter your choice: ");


        int choice = Game.in.nextInt();
        switch(choice){
            case 1:
                this.attack(other);
                break;
            case 2:
                this.defend(other);
                break;
            case 3:
                if(hasItems()){
                    this.useItem(this, other);
                } else {
                    System.out.println("You dig through your bag but find no items. You lose a turn!!");
                }
                break;
            case 4:
                if(this.getMana()>=3){
                    other.modifyHealth(-(other.getHealth()/2));
                    this.modifyMana(-3);
                }
                else{
                    System.out.println("No enough mana to cast a spell..");
                }
                break;
            case 5:
                this.modifyMana(1);
                break;
        }
    }
}