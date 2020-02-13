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
     * Constructor for objects of class Snake
     */
    public Snake(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location, 450, 20, 0.05, 11, 4);
    }

    @Override
    public Snake returnOffspring(Field field, Location location)
    {
        return new Snake(false, field, location);
    }
}
