package Assig5;


/**
 * CarEnterTask is a task, that puts cars inside the parkinghouse.
 */
public class CarEnterTask implements Runnable {

    private ParkingHouseBuffer parkingHouse;


    public CarEnterTask(ParkingHouseBuffer parkingHouse) {
        this.parkingHouse = parkingHouse;
    }

    /**
     * Run method
     */
    public void run() {

        //Keeps puting cars untill 2000 mark
        if (parkingHouse.size() < 2000) {
            Car car = new Car();
            parkingHouse.put(car);
        }


    }
}