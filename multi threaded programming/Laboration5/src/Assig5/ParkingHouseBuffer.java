package Assig5;

import java.util.LinkedList;

/**
 * Buffer that represents tha parkinghouse
 * @param <T>
 */
public  class ParkingHouseBuffer<T> {
	private LinkedList<T> buffer = new LinkedList<T>();
	
	public synchronized void put(T obj) {
		buffer.addLast(obj);
		notifyAll();
	}
	
	public synchronized T get() throws InterruptedException {
		while(buffer.isEmpty()) {
			wait();
		}
		return buffer.removeFirst();
	}
		
	public synchronized void clear() {
		buffer.clear();
	}
	
	public int size() {
		return buffer.size();
	}
}