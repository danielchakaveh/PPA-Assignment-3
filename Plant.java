import java.util.List;
import java.util.Random;

/**
 * Class Plant - Represents a plant in the simulation
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public class Plant extends Organism
{
    public static double creationProbability = 0.1;
    private static final Random rand = Randomizer.getRandom();

    /**
     * Constructor for objects of class Plant
     */
    public Plant(Field field, Location location){
        super(field, location, 1);
        // Location variable refers to 
        // the set location of animals species
    }

    /**
     *  May give birth or may not
     * @param newOrganisms A list to receive newly born animals.
     */
	@Override
    public void act(List<Organism> newOrganisms)
    {
		if(isAlive()) {
		    giveBirth(newOrganisms);
		}
	}

    /**
     * Calculates how many births for the plant to have
     * @return number of births for plant to have
     */
    @Override
    protected int breed() {
        int maxNewPlants = 3;
        double breedingProbability = 0.1;
        if(rand.nextDouble() <= breedingProbability)
        {
            return rand.nextInt(5) + 1;
        }
        else{
            return 0;
        }
    }

    /**
     *
     * @param field The grid for the animal to be placed on
     * @param location  The position of the animal on the grid
     * @return A new basic plant
     */
    @Override
    protected Organism returnOffspring(Field field, Location location) {
        return new Plant(field, location);
    }

}
