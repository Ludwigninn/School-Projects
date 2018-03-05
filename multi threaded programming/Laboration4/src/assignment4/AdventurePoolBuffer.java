package assignment4;

import java.util.LinkedList;

/**
 * A buffer that got two version of put and get, synced and asynced. Uses a linkedlist as datastructer for the char.
 *
 * @param <T>
 */
public class AdventurePoolBuffer<T> {

    private LinkedList<T> buffer = new LinkedList<T>();

    /**
     * Synced put method. Notifes after a add.
     *
     * @param element
     */
    public synchronized void put(T element) {
        buffer.addLast(element);
        notify();
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
