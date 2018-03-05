
package Assignment1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

/**
 * The GUI for assignment 1, DualThreads
 */
public class GUIDualThreads {
	/**
	 * These are the components you need to handle. You have to add listeners
	 * and/or code
	 */

	private JFrame frame; // The Main window
	private JButton btnDisplay; // Start thread moving display
	private JButton btnDStop; // Stop moving display thread
	private JButton btnTriangle;// Start moving graphics thread
	private JButton btnTStop; // Stop moving graphics thread
	private JPanel pnlMove; // The panel to move display in
	private JPanel pnlRotate; // The panel to move graphics in

	private JLabel label;
	private JPanel pnlTriangle;
	private JPanel pnlDisplay;
	private JLabel labelImage;

	private GUIDualThreads gui;
	private ThreadText tt;
	private ThreadRotate tr;

	/**
	 * Constructor
	 */
	public GUIDualThreads() {
		gui = this;
	}

	/**
	 * Starts the application
	 */
	public void Start() {
		frame = new JFrame();
		frame.setBounds(0, 0, 494, 332);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setTitle("Multiple Thread Demonstrator");
		InitializeGUI(); // Fill in components
		frame.setVisible(true);
		frame.setResizable(false); // Prevent user from change size
		frame.setLocationRelativeTo(null); // Start middle screen
	}

	/**
	 * Sets up the GUI with components
	 */
	private void InitializeGUI() {

		// The moving display outer panel
		pnlDisplay = new JPanel();
		Border b2 = BorderFactory.createTitledBorder("Display Thread");
		pnlDisplay.setBorder(b2);
		pnlDisplay.setBounds(12, 12, 222, 269);
		pnlDisplay.setLayout(null);
		// Add buttons and drawing panel to this panel
		btnDisplay = new JButton("Start Display");
		btnDisplay.setBounds(10, 226, 121, 23);

		/**
		 * Starts text thread
		 */

		btnDisplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				tt = new ThreadText(gui, label);
				tt.setThreadStopText(true);
				tt.start();

				btnDisplay.setEnabled(false);
				btnDStop.setEnabled(true);
			}
		});

		pnlDisplay.add(btnDisplay);
		btnDStop = new JButton("Stop");
		btnDStop.setBounds(135, 226, 75, 23);
		btnDStop.setEnabled(false);

		/**
		 * Stops thread text
		 */
		btnDStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tt.setThreadStopText(false);
				btnDisplay.setEnabled(true);
				btnDStop.setEnabled(false);
			}
		});

		pnlDisplay.add(btnDStop);
		pnlMove = new JPanel();
		pnlMove.setBounds(10, 19, 200, 200);
		Border b21 = BorderFactory.createLineBorder(Color.black);
		pnlMove.setBorder(b21);
		label = new JLabel("Your text here");
		label.setText("text");
		label.setBounds(223, 270, 10, 5);
		label.setLocation(223, 270);
		pnlMove.add(label);
		pnlDisplay.add(pnlMove);

		// Then add this to main window
		frame.add(pnlDisplay);

		// The moving graphics outer panel
		pnlTriangle = new JPanel();
		Border b3 = BorderFactory.createTitledBorder("Triangle Thread");
		pnlTriangle.setBorder(b3);
		pnlTriangle.setBounds(240, 12, 222, 269);
		pnlTriangle.setLayout(null);
		// Add buttons and drawing panel to this panel
		btnTriangle = new JButton("Start Rotate");
		btnTriangle.setBounds(10, 226, 121, 23);

		/**
		 * Start thread rotate
		 */
		btnTriangle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tr = new ThreadRotate(gui, labelImage);
				tr.start();
				tr.setThreadStopRotate(true);
				btnTriangle.setEnabled(false);
				btnTStop.setEnabled(true);
			}
		});

		pnlTriangle.add(btnTriangle);
		btnTStop = new JButton("Stop");
		btnTStop.setBounds(135, 226, 75, 23);
		btnTStop.setEnabled(false);

		/**
		 * Stops thread rotate
		 */
		btnTStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tr.setThreadStopRotate(false);
				btnTriangle.setEnabled(true);
				btnTStop.setEnabled(false);
			}
		});

		pnlTriangle.add(btnTStop);
		pnlRotate = new JPanel();
		pnlRotate.setBounds(10, 19, 200, 200);
		Border b31 = BorderFactory.createLineBorder(Color.black);
		pnlRotate.setBorder(b31);

		/**
		 * Adds Image to a label.
		 */

		ImageIcon icon = new ImageIcon("image/logo.png");
		Image scaleImage = icon.getImage().getScaledInstance(28, 28, Image.SCALE_DEFAULT);
		labelImage = new JLabel(new ImageIcon(scaleImage));

		pnlRotate.add(labelImage);
		pnlTriangle.add(pnlRotate);

		// Add this to main window

		frame.add(pnlTriangle);

	}

}
