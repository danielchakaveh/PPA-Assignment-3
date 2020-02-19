import java.util.List;

/**
 * Class Mouse - An animal which is low down the food chain
 *
 * @author Obed Ngigi, Daniel Chakaveh-Roberts
 * @version 1.0
 */
public class Mouse extends Animal
{
    public static double creationProbability = 0.2;
    /**
     * Create a Rabbit. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the mouse will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Mouse(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location, 140, 1, 0.55, 14, 2);
    }

    @Override
    protected Animal returnOffspring(Field field, Location location) {
        return new Mouse(false, field, location);
    }
}

