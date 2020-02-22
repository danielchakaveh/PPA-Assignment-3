import java.util.*;
public class Disease
{
    // The chance of an animal dying from the disease at any step
    private double mortalityRate;
    // The chance of a disease spreading to another being at any step
    private double contagiousness;
    // The name of the disease
    private String name;

    /**
     * Creates a new disease
     * @param diseaseName  The name of the disease
     * @param contagiousness The chance of the disease spreading to another being upon contact with an infected
     * @param mortalityRate The chance of an infected dying at any step
     */
    public Disease(double contagiousness, double mortalityRate, String diseaseName)
    {
        this.contagiousness = contagiousness;
        name = diseaseName;
        this.mortalityRate = mortalityRate;
    }

    /**
     * slightly changes the infected organism
     * @param infected The organism which has the disease
     */
    public void affect(Organism infected)
    {
        if(randomizer.nextDouble() <= mortalityRate)
        {
            //System.out.println(infected.getClass().getName() + " killed by disease");
            infected.setDead();
        }
    }

    /**
     * Returns the name of the disease
     * @return The name of the disease
     */
    public String getName()
    {
        return name;
    }

    /**
     * Calculates randomly whether or not the disease is to be spread based off contagiousness
     * @return True if the disease would randomly spread to another individual
     */
    public boolean wouldSpread()
    {
        return (randomizer.nextDouble() <= contagiousness);
    }

    private static Disease[] allDiseases = {
            new Disease(0.01, 0.7, "Swine Flu"),
            new Disease(0.1, 0.04, "Corona"),
            new Disease(0.3, 0.006, "Common cold")
    };
    private static Random randomizer = Randomizer.getRandom();

    /**
     * Returns a random disease
     * @return a random disease
     */
    public static Disease getRandomDisease()
    {
        int numberOfDiseases = allDiseases.length;
        if(numberOfDiseases == 0)
        {
            return null;
        }
        return allDiseases[randomizer.nextInt(numberOfDiseases)];
    }
}
