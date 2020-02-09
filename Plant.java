import java.util.List;

/**
 * Class Plant - Represents a plant in the simulation
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public class Plant extends Organism
{
    /**
     * Constructor for objects of class Plant
     */
    public Plant(Field field, Location location)
    {
        super(field, location);
        // Location variable refers to 
        // the set location of animals species
    }

        public int getTrophicLevel()
    {
        return 1;
    }
    
}
