import java.util.Random;

/**
 *
 * ReaderThread reads from the buffer randomly timed. Consist off two modes synced and asynced.
 * Author Ludwig Ninn
 */
public class ReaderThread extends Thread {
	private Buffer buffer;
	private boolean threadSynced;
	private int size;
	private Controller controller;
	private Random rand = new Random();

	public ReaderThread(int size, Buffer buffer, Controller controller) {
		this.size = size;
		this.buffer = buffer;
		this.controller = controller;
	}

	/**
	 * Timed intervall between 500-1000 ms. Recives a char from the buffer.
	 */
	public void run() {

		char c;

		if (threadSynced == true) {
			for (int i = 0; i < size; ++i) {
				try {
					this.sleep(rand.nextInt(1000) + 500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					c = (char) buffer.get();
					System.out.println(c);
					controller.appendToTextAreaReader(c);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} else {
			for (int i = 0; i < size; ++i) {
				try {
					this.sleep(rand.nextInt(1000) + 1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					c = (char) buffer.getAsync();
					System.out.println(c);
					controller.appendToTextAreaReader(c);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	/**
	 * Method that sets if the thread should be synced or not.
	 * @param threadSynced
	 */
	public void synced(boolean threadSynced) {
		this.threadSynced = threadSynced;
	}
}
