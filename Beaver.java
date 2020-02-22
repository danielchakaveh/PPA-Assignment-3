/**
 * Class Beaver - An animal which is low down the food chain
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public class Beaver extends Animal
{
    public static double creationProbability = 0.07;
    /**
     * Create a Jackal. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the mouse will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Beaver(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location, 160, 12, 0.28, 4, 2, 0.06, 0.001);
    }

    /**
     * Creates a baby mouse
     * @param field The grid for the animal to be placed on
     * @param location  The position of the animal on the grid
     * @return Returns a new born mouse
     */
    @Override
    protected Animal returnOffspring(Field field, Location location) {
        return new Beaver(false, field, location);
    }
}

