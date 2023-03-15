package adventure_game;

/*
 * Project-01: Adventure Game
 * Name: Yue Wu
 */

import java.util.Scanner;

import adventure_game.items.HealingPotion;

import java.util.Random;

public class Game {
    static Scanner in = new Scanner(System.in);
    public static Random rand = new Random();
    private Player player;
    
    public static void main(String[] args){

        Game game = new Game();

        game.createPlayer();
        System.out.println(game.player.toString());

        NPC opponent = new NPC("Geoff", 200, 0, 10);
        System.out.println(opponent.toString());
        game.enterCombat(opponent);

        in.close();
    }

    public Game() {
        
    }

    public void createPlayer(){
        try (/* TO-DO */
                /* Modify this method to allow the user to create their own player */
                /* The user will specify the player's name and description, and spend */
                /* 20 points on health, mana, and baseDamage as they see fit. */
        Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the character creation screen!");
            System.out.print("Please enter your character name: ");
            String name = scanner.nextLine();
            
            int healthPoints = 0;
            int damagePoints = 0;
            int manaPoints = 0;
            int statPoints = 20;
            
            System.out.println("\nYou have 20 stat points to spend on your character.");
            while (statPoints > 0) {
                System.out.printf("\nYou have %d stat points remaining.\n", statPoints);
                System.out.println("Which stat would you like to increase?");
                System.out.println("1. Health (+10 health points per point)");
                System.out.println("2. Damage (+1 base damage per point)");
                System.out.println("3. Mana (+3 mana points per point)");
                System.out.print("Enter the number of your choice: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        healthPoints++;
                        statPoints--;
                        System.out.printf("Health increased to %d (+%d)\n", healthPoints*10, healthPoints);
                        break;
                    case 2:
                        damagePoints++;
                        statPoints--;
                        System.out.printf("Damage increased to %d (+%d)\n", damagePoints, damagePoints);
                        break;
                    case 3:
                        manaPoints++;
                        statPoints--;
                        System.out.printf("Mana increased to %d (+%d)\n", manaPoints*3, manaPoints);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
            
            System.out.println("\nCharacter created! Here are your stats:");
            System.out.printf("Name: %s\n", name);
            System.out.printf("Health: %d\n", healthPoints*10);
            System.out.printf("Damage: %d\n", damagePoints);
            System.out.printf("Mana: %d\n", manaPoints*3);
            Player player = new Player(name, healthPoints*10, damagePoints, manaPoints*3);
            player.obtain(new HealingPotion());
        }
    }

    public void enterCombat(NPC opponent){
        System.out.printf("%s and %s are in a brawl to the bitter end.\n", this.player.getName(), opponent.getName());
        while(true){
            this.player.takeTurn(opponent);
            if(!opponent.isAlive()){
                System.out.printf("%S is SLAIN!!\n",opponent.getName());
                break;
            }

            opponent.takeTurn(this.player);
            if(!this.player.isAlive()){
                System.out.printf("%S is SLAIN!!\n",this.player.getName());
                break;
            }
        }
    }
}