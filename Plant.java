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
    private static double randomSpawningProbability = 0.0001;
    private static final Random rand = Randomizer.getRandom();
    private static final double chanceOfDeathInDrought = 0.05;

    /**
     * Constructor for objects of class Plant
     */
    public Plant(Field field, Location location){
        super(field, location, 1, 0.1, 0.1, 6, 0.0001);
        // Location variable refers to 
        // the set location of animals species
    }

    /**
     *  May give birth or may not
     * @param newOrganisms A list to receive newly born animals.
     * @param weather The current weather
     * @param isDayTime
     */
	@Override
    public void act(List<Organism> newOrganisms, Weather weather, boolean isDayTime)
    {
        if(!isAlive())
            return; //Does not do anything if plant is dead

        spreadDiseases();
        affectByDiseases();
        mutateNewDisease();

        if(!isAlive())
            return; //Does not do anything if plant is dead

		if(weather == Weather.CLEAR_SKY) {
		    giveBirth(newOrganisms);
		}
		else if(weather == Weather.DROUGHT)
        {
            mightDieInDrought();
        }
		else if (weather == Weather.SNOWY)
        {
            mightDieInSnow();
        }
	}

    /**
     * Calculates randomly whether or not a plant will spawn for a given spot
     * @return whether or not a plant will spawn for a given spot
     */
	public static boolean willSpawn()
    {
        return rand.nextDouble() <= randomSpawningProbability;
    }

    /**
     * A plant may die
     * Whether on not plant dies is based off of chanceOfDeathInSnow
     */
    private void mightDieInSnow()
    {
        if(rand.nextDouble() <= chanceOfDeathInSnow)
        {
            setDead();
        }
    }

    /**
     * A plant may die
     * Whether on not plant dies is based off of chanceOfDeathInDrought
     */
	private void mightDieInDrought()
    {
        if(rand.nextDouble() <= chanceOfDeathInDrought)
        {
            setDead();
        }
    }

    /**
     * Calculates how many births for the plant to have
     * @return number of births for plant to have
     */
    @Override
    protected int breed() {
        if(rand.nextDouble() <= breedingProbability)
        {
            return rand.nextInt(maxOffspring) + 1;
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
