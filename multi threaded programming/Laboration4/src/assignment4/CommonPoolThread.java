package assignment4;

import java.util.Random;

/**
 * Created by ludwi on 12/12/2016.
 */
public class CommonPoolThread extends Thread {
    private CommonPoolBuffer commonPoolBuffer;
    private AdventurePoolBuffer adventurePoolBuffer;
    private EntranceWaitingCommonBuffer entranceWaitingCommonBuffer;
    private Random rand = new Random();
    private boolean loopCond=true;
    private Controller controller;

    public CommonPoolThread(Controller controller, CommonPoolBuffer commonPoolbuffer, AdventurePoolBuffer adventurePoolBuffer, EntranceWaitingCommonBuffer entranceWaitingBuffe) {
        this.controller = controller;
        this.adventurePoolBuffer = adventurePoolBuffer;
        this.commonPoolBuffer = commonPoolbuffer;
        this.entranceWaitingCommonBuffer = entranceWaitingBuffe;
    }


    @Override
    public void run() {
        Customer customer;
        while (loopCond) {
            try {

                if(commonPoolBuffer.getSize()<20) {

                    if (rand.nextInt(4) == 1) {
                        customer = (Customer) commonPoolBuffer.getVIP();
                        if(adventurePoolBuffer.getSize()<20) {
                            adventurePoolBuffer.put(customer);
                            controller.setAdventureSizeLabel();
                            controller.setCommenSizeLabel();
                            System.out.println("Costumer moves from CommonPool");
                            controller.setEntranceWaitingAdventureSizeLabel();
                            controller.setEntranceWaitingCommonSizeLabel();
                        }
                    } else {
                        customer = (Customer) entranceWaitingCommonBuffer.get();
                        commonPoolBuffer.put(customer);
                        controller.setCommenSizeLabel();
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
