
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a character in the battle simulation.
 */
public class Character {
    // Attributes of a character
    private String characterType; // Type of character ( monster, hero)
    private int healthPoints; // Health points of the character
    private int attackStrength; // Attack strength of the character
    private int defenseStrength; // Defense strength of the character

    // Constructor to create a character with random attributes
    public Character(String type) {
        this.characterType = type;
        Random rand = new Random();
        this.healthPoints = rand.nextInt(41) + 10; // Random health between 10 and 50
        this.attackStrength = rand.nextInt(91) + 10; // Random attack between 10 and 100
        this.defenseStrength = rand.nextInt(10) + 1; // Random defense between 1 and 10
    }

    //  create a character with random health but specified limit
    public Character(String type, int healthLimit) {
        this.characterType = type;
        Random rand = new Random();
        this.healthPoints = rand.nextInt(healthLimit - 9) + 10; // Random health between 10 and healthLimit
        this.attackStrength = rand.nextInt(91) + 10; // Random attack between 10 and 100
        this.defenseStrength = rand.nextInt(10) + 1; // Random defense between 1 and 10
    }

    // Getter methods
    public String getType() {
        return characterType;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public int getDefenseStrength() {
        return defenseStrength;
    }

    // Setter methods
    public void setType(String type) {
        this.characterType = type;
    }

    public void setHealthPoints(int hp) {
        this.healthPoints = hp;
    }

    public void setAttackStrength(int as) {
        this.attackStrength = as;
    }

    public void setDefenseStrength(int ds) {
        this.defenseStrength = ds;
    }

    // Method to simulate an attack on another character
    public void attack(Character target) {
        Random rand = new Random();
        int damage = this.attackStrength / target.defenseStrength; // Calculate damage
        System.out.println("The " + this.characterType + " tries to attack the " +
                target.getType() + "!");
        System.out.println("The " + target.getType() + " takes " + damage + " points of damage.");
        target.setHealthPoints(target.getHealthPoints() - damage); // Update target's health
    }

    // Method to print the stats of the character
    public void printStats() {
        System.out.println("-- " + this.characterType + " --");
        System.out.println("Health: " + this.healthPoints);
        System.out.println("Attack Str: " + this.attackStrength);
        System.out.println("Defense Str: " + this.defenseStrength);
        if (this.healthPoints <= 0) {
            System.out.println("-- DEFEATED --");
        }
    }

    /**
     * Main method to start the battle simulation.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner kbScanner = new Scanner(System.in);
        System.out.println("""

                 ----------------------------------------
                 \tWelcome to the BATTLE SIMULATOR!
                ----------------------------------------
                 """);

        int numMonsters = 0;
        // Prompt user for the number of monsters to battle
        while (numMonsters <= 0) {
            System.out.println("How many MONSTERS would you like to battle?");
            if (kbScanner.hasNextInt()) {
                numMonsters = kbScanner.nextInt();
                if (numMonsters <= 0) {
                    System.out.println("Error: You did not enter a positive integer. Please try again.");
                }
            } else {
                System.out.println("Error: You did not enter an integer. Please try again.");
                kbScanner.next(); // Clear invalid input
            }
        }

        // Create an array to store monsters
        Character[] monsterList = new Character[numMonsters];
        for (int i = 0; i < numMonsters; i++) {
            String monsterNameId = "MONSTER " + (i + 1);
            monsterList[i] = new Character(monsterNameId); // Create a monster
        }

        // Display stats of monsters
        System.out.println("Here are your MONSTERS:");
        for (Character monster : monsterList) {
            monster.printStats();
            System.out.println("------------------");
        }

        // Prompt user to create a hero
        System.out.println("---------------------------------------------------------------");
        System.out.println("Press ENTER to create a HERO...");
        kbScanner.nextLine();

        // Create a hero character
        Character newHero = new Character("HERO", 101); // Hero with 101 health
        System.out.println("Here is the Hero:");
        newHero.printStats(); // Display hero stats
        System.out.println("---------------------------------------------------------------");

        // Begin battle simulation
        System.out.println("Press ENTER to begin the battle simulation...");
        kbScanner.nextLine();

        Random rand = new Random();
        while (newHero.getHealthPoints() > 0) { // Loop until hero's health is zero
            int randomIndex = rand.nextInt(numMonsters); // Choose a random monster
            Character targetMonster = monsterList[randomIndex];
            if (targetMonster.getHealthPoints() > 0) { // Check if monster is still alive
                newHero.attack(targetMonster); // Hero attacks monster
                for (Character monster : monsterList) { // Each monster attacks hero
                    if (monster.getHealthPoints() > 0) {
                        monster.attack(newHero);
                    }
                }
                // Display current stats of hero and monsters
                System.out.println("\n---------------------------------------------------------------");
                System.out.println("--- CURRENT STATS ---");
                System.out.println("-- HERO --");
                newHero.printStats();
                System.out.println("------------------");
                for (Character monster : monsterList) {
                    monster.printStats();
                    System.out.println("------------------");
                }
                // Prompt user for next round
                System.out.println("\nPress ENTER to begin another battle round...\n");
                kbScanner.nextLine();
            } else {
                System.out.println("The Monsters are already defeated!");
                break;
            }
        }
        // Print end-game message based on the simulation outcome
        System.out.println("\n---------------------------------------------------------------");
        if (newHero.getHealthPoints() <= 0) {
            System.out.println("THE HERO HAS BEEN DEFEATED -- GAME OVER!");
        } else {
            boolean allMonstersDefeated = true;
            for (Character monster : monsterList) {
                if (monster.getHealthPoints() > 0) {
                    allMonstersDefeated = false;
                    break;
                }
            }
            if (allMonstersDefeated) {
                System.out.println("VICTORY! -- THE HERO HAS DEFEATED ALL THE MONSTERS!");
            } else {
                System.out.println("THE HERO HAS WON!");
            }
            System.out.println("---------------------------------------------------------------");
        }
    }
}
