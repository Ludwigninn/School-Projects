package Assignment1;

import javax.swing.*;
import java.util.Random;

/**
 * Randomly set the location on a text label .
 * Created by Ludwig Ninn on 15/11/2016.
 */
public class ThreadText extends Thread {

    private Random rand = new Random();
    private boolean threadStopText;
    private GUIDualThreads gui;
    private JLabel label;

    public ThreadText(GUIDualThreads gui,JLabel label) {
        this.gui=gui;
        this.label=label;

    }

    @Override
    public void run() {
        while (threadStopText) {

            try {
                this.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            label.setLocation(rand.nextInt(203), rand.nextInt(220));

        }
    }
/**
 *  Sets the condition in the thread loop.
 * @param threadStopText
 */
    public void setThreadStopText(boolean threadStopText) {
        this.threadStopText = threadStopText;
    }

}


