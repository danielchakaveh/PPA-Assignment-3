import java.util.*;

/**
 * A class representing all living things
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public abstract class Organism
{
    // Whether the organism is alive or not.
    private boolean alive;
    // The organism's field.
    private Field field;
    // The organism's position in the field.
    private Location location;
	//
    private int trophicLevel;

    /**
     * Create a new organism at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param trophicLevel The organisms position in the food chain
     */
    public Organism(Field field, Location location, int trophicLevel)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        this.trophicLevel = trophicLevel;
		diseased = false;
        
        
        if(field == null || location == null)
        {
            System.out.println("Invalid organism");
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
     * Returns the organisms position in the food heirarchy.
     * Organisms can eat other organisms that are 1 position below them.
     */
    final public int getTrophicLevel()
    {
        return trophicLevel;
    }

    /**
     * Check whether the organism is alive or not.
     * @return true if the organism is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Make this organism act - that is: make it do
     * whatever it wants/needs to do.
     * @param newOrganisms A list to receive newly born organisms.
     * @param weather The weather at the time of acting
     * @param isDayTime True if the current time is day time
     */
    public abstract void act(List<Organism> newOrganisms, Weather weather, boolean isDayTime);

    /**
     * Indicate that the organism is no longer alive.
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
     * Return the organism's location.
     * @return The organism's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the organism at the new location in the given field.
     * @param newLocation The organism's new location.
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
     * Returns an offspring of the organism
     * @param field The grid for the organism to be placed on
     * @param location  The position of the organism on the grid
     * @return New instance of specific organism
     */
    protected abstract Organism returnOffspring(Field field, Location location);

    /**
     * Return the organism's field.
     * @return The organism's field.
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
