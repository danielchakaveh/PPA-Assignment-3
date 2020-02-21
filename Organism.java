import java.util.*;

/**
 * A class representing all living things
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public abstract class Organism
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The level of the organism in the food chain
    private int trophicLevel;
    // The chance of an organism dying from snow at any step
    protected double chanceOfDeathInSnow;
    // The chance of 2 breedable organisms breeding at any step
    protected double breedingProbability;
    // The most offspring an organism can create at once
    protected int maxOffspring;
    // The probability of a disease first appearing in this organism
    private double diseaseMutationProbability;
    protected Random rand = Randomizer.getRandom();
    // The diseases an organism has
    private Set<Disease> diseases = new HashSet<Disease>();

    /**
     * Create a new animal at location in field.
     *  @param field The field currently occupied.
     * @param location The location within the field.
     * @param trophicLevel The animals position in the food chain
     * @param chanceOfDeathInSnow The chance of an organism dying from snow at any step
     * @param breedingProbability The chance of 2 breedable organisms breeding at any step
     * @param maxOffspring The most offspring an organism can create at once
     * @param diseaseMutationProbability The probability of a disease first appearing in this organism
     */
    public Organism(Field field, Location location, int trophicLevel, double chanceOfDeathInSnow, double breedingProbability, int maxOffspring, double diseaseMutationProbability)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        this.trophicLevel = trophicLevel;
        this.chanceOfDeathInSnow = chanceOfDeathInSnow;
        this.breedingProbability = breedingProbability;
        this.maxOffspring = maxOffspring;
        this.diseaseMutationProbability = diseaseMutationProbability;
        
        if(field == null || location == null)
        {
            System.out.println("Invalid organism");
        }
    }

    protected void spreadDiseases()
    {
        for (Location adjacentLocation: field.adjacentLocations(location)) {
            if(field.getObjectAt(adjacentLocation).getClass() == getClass())    //disease only spreads between organisms of same species
            {
                for (Disease disease: diseases) {
                    if(disease.wouldSpread())
                    {
                        ((Organism)field.getObjectAt(adjacentLocation)).giveDisease(disease);
                    }
                }
            }
        }
    }

    /**
     * Gives a disease to the organism
     * @param disease The disease to be contracted
     */
    public void giveDisease(Disease disease)
    {
        diseases.add(disease);
    }

    /**
     * May randomly contract a disease based off of diseaseMutationProbability
     */
    protected void mutateNewDisease()
    {
        if(rand.nextDouble() <= diseaseMutationProbability)
        {
            giveDisease(Disease.getRandomDisease());
        }
    }

    /**
     * Makes each contracted disease affect the current organism
     */
    protected void affectByDiseases()
    {
        for (Disease disease: diseases) {
            disease.affect(this);
        }
    }

    /**
     * Returns the amount of additional days the predator can go without needing food.
     */
    public int getFoodValue()
    {
        return getTrophicLevel() * getTrophicLevel() * 32;
    }

    /**
     * Returns the animals position in the food heirarchy.
     * Animals can eat other animals that are 1 position below them.
     */
    final public int getTrophicLevel()
    {
        return trophicLevel;
    }

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Make this organism act - that is: make it do
     * whatever it wants/needs to do.
     * @param newOrganisms A list to receive newly born animals.
     * @param weather The weather at the time of acting
     * @param isDayTime True if the current time is day time
     */
    public abstract void act(List<Organism> newOrganisms, Weather weather, boolean isDayTime);

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Check whether or not this organism is to give birth at this step.
     * If so, new instances of that organism are put into adjacent locations.
     * New births will be made into free adjacent locations.
     * @param newOrganism A list to return newly born foxes.
     */
    protected void giveBirth(List<Organism> newOrganism)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = new ArrayList<>();

        free = field.getFreeAdjacentLocations(getLocation());

        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Organism young = returnOffspring(field, loc);
            // System.out.println(young.getField());
            // System.out.println(young.getLocation());
            newOrganism.add(young);
        }
    }

    /**
     * Calculates the number of births for an  organism to have
     * @return Number of births for an organism to have
     */
    protected abstract int breed();

    /**
     * Returns an offspring of the animal
     * @param field The grid for the animal to be placed on
     * @param location  The position of the animal on the grid
     * @return New instance of specific animal
     */
    protected abstract Organism returnOffspring(Field field, Location location);

    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        if(field == null) {
            System.out.println(this.getClass().getName());
            System.out.println("Null");
        }
        return field;
    }
}
