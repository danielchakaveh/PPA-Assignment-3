/**
 * Class Deer - Represents a snake animal in the simulation
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public class Deer extends Animal
{
    public static double creationProbability = 0.06;
    /**
     * Create a snake. A snake can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the snake will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Deer(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location, 200, 17, 0.23, 6, 2, 0.02, 0.0001);
    }

    /**
     * Creates a baby snake with age 0.
     * @param field The grid for the snake to be placed on
     * @param location  The position of the snake on the grid
     * @return A baby snake
     */
    @Override
    public Deer returnOffspring(Field field, Location location)
    {
        return new Deer(false, field, location);
    }
}
