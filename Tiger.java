import java.util.List;

/**
 * Class Tiger - Represents a tiger in the simulation
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public class Tiger extends Animal
{
    /**
     * Constructor for objects of class Mouse
     */
    public Tiger(boolean randomAge, Field field, Location location)
    {
        super(false, field, location, 900, 30,0.12, 4, 5);
    }

    public Tiger returnOffspring(Field field, Location location)
    {
        return new Tiger(false, field, location);
    }
}
