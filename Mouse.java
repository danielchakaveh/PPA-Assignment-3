import java.util.List;

/**
 * Write a description of class Mouse here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
        return 5;
    }
}
