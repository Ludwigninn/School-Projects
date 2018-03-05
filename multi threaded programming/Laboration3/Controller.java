package assigment3;


import java.util.Random;

/**
 * Controlls communcation between the GUI and logic.
 * Created by ludwig ninn on 28/11/2016.
 */
public class Controller {
    private Random rand = new Random();
    private Product[] productArray = new Product[21];
    private Buffer buffer;
    private GUISemaphore gui;
    private Consumer cityGross;
    private  Consumer ica;
    private  Consumer coop;
    private  Producer  axFood;
    private  Producer  arla;
    private  Producer  scan;
    private int volume;
    private int weight;

    public Controller() {

        initializeGUI();
        initProductArray();
        initalizeBuffer();

    }

    /**
     * Iniitialize the graphical interface.
     */
    public void initializeGUI() {
        gui = new GUISemaphore(this);
        gui.Start();
    }


    /**
     *   Initalize the buffer.
     */
    public void initalizeBuffer() {
        buffer = new Buffer();
    }

    /**
     * Initalizes prouctarray with food items.
     */
    public void initProductArray() {
        productArray[0] = new Product("Milk", 1.1, 0.5);
        productArray[1] = new Product("Cream", 0.6, 0.1);
        productArray[2] = new Product("Yoghurt", 1.1, 0.5);
        productArray[3] = new Product("Butter", 2.24, 0.66);
        productArray[4] = new Product("Flower", 2.1, 1.5);

        productArray[5] = new Product("Suger", 4.1, 1.5);
        productArray[6] = new Product("Salt", 1.51, 0.25);
        productArray[7] = new Product("Almonds", 0.11, 0.15);
        productArray[8] = new Product("Bread", 1.61, 0.75);
        productArray[9] = new Product("Donuts", 1.41, 0.5);

        productArray[10] = new Product("Jam", 1.1, 0.5);
        productArray[11] = new Product("Ham", 4.1, 2.5);
        productArray[12] = new Product("Chicken", 6.8, 2.5);
        productArray[13] = new Product("Salat", 0.91, 0.55);
        productArray[14] = new Product("Orange", 2.41, 0.25);

        productArray[15] = new Product("Apple", 2.41, 0.4);
        productArray[16] = new Product("Pear", 1.2, 0.77);
        productArray[17] = new Product("Soda", 2.91, 2.5);
        productArray[18] = new Product("Bear", 2.71, 1.5);
        productArray[19] = new Product("Hotdogs", 2.1, 1.35);
        productArray[20] = new Product("Bagel", 2.4, 1.8);

    }

    /**
     * Return a random product from the araray.
     * @return
     */
    public Product getRandProduct() {

        return productArray[rand.nextInt(21)];
    }

    /**
     * Starts the threads from the GUI.
     * @param thread
     */
    public void startThreads(int thread){

        switch (thread) {
            case 1:
                ica = new Consumer(this,buffer);
                ica.start();
                break;
            case 2:
                coop = new Consumer(this,buffer);
                coop.start();
                break;
            case 3:
                cityGross = new Consumer(this,buffer);
                cityGross.start();
                break;
            case 4:
                arla = new Producer(this,buffer);
                arla.start();
                break;
            case 5:
                scan = new Producer(this,buffer);
                scan.start();
                break;
            case 6:
                axFood = new Producer(this,buffer);
                axFood.start();
                break;
            case 7:
                ica.setCargoFilled(false);
                break;
            case 8:
                coop.setCargoFilled(false);
                break;
            case 9:
                cityGross.setCargoFilled(false);
                break;
            case 10:
                arla.setStorageFilled(false);
                break;
            case 11:
                scan.setStorageFilled(false);
                break;
            case 12:
                axFood.setStorageFilled(false);
                break;
        }


    }


    /**
     * Set the GUI procent bar.
     * @param procent
     */
    public void setGuI(int procent){
            gui.setGUI(procent);
    }

    /**
     * Sets the GUI for the consumer.
     * @param weight
     * @param volume
     * @param name
     * @param carogosize
     * @param consumer
     */
    public void setGuIConsumer(double weight, double volume,String name,int carogosize, Consumer consumer){

        if(consumer==cityGross){
            gui.setGUIConsumer(weight,volume,name,carogosize,1);

        }else if(consumer==ica){
            gui.setGUIConsumer(weight,volume,name,carogosize,2);
        }else if(consumer==coop){
            gui.setGUIConsumer(weight,volume,name,carogosize,3);
        }

    }

    /**
     * Set idle status for consumer.
     * @param status
     * @param consumer
     */
    public void setStatusIdleConsumer(String status,Consumer consumer){
        if(consumer==cityGross){
            gui.setCGStatus(status);
        }else if(consumer==ica){
            gui.setIcaStatus(status);
        }else if(consumer==coop){
           gui.setCoopStatus(status);
        }
    }

    /**
     * Set idle status for proucers.
     * @param status
     * @param producer
     */
    public void setStatusIdleProducer(String status,Producer producer){
        if(producer==scan){
            gui.setLblStatusScan(status);
        }else if(producer==axFood){
            gui.setLblStatusAxFood(status);
        }else if(producer==arla){
            gui.setLblStatusArla(status);
        }
    }


}
