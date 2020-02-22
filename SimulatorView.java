import java.awt.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class SimulatorView extends JFrame
{
    // Colors used for empty locations.
    private static final Color DAY_COLOR = Color.WHITE;
            //Color.getHSBColor(55, 100, 90);
    private static final Color NIGHT_COLOR = Color.DARK_GRAY;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.MAGENTA;
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel timeLabel, population, infoLabel, weatherLabel;
    private FieldView fieldView;
    private JPanel infoPane;
    
    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;

    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width)
    {
        stats = new FieldStats();
        colors = new LinkedHashMap<>();

        setTitle("Jungle Simulator");
        timeLabel = new JLabel(getTimeString(0, 0, 0), JLabel.CENTER);
        infoLabel = new JLabel("  ", JLabel.CENTER);
        weatherLabel = new JLabel("", JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);

        
        setLocation(100, 50);
        
        fieldView = new FieldView(height, width);

        Container contents = getContentPane();
        
        infoPane = new JPanel(new BorderLayout());
            infoPane.add(timeLabel, BorderLayout.WEST);
            infoPane.add(infoLabel, BorderLayout.CENTER);
            infoPane.add(weatherLabel, BorderLayout.EAST);
        contents.add(infoPane, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(population, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
    
    /**
     * Define a color to be used for a given class of animal.
     * @param organismClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class organismClass, Color color)
    {
        colors.put(organismClass, color);
    }
    /**
     * Display a short information label at the top of the window.
     */
    public void setInfoText(String text)
    {
        infoLabel.setText(text);
    }

    /**
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class animalClass)
    {
        Color col = colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     * Show the current status of the field.
     * Show the current time and day of the simulator.
     * @param field The field whose status is to be displayed.
     * @param days  The number of days since the simulator started
     * @param hours The number of hours since the simulator started
     * @param minutes The number of minutes since the simulator started
     */
    public void showStatus(Field field, int days, int hours, int minutes, Weather weather)
    {
        if(!isVisible()) {
            setVisible(true);
        }

        timeLabel.setText(getTimeString(days, hours, minutes));
        weatherLabel.setText(weather.toString());
        stats.reset();
        
        fieldView.preparePaint();

        // Sorts out day/night weather
        Color currentColour = Simulator.isDayTime(hours) ? DAY_COLOR : NIGHT_COLOR;
        population.setBackground(currentColour);
        infoPane.setBackground(currentColour);
        getContentPane().setBackground(currentColour);

		if(currentColour == NIGHT_COLOR) {
		population.setForeground(Color.WHITE);
		infoPane.setForeground(Color.WHITE);
		}
		else {
		population.setForeground(Color.BLACK);
		infoPane.setForeground(Color.BLACK);
		}

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, currentColour);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

    /**
     * Creates a string containing the time in days, minutes and hours
     * @param days The number of the day
     * @param hours The number of the hour
     * @param minutes The number of the minute
     * @return The newly created time string
     */
    private String getTimeString(int days, int hours, int minutes)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Days: ");
        builder.append(days);
        builder.append(" Hours: ");
        builder.append(hours);
        builder.append(" Minutes: ");
        builder.append(minutes);
        return builder.toString();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
    
    /**
     * Provide a graphical view of a rectangular field. This is 
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this 
     * for your project if you like.
     */
    private class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}
