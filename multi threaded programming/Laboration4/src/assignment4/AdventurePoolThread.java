package assignment4;

import java.util.Random;

/**
 * Created by ludwi on 12/12/2016.
 */
public class AdventurePoolThread  extends Thread{
    private CommonPoolBuffer commonPoolBuffer;
    private AdventurePoolBuffer adventurePoolBuffer;
    private EntranceWaitingAdventureBuffer entranceWaitingAdventureBuffer;
    private Random rand = new Random();
    private boolean loopCond=true;
    private Controller controller;

    public AdventurePoolThread(Controller controller, CommonPoolBuffer commonPoolbuffer, AdventurePoolBuffer adventurePoolBuffer, EntranceWaitingAdventureBuffer entranceWaitingBuffe) {
        this.controller=controller;
        this.commonPoolBuffer = commonPoolbuffer;
        this.adventurePoolBuffer = adventurePoolBuffer;
        this.entranceWaitingAdventureBuffer = entranceWaitingBuffe;
    }


    @Override
    public void run() {
        Customer customer;
        while (loopCond) {
            try {
                if(adventurePoolBuffer.getSize()<20) {
                    if (rand.nextInt(4) == 1) {
                        if(commonPoolBuffer.getSize()<20) {
                            customer = (Customer) adventurePoolBuffer.get();
                            commonPoolBuffer.put(customer);
                            controller.setCommenSizeLabel();
                            controller.setAdventureSizeLabel();
                            controller.setEntranceWaitingAdventureSizeLabel();
                            controller.setEntranceWaitingCommonSizeLabel();
                            System.out.println("Costumer moves from AdventuresPool");
                        }
                        } else {
                        customer = (Customer) entranceWaitingAdventureBuffer.get();
                        adventurePoolBuffer.put(customer);
                        controller.setAdventureSizeLabel();
                        controller.setEntranceWaitingAdventureSizeLabel();
                        controller.setEntranceWaitingCommonSizeLabel();
                    }
                }
                this.sleep(rand.nextInt(500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
