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
    public Snake(Field field, Location location)
    {
        super(field, location);
    }

    public void act(List<Animal> animals)
    {
        
    }
    
    public int getTrophicLevel()
    {
        return 4;
    }
}
