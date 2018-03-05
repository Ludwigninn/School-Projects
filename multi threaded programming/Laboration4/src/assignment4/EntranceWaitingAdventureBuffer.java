package assignment4;

import java.util.LinkedList;

/**
 * Created by ludwi on 13/12/2016.
 */
public class EntranceWaitingAdventureBuffer<T> {
    private LinkedList<T> buffer = new LinkedList<T>();

    /**
     * Synced put method. Notifes after a add.
     *
     * @param element
     */
    public synchronized void put(T element) {
        buffer.addLast(element);
        notifyAll();
    }

    /**
     * Synced get method. Waits while the buffer is empty.
     *
     * @return
     * @throws InterruptedException
     */
    public synchronized T get() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        return buffer.removeFirst();
    }

    public int getSize() {
        return buffer.size();
    }
}
