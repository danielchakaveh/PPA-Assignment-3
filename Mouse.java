import java.util.List;

/**
 * Class Mouse - An animal which is low down the food chain
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public class Mouse extends Animal
{

    /**
     * Constructor for objects of class Mouse
     */
    public Mouse(Field field, Location location)
    {
        super(field, location);
    }

    public void act(List<Animal> animals)
    {
        
    }
    
    public int getTrophicLevel()
    {
        return 2;
    }
}
