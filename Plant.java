import java.util.List;

/**
 * Class Plant - Represents a plant in the simulation
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public class Plant extends Organism
{
    public static double creationProbability = 0.1;

    /**
     * Constructor for objects of class Plant
     */
    public Plant(Field field, Location location){
        super(field, location, 1);
        // Location variable refers to 
        // the set location of animals species
    }

	@Override
    public void act(List<Organism> newOrganisms)
		if(isAlive()) {
            giveBirth(newOrganisms);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();/////
            }
		}

	}
}
