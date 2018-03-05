package Assig5;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Entrance represents 4 diffrent workers or entrence. That gets task from this class.
 */
public class Entrence extends Thread {
    private ThreadPoolExecutor entrence;
    private GUI gui;
    private int count;
    private ParkingHouseBuffer parkingHouse;
    private Random rand = new Random();

    public Entrence(ParkingHouseBuffer parkingHouse, ThreadPoolExecutor entrence, GUI gui) {
        this.parkingHouse = parkingHouse;
        this.entrence = entrence;
        this.gui = gui;
    }

    /**
     * Run method
     */
    @Override
    public void run() {
        System.out.println("Entrance Init RUN:");
        while (true) {


            try {

                //Handling tasks
                count++;
                CarEnterTask task = new CarEnterTask(parkingHouse);
                entrence.execute(task);


                //GUI ticks
                System.out.println("Bil in" + " " + "Antal  i parkingshuset " + " " + parkingHouse.size());
                gui.setTestlblin("" + count);
                gui.setTestTotal("" + parkingHouse.size());
                Thread.sleep(rand.nextInt(1000) + 1000);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}



