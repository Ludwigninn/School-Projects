package assignment4;

import java.util.Random;

/**
 * Created by ludwi on 12/12/2016.
 */
public class ReceptionThread extends Thread {

    private boolean loopCond=true;
    private Controller controller;
    private EntranceWaitingAdventureBuffer entranceWaitingAdventureBuffer;
    private EntranceWaitingCommonBuffer entranceWaitingCommonBuffer;
    private Random rand = new Random();

    public ReceptionThread(Controller controller, EntranceWaitingAdventureBuffer entranceWaitingAdventureBuffer, EntranceWaitingCommonBuffer entranceWaitingCommonBuffer) {
        this.controller = controller;
        this.entranceWaitingAdventureBuffer = entranceWaitingAdventureBuffer;
        this.entranceWaitingCommonBuffer = entranceWaitingCommonBuffer;
    }


    @Override
    public void run() {
        Customer customer;
        while (loopCond) {
            customer = new Customer();
            if (rand.nextBoolean()) {
                if (rand.nextInt(4) == 1) {
                    customer.setVip(true);
                    entranceWaitingAdventureBuffer.put(customer);
                    controller.setEntranceWaitingAdventureSizeLabel();
                } else {
                    customer.setVip(false);
                    entranceWaitingAdventureBuffer.put(customer);
                    controller.setEntranceWaitingAdventureSizeLabel();
                }
            }else{
                    if (rand.nextInt(4) == 1) {
                        customer.setVip(true);
                        entranceWaitingCommonBuffer.put(customer);
                        controller.setEntranceWaitingCommonSizeLabel();
                    } else {
                        customer.setVip(false);
                        entranceWaitingCommonBuffer.put(customer);
                        controller.setEntranceWaitingCommonSizeLabel();
                    }
                }
            try {
                this.sleep(rand.nextInt(500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                }




        }
    public void setLoopCond(boolean loopCond) {
        this.loopCond = loopCond;
    }

    }

