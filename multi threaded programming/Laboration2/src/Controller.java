/**
 * Controller between GUI and Threads.
 * Author Ludwig Ninn
 */
public class Controller {
	private boolean synced = false;
	private String text;
	private GUIMutex gui;
	private Buffer buffer;

	public Controller() {

		initializeGUI();

	}

	/**
	 * Initalizes Graphical user interface.
	 */
	public void initializeGUI() {
		gui = new GUIMutex(this);
		gui.Start();
	}

	/**
	 * Initaializes Threads.
	 */
	public void initalizeThreads() {
		initalizeBuffer();
		initializeWriterThread(text);
		initializeReaderThread(text.length());
	}

	/**
	 * Initaializes buffer.
	 */
	public void initalizeBuffer() {
		 buffer = new Buffer();
	}

	/**
	 * Initaializes WriterThread.
	 * @param text
	 */
	public void initializeWriterThread(String text) {
		WriterThread reader = new WriterThread(text, buffer,this);
		reader.synced(synced);
		reader.start();
	}

	/**
	 * Iniataializes ReaderThread.
	 * @param size
	 */
	public void initializeReaderThread(int size) {
		ReaderThread writer = new ReaderThread(size, buffer,this);
		writer.synced(synced);
		writer.start();
	}

	/**
	 * Sets the flag for synced or asynced between GUI and Threads.
	 * @param modeType
	 */
	public void setMode(boolean modeType) {
		this.synced = modeType;
	}

	/**
	 * Sets the char from GUI and to Threads.
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Sets the char from WriterThread and to GUI.
	 * @param text
	 */
	public void appendToTextAreaWriter(char text) {

		gui.setListW(text);
	}

	/**
	 * Sets the char from ReaderThread and to GUI.
	 * @param text
	 */
	public void appendToTextAreaReader(char text) {
		gui.setListR(text);
	}
	
}
