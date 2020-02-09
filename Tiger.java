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
    public Tiger(Field field, Location location)
    {
        super(field, location);
    }

    public void act(List<Animal> animals)
    {
        
    }
    
    public int getTrophicLevel()
    {
        return 5;
    }
}
