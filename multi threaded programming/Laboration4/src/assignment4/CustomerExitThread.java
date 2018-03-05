package assignment4;

import java.util.Random;

/**
 * Created by ludwi on 12/12/2016.
 */
public class CustomerExitThread extends Thread {
    private boolean loopCond = true;
    private CommonPoolBuffer commonPoolBuffer;
    private AdventurePoolBuffer adventurePoolBuffer;
    private Controller controller;
    private Random rand = new Random();
    private int countCommon;
    private int countAdventure;

    public CustomerExitThread(Controller controller, CommonPoolBuffer commonPoolbuffer, AdventurePoolBuffer adventurePoolBuffer) {
        this.controller = controller;
        this.commonPoolBuffer = commonPoolbuffer;
        this.adventurePoolBuffer = adventurePoolBuffer;
    }

    @Override
    public void run() {
        Customer customer;
        while (true) {

            try {

                if (rand.nextBoolean()) {


                    if (commonPoolBuffer.getSize() > 0) {
                        customer = (Customer) commonPoolBuffer.get();
                        countCommon++;
                        controller.setExitCommonLabel();
                        controller.setCommenSizeLabel();
                        System.out.println(" Number of customers in Commonpool: " +commonPoolBuffer.getSize());
                        System.out.println("Customer left from Commonpool");
                    }
                } else {
                    if (adventurePoolBuffer.getSize() > 0) {
                        customer = (Customer) adventurePoolBuffer.get();
                        countAdventure++;
                        controller.setExitAdventureLabel();
                        controller.setAdventureSizeLabel();
                        System.out.println(" Number of customers in AdventurePool: " +adventurePoolBuffer.getSize());
                        System.out.println("Customer left from Adventurepool");
                    }
                }

                this.sleep(rand.nextInt(2500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    public int getCountCommon() {
        return countCommon;
    }

    public int getCountAdventure() {
        return countAdventure;
    }

}
