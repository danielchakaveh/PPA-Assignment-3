import java.util.List;

/**
 * Class Tiger - Represents a tiger in the simulation
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public class Tiger extends Animal
{
    public static double creationProbability = 0.01;
    /**
     * Create a tiger. A tiger can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the tiger will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Tiger(boolean randomAge, Field field, Location location)
    {
        super(false, field, location, 900, 16,0.05, 3, 5, 0.03);
    }

    public boolean canEat(Organism organism)
    {
        return true;
    }

    /**
     * Creates a baby tiger with age 0
     *
     * @param field The grid for the tiger to be placed on
     * @param location  The position of the tiger on the grid
     * @return A baby tiger
     */
    public Tiger returnOffspring(Field field, Location location)
    {
        return new Tiger(false, field, location);
    }
}
