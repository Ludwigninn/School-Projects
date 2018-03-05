import java.util.Random;

/**
 * WriterThread writes to the buffer randomly timed. Consist off two modes synced and asynced.
 * Author Ludwig Ninn
 */
public class WriterThread extends Thread {
	private Buffer buffer;
	private boolean threadSynced;
	private String text;
	private Controller controller;
	private Random rand = new Random();

	public WriterThread(String text, Buffer buffer, Controller controller) {
		this.text = text;
		this.buffer = buffer;
		this.controller = controller;

	}

	/**
	 * Timed intervall between 500-1000 ms. Sends a char to the buffer.
	 */
	public void run() {
		char c;
		if (threadSynced == true) {
			for (int i = 0; i < text.length(); ++i) {
				try {
					this.sleep(rand.nextInt(1000) + 500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c = text.charAt(i);
				System.out.println(c);
				buffer.put(c);
				controller.appendToTextAreaWriter(c);

			}
		}else{
			for (int i = 0; i < text.length(); ++i) {
				try {
					this.sleep(rand.nextInt(1000) + 1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c = text.charAt(i);
				System.out.println(c);
				buffer.putAsync(c);
				controller.appendToTextAreaWriter(c);

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