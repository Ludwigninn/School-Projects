package Assig5;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Controller initalizes all threads and queus/buffers.
 * The threadpool consist of 4 workers instead of 4 diffrent threadpools. That represents a entrence each.
 */
public class Controller {

    private int poolSize = 4;  // Amount workers in thread
    private ParkingHouseBuffer buffer = new ParkingHouseBuffer();
    private GUI gui;


    public Controller(GUI gui) {
        this.gui = gui;
    }

    /**
     * Initalizes the Exit thread and threadpool/entrence.
     */
    public void intit() {
        //init entrence
        ThreadPoolExecutor entrencePool = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);
        Entrence entrence = new Entrence(buffer, entrencePool, gui);

        // init exit
        Exit exit = new Exit(buffer, gui);

        entrence.start();
        exit.start();
    }

}