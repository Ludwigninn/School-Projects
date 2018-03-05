package assigment3;


/**
 * Product represents a item in the buffer which the producer inserts in the buffer and the consumer acquires to a certain limit.
 * Created by ludwig ninn on 28/11/2016.
 */
public class Product {

    private String  name;
    private double weight;
    private double volume;

    public Product(String name, double weight, double volume){
        this.name = name;
        this.weight= weight;
        this.volume=volume;
    }

    /**
     * Returns name of the product.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the weight of the product.
     * @return
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the product.
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Returns the volume of the product.
     * @return
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume of the product.
     * @param volume
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

}
