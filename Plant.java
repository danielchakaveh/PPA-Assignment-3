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
     *
     * @param newOrganisms A list to receive newly born animals.
     */
	@Override
    public void act(List<Organism> newOrganisms)
    {
		if(isAlive()) {
		    giveBirth(newOrganisms);
		}
	}

    @Override
    protected int breed() {
        int maxNewPlants = 5;
        double breedingProbability = 0.3;
        if(rand.nextDouble() <= breedingProbability)
        {
            return rand.nextInt(5);
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
