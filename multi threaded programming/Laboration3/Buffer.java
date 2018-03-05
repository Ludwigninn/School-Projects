package assigment3;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * The buffer represents a storage with items/products. Semphores controlls the flow of the acces to the buffer.
 * Created by ludwig ninn on 28/11/2016.
 */
public class Buffer {

	static final Semaphore semaphoreProd = new Semaphore(25);
	static final Semaphore semaphoreCons = new Semaphore(0);
	static final Semaphore mutex = new Semaphore(1);
    private LinkedList<Product> buffer = new LinkedList<Product>();


	/**
	 * Stores a item in the buffer. Acquires a semphore producer and release a consumer semphore.
	 * A mutex which is shred between the put and get method.
	 * @param element
	 */
	public  void put(Product element) {

		try {
            semaphoreProd.acquire();
			mutex.acquire();


			//CR
			buffer.addLast(element);
            System.out.println("PUT: "+ element.getName() + "\n" +   buffer.size());





            mutex.release();
            semaphoreCons.release();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Acquires a item from the buffer. Acquiers a consumer semaphore and releases a producer semaphore.
	 * Contains mutex which is shred between the put and get method.
	 * @return
	 * @throws InterruptedException
	 */
	public  Product get(Consumer consumer) throws InterruptedException {
        Product product;

        semaphoreCons.acquire();
		mutex.acquire();



		//CR

        product = buffer.removeFirst();
		consumer.checkCargoFilled(product);
		System.out.println("GET: "+ product.getName());


        mutex.release();
        semaphoreProd.release();
        return product;
	}

	/**
	 * Returns the buffersize.
	 * @return
	 */
	public int getBufferSize(){
      return  buffer.size();
    }

}
