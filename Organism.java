import java.util.List;

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
    
    private int trophicLevel;
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param trophicLevel The animals position in the food chain
     */
    public Organism(Field field, Location location, int trophicLevel)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        this.trophicLevel = trophicLevel;
    }
    
    /**
     * Returns the amount of additional days the predator can go without needing food.
     */
    public int getFoodValue()
    {
        return getTrophicLevel() * getTrophicLevel() * 4;
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
     */
    public abstract void act(List<Organism> newOrganisms);

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
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newOrganism A list to return newly born foxes.
     */
    protected void giveBirth(List<Organism> newOrganism)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Organism young = returnOffspring(field, loc);
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
        return field;
    }
}
