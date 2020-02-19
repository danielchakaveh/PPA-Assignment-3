import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
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
    /*
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;
    */

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
    Random rand = Randomizer.getRandom();

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
        view.setColor(Rabbit.class, Color.GRAY);
        view.setColor(Fox.class, Color.RED);
        view.setColor(Snake.class, Color.GREEN);
        view.setColor(Tiger.class, Color.ORANGE);
        view.setColor(Mouse.class, Color.BLACK);
        view.setColor(Plant.class, Color.YELLOW);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Assigns a random weather to the simulator weather
     */
    private void setRandomWeather()
    {
        int weatherID = rand.nextInt(Weather.values().length);
        weather = Weather.values()[weatherID];
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

        // Provide space for newborn animals.snake
        List<Organism> newAnimals = new ArrayList<>();
        // Let all rabbits act.
	    if(getCurrentHour() >= 20 || getCurrentHour() <= 6) {
            for(Iterator<Organism> it = organisms.iterator(); it.hasNext(); ) {
                Organism organism = it.next();
                organism.act(newAnimals, weather, );
                if(! organism.isAlive()) {
                    it.remove();
                }
            }
	    }
        // Add the newly born foxes and rabbits to the main lists.
        organisms.addAll(newAnimals);
        view.showStatus(step, field);
    }

	/**
	 * Calculate the current day.
	 */
	public int getCurrentDay()
	{
		int day = step / 72;
		return day;
	}

	/**
	 * Calcualate the current hour.
	 */
	public int getCurrentHour()
	{
		int timeHour = (step / 3) % 24;
		return timeHour;
	}

	/**
	 * Calculate the current minute.
	 */
	public int getCurrentMinute()
	{
		int timeMinute = (step % 3) * 20;
		return timeMinute;
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
        view.showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= Mouse.creationProbability)
                {
                    Location location = new Location(row, col);
                    Mouse mouse = new Mouse(true, field, location);
                    organisms.add(mouse);
                }
                if(rand.nextDouble() <= Rabbit.creationProbability) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    organisms.add(rabbit);
                }
                else if(rand.nextDouble() <= Fox.creationProbability) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    organisms.add(fox);
                }
                else if(rand.nextDouble() <= Snake.creationProbability) {
                    Location location = new Location(row, col);
                    Snake snake = new Snake(true, field, location);
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
