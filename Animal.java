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
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    private int breedingAge;
    // Minimum maturity age to reproduce.
    private double breedingProbability;
    // A shared random number generator to control breeding.
    private int maxLitterSize;
    // Maximum number of offspring in a single pregnancy.

    private static final Random rand = Randomizer.getRandom();

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param trophicLevel The animals position in the food chain
     */
    public Animal(boolean randomAge, Field field, Location location, int maxAge,
    int breedingAge, double breedingProbability, int maxLitterSize, int trophicLevel)
    {
        super(field, location, trophicLevel);
        this.maxAge = maxAge;
        this.breedingAge = breedingAge;
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;

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
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    public void act(List<Animal> newAnimals)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newAnimals);
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
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to return newly born foxes.
     */
    private void giveBirth(List<Animal> newAnimals)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = returnOffspring(field, loc);
            newAnimals.add(young);
        }
    }

    /**
     * Returns an offspring of the animal
     * @param field The grid for the animal to be placed on
     * @param location  The position of the animal on the grid
     * @return New instance of specific animal
     */
    protected abstract Animal returnOffspring(Field field, Location location);

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
    private int breed()
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
        return age >= breedingAge;
    }

    /**
     * 
     */
    private boolean canEat(Organism organism)
    {
        return (getTrophicLevel() - organism.getTrophicLevel()) == 1 ;
    }
}
