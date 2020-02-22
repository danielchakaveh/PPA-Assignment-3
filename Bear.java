/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Bear extends Animal
{
    public static double creationProbability = 0.026;
    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Bear(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location, 300, 35, 0.12, 2, 4, 0, 0.0003);
    }

    /**
     *
     * @param field The grid for the animal to be placed on
     * @param location  The position of the animal on the grid
     * @return Returns a new born fox
     */
    @Override
    protected Animal returnOffspring(Field field, Location location) {
        return new Bear(false, field, location);
    }
}
