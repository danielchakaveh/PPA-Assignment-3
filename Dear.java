/**
 * Class Deer - Represents a deer animal in the simulation
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public class Deer extends Animal
{
    public static double creationProbability = 0.05;
    /**
     * Create a deer. A deer can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the deer will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Deer(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location, 450, 12, 0.01, 6, 2, 0.1);
    }

    /**
     * Creates a baby deer with age 0.
     * @param field The grid for the deer to be placed on
     * @param location  The position of the deer on the grid
     * @return A baby deer
     */
    @Override
    public Deer returnOffspring(Field field, Location location)
    {
        return new Deer(false, field, location);
    }
}
