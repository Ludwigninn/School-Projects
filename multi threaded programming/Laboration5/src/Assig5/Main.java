package Assig5;

/**
 * Main metho that starts the program.
 */
public class Main 
{
  public static void main(String[] args)
    {
        GUI gui = new GUI();
        gui.start();
        Controller controller = new Controller(gui);
	    controller.intit();
	
    }
}

