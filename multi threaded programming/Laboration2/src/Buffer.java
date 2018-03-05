import java.util.LinkedList;

/**
 * A buffer that got two version of put and get, synced and asynced. Uses a linkedlist as datastructer for the char.
 * @param <T>
 */
public class Buffer<T> {

    private LinkedList<T> buffer = new LinkedList<T>();

	/**
	 * Synced put method. Notifes after a add.
	 * @param element
	 */
	public synchronized void put(T element) {
		buffer.addLast(element);
		notifyAll();
	}

	/**
	 * Synced get method. Waits while the buffer is empty.
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized T get() throws InterruptedException {
		while(buffer.isEmpty()) {
			wait();
		}
		return buffer.removeFirst();
	}

	/**
	 * Asynced put method. Adds last.
	 * @param obj
	 */
	public void putAsync(T obj) {
		buffer.addLast(obj);
		
	}

	/**
	 * Asynced get method. Does a busy wait while buffer is empty. Can reslut in critcal errors.
	 * @return
	 * @throws InterruptedException
	 */
	public T getAsync() throws InterruptedException {

		while(buffer.isEmpty()) {
		
		}
		return buffer.getLast();
	}

}
