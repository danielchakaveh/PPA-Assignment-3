/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Jackal extends Animal
{
    public static double creationProbability = 0.05;
    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Jackal(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location, 200, 20, 0.4, 5, 3, 0.05, 0.0001);
    }
        @Override
        protected Animal returnOffspring(Field field, Location location) {
            return new Jackal(false, field, location);
        }
}


