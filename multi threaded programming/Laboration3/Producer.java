package assigment3;


import java.util.Random;

/**
 * Producer thread represents a thread that stores product in the buffer.
 * Producer has three states producing,idle and stopped.
 * Idle requirement is met when the sempahore in the buffer is 25.
 * Created by ludwig ninn on 28/11/2016.
 *
 */
public class Producer extends Thread {
    private Buffer buffer;
    private Random rand = new Random();
    private Product product;
    private boolean StorageFilled= true;
    private Controller controller;

    public Producer(Controller controller, Buffer buffer) {
        this.controller=controller;
        this.buffer = buffer;
    }

    /**
     * Randomnize a product from the controller class to insert into the buffer. While at the same time
     * updates the status of the producer to the GUI.
     */
    @Override
    public void run() {
        while (StorageFilled) {



            product = controller.getRandProduct();
           System.out.println("Producer:"+product.getName());
            controller.setStatusIdleProducer("idle",this);
            buffer.put(product);
            controller.setStatusIdleProducer("Producing",this);
            controller.setGuI(buffer.getBufferSize());

            try {
                this.sleep(rand.nextInt(500) + 1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the threads infinit loop to either true or false.
     * @param storageFilled
     */
    public void setStorageFilled(boolean storageFilled) {
        StorageFilled = storageFilled;
    }

}