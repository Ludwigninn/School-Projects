package Assig5;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Exit is a thread that removes cars in set timer.
 */
public class Exit extends Thread {

    private ParkingHouseBuffer parkingHouse;
    private GUI gui;
    private int count;
    private Random rand = new Random();


    public Exit(ParkingHouseBuffer parkingHouse, GUI gui) {
        this.parkingHouse = parkingHouse;
        this.gui = gui;
        System.out.println("Exit Init:");
    }

    public void run() {

        while (true) {

            try {
                count++;
                Thread.sleep(rand.nextInt(2400)+1000);

                //Removes a car from buffer/parkinghouse
                parkingHouse.get();

                //GUI ticks
                System.out.println("Bil ut" + " "+"Antal  i parkingshuset " + " "+parkingHouse.size());
                gui.setTestlblOut(""+count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
