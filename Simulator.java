import java.util.*;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    public static void main(String[] args)
    {
        Simulator simulator = new Simulator();

        simulator.runLongSimulation();
    }

    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability of weather changing at any step
    private static final double weatherChangeProbability = 0.1;
    // The probability of weather changing at any step

    // List of animals in the field.
    private List<Organism> organisms;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // The current weather in the simulation
    private Weather weather;
    // Randomizer to allow for randomness in simulation
    private Random rand = Randomizer.getRandom();

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        organisms = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Jackal.class, Color.getHSBColor(39, 55, 48));
        view.setColor(Bear.class, Color.GRAY);
        view.setColor(Dear.class, Color.getHSBColor(0, 100, 35));
        view.setColor(Tiger.class, Color.getHSBColor(25, 100, 50));
        view.setColor(Beaver.class, Color.getHSBColor(23, 100, 15));
        view.setColor(Plant.class, Color.GREEN);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Assigns a random weather to the simulator weather
     */
    private void setRandomWeather()
    {
        Weather[] weathers = Weather.values();
        int weatherID = rand.nextInt(Weather.values().length - 1);
        weather = (Weather)Arrays.stream(weathers).filter(w -> w != weather).toArray()[weatherID];
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation(){
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps){
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(60);   // uncomment this to run more slowly
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
        if(rand.nextDouble() <= weatherChangeProbability)
        {
            setRandomWeather();
        }

        // Provide space for newborn animals.snake
        List<Organism> newAnimals = new ArrayList<>();
        // Let all rabbits act.

        for(Iterator<Organism> it = organisms.iterator(); it.hasNext(); ) {
            Organism organism = it.next();
            if(!organism.isAlive()) {
                it.remove();
            }
            organism.act(newAnimals, weather, isDayTime(getCurrentHour()));

        }

        // Add the newly born foxes and rabbits to the main lists.
        organisms.addAll(newAnimals);
        view.showStatus(field, getCurrentDay(), getCurrentHour(), getCurrentMinute());
    }

    /**
     * Calculate the current day.
     */
    public int getCurrentDay()
    {
        return step / 72;
    }

    /**
     * Calcualate the current hour.
     */
    public int getCurrentHour()
    {
        return (step / 3) % 24;
    }

    /**
     * Calculate the current minute.
     */
    public int getCurrentMinute()
    {
        return (step % 3) * 20;
    }

    /**
     * returns true if the hour is in the day time
     * @param hour The hour to check
     */
    public static boolean isDayTime(int hour)
    {
        return (hour >= 6 && hour<= 20);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        organisms.clear();
        setRandomWeather();
        populate();

        // Show the starting state in the view.
        view.showStatus(field, getCurrentDay(), getCurrentHour(), getCurrentMinute());
    }

    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= Beaver.creationProbability)
                {
                    Location location = new Location(row, col);
                    Beaver beaver = new Beaver(true, field, location);
                    organisms.add(beaver);
                }
                if(rand.nextDouble() <= Jackal.creationProbability) {
                    Location location = new Location(row, col);
                    Jackal rabbit = new Jackal(true, field, location);
                    organisms.add(rabbit);
                }
                else if(rand.nextDouble() <= Bear.creationProbability) {
                    Location location = new Location(row, col);
                    Bear fox = new Bear(true, field, location);
                    organisms.add(fox);
                }
                else if(rand.nextDouble() <= Dear.creationProbability) {
                    Location location = new Location(row, col);
                    Dear snake = new Dear(true, field, location);
                    organisms.add(snake);
                }
                else if(rand.nextDouble() <= Tiger.creationProbability) {
                    Location location = new Location(row, col);
                    Tiger tiger = new Tiger(true, field, location);
                    organisms.add(tiger);
                }
                else if(rand.nextDouble() <= Plant.creationProbability) {
                    Location location = new Location(row, col);
                    Plant plant = new Plant(field, location);
                    organisms.add(plant);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
