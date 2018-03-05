package assigment3;


import java.util.LinkedList;
import java.util.Random;

/**
 * Consumer thread represents a thread that acquires product from the buffer/storage and stores them in a linkelist/cargo.
 * The cargo has a limit regarding volym size and weight.
 * Consumer has three states loading,idle and stopped.
 * Idle requirement is met when the sempahore in the buffer is gives permsission to another consumer thread.
 *
 * Created by ludwig ninn on 28/11/2016.
 *
 */
public class Consumer extends Thread {
    private Buffer buffer;
    private Random rand = new Random();
    private boolean cargoFilled = true;
    private LinkedList<Product> truckCargo = new LinkedList<Product>();
    private Controller controller;
    private Product product;
    private double weight;
    private double volume;
    private String name;
    private  boolean checkContLoop =false;

    public Consumer(Controller controller, Buffer buffer) {
        this.controller = controller;
        this.buffer = buffer;
    }

    /**
     * Stores products in a linkedlist while at the same time sending the information to the GUI from the current product.
     */
    @Override
    public void run() {
        while (cargoFilled) {


            try {

                if(product!=null) {
                    checkCargoFilled(product);
                }
                controller.setStatusIdleConsumer("Idle",this);
                product = buffer.get(this);
                controller.setStatusIdleConsumer("Loading",this);
                truckCargo.addLast(buffer.get(this));
                weight +=product.getWeight();
                volume +=product.getVolume();
                name = product.getName();
                controller.setGuIConsumer(weight,volume,name,getCargoSize(),this);
              //  System.out.println(product.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                this.sleep(rand.nextInt(500) + 1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * CheckCargoFilled checks if the cargo have met the criteria for "Cargo filled" that currently is volume > 10 or wieght > 20.
     * @param nextProduct
     */
    public void checkCargoFilled(Product nextProduct) {
        System.out.println("HEJ");
        Product product;
        double volume = 0;
        double weight = 0;


        for (int i = 0; i < truckCargo.size(); i++) {
            product = truckCargo.get(i);
            volume += product.getVolume();
            weight += product.getWeight();
        }

        volume += nextProduct.getVolume();
        weight += nextProduct.getWeight();

        if (volume > 10 || weight > 20 || truckCargo.size()> 25 ) {

            cargoFilled = false;
            controller.setStatusIdleConsumer("Filled",this);
            System.out.println("FALSE");

        }

    }

    /**
     * Returns the size of the cargo in the current thread.
     * @return
     */
    public int getCargoSize(){
       return truckCargo.size();
    }

    /**
     * Sets the threads infinit loop to either true or false.
     * @param cargoFilled
     */
    public void setCargoFilled(boolean cargoFilled) {
        this.cargoFilled = cargoFilled;
    }
    public void contloop(boolean checkContLoop) {
        this.checkContLoop = checkContLoop;
    }
}