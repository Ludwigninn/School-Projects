package Assignment1;

import javax.swing.*;
import java.util.Random;

/**
 * Randomly places the picture in different locations.
 * Created by Ludwig Ninn on 15/11/2016.
 */
public class ThreadRotate extends Thread {

    private boolean threadStopRotate;
    private Random rand = new Random();
    private GUIDualThreads gui;
    private  JLabel labelImage;

    public ThreadRotate(GUIDualThreads gui, JLabel labelImage) {
        this.gui=gui;
        this.labelImage=labelImage;
    }

    @Override
    public void run() {
        while (threadStopRotate) {
            try {
                this.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            labelImage.setLocation(rand.nextInt(203), rand.nextInt(220));
        }

    }
/**
 * Sets the condition in the thread loop.
 * @param threadStopRotate
 */
    public void setThreadStopRotate(boolean threadStopRotate) {
        this.threadStopRotate = threadStopRotate;
    }
}
