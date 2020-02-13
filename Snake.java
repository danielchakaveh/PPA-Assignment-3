import java.util.List;

/**
 * Class Snake - Represents a snake animal in the simulation
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public class Snake extends Animal
{
    /**
     * Create a snake. A snake can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the snake will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Snake(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location, 450, 20, 0.05, 11, 4);
    }

    /**
     * Creates a baby snake with age 0.
     * @param field The grid for the snake to be placed on
     * @param location  The position of the snake on the grid
     * @return A baby snake
     */
    @Override
    public Snake returnOffspring(Field field, Location location)
    {
        return new Snake(false, field, location);
    }
}
