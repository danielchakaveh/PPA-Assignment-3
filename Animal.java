import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal extends Organism
{
    private int age;
    // Age of the animal organism.
    private int maxAge;
    // The animal's food level, which is increased by eating rabbits.
    private int foodLevel;
    // The age at which the animal can first breed
    private int breedingAge;
    // Minimum maturity age to reproduce.
    private double breedingProbability;
    // Maximum number of offspring in a single pregnancy.
    private int maxLitterSize;
    // The gender of the animal
    private Gender gender;
    // The chance of animal dying due to snow at any time
    private double chanceOfDeathInSnow;

    private static final Random rand = Randomizer.getRandom();
    // A shared random number generator to control breeding.

    /**
     * Create a new animal at location in field.
     *
     * @param randomAge True if you want the animal to be given a random age
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param maxAge The age in steps at which the animal dies
     * @param breedingAge The age in steps at which the animal can start breeding
     * @param breedingProbability The probability (as a decimal) of the animal breeding at any step
     * @param maxLitterSize The most births the animal can have at once
     * @param trophicLevel The animals position in the food chain
     * @param chanceOfDeathInSnow The chance of animal dying due to snow at any time
     */
    public Animal(boolean randomAge, Field field, Location location, int maxAge,
    int breedingAge, double breedingProbability, int maxLitterSize, int trophicLevel, double chanceOfDeathInSnow)
    {
        super(field, location, trophicLevel);
        this.maxAge = maxAge;
        this.breedingAge = breedingAge;
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;
        this.chanceOfDeathInSnow = chanceOfDeathInSnow;

        gender = rand.nextBoolean() ? Gender.MALE : Gender.FEMALE;
        // Animal has a 50% chance of being male or female

        if(randomAge) {
            age = rand.nextInt(maxAge);
            foodLevel = rand.nextInt(getFoodValue() - 4);
        }
        else {
            age = 0;
            foodLevel = getFoodValue() - 4;
        }
    }

    /**
     * Returns the animals gender
     * @return  The gender of the animal
     */
    public Gender getGender()
    {
        return gender;
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newOrganisms A list to receive newly born animals.
     * @param weather
     * @param isDayTime
     */
    public void act(List<Organism> newOrganisms, Weather weather, boolean isDayTime)
    {
        incrementAge();
        incrementHunger();
        if(isAlive() && isDayTime) {
            if(weather == Weather.SNOWY)
            {
                mayDieFromSnow();
            }

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
                setDead();
            }
        }

    }

    /**
     * Sets the animal as dead based off chanceOfDeathInSnow field
     */
    private void mayDieFromSnow()
    {
        if(rand.nextDouble() <= chanceOfDeathInSnow)
        {
            setDead();
        }
    }

    /**
     * Look for preys adjacent to the current location.
     * Only the first live prey is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Organism organism = field.getObjectAt(where);
            if(!(organism == null) && canEat(organism)) {
                if(organism.isAlive()) { 
                    organism.setDead();
                    foodLevel += organism.getFoodValue();
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Increases the animals age and if the animal reaches its maximum age, it dies
     */
    private void incrementAge()
    {
        age++;
        if(age > maxAge) {
            setDead();
        }
    }

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Assigns a random age to the current animal
     */
    public void setRandomAge()
    {
        age = rand.nextInt(maxAge);
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        boolean mateNearby = getField().adjacentLocations(getLocation()).stream().anyMatch(
                location -> (getField().getObjectAt(location) != null    // prevent NullPointerException
                && getField().getObjectAt(location).getClass() == this.getClass()  //If space contains animal of same type
                && ((Animal)getField().getObjectAt(location)).gender != getGender())     // And both are opposite species
        );
        return age >= breedingAge && mateNearby;
    }

    /**
     * Organisms can eat organisms with trophic levels 1 or 2 below it.
     */
    private boolean canEat(Organism organism)
    {
        return getTrophicLevel() - organism.getTrophicLevel() == 1 ||
                getTrophicLevel() - organism.getTrophicLevel() == 2;
    }
}
