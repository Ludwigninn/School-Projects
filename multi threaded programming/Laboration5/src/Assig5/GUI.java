package Assig5;


import javax.swing.*;

/**
 * GUI represents  3 values total cars, total in amount and out amount.
 * Created by ludwig on 10/01/2017.
 */
public class GUI {
    private JFrame frame;
    private JLabel lblIn;            // Waiting in adventure queue
    private JLabel lblOut;
    private  JLabel lbltotal;

    /**
     * Starts GUI
     */
    public void start() {
        frame = new JFrame();
        frame.setBounds(0, 0, 574, 431);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("PArkinghouse");
        InitializeGUI();                    // Fill in components
        frame.setVisible(true);
        frame.setResizable(false);            // Prevent user from change size
        frame.setLocationRelativeTo(null);    // Start middle screen
    }

    /**
     * Init method
     */
    private void InitializeGUI() {


        lblIn = new JLabel("In ur parkingshuset "+"0");
        lblIn.setBounds(145, 84, 250, 250);
        lblOut = new JLabel("UT ur parkingshuset "+"0");
        lblOut.setBounds(145, 100, 250, 250);
        lbltotal = new JLabel("Antalet bilar i parkingshuset "+"0");
        lbltotal.setBounds(145, 120, 250, 250);
        frame.add(lbltotal);
        frame.add(lblOut);
        frame.add(lblIn);
    }

    /**
     * Set text label in
     * @param text
     */
    public void setTestlblin(String text){
        lblIn.setText("In hur parkeings huset "+" "+text);
    }

    /**
     * Set text label out
     * @param text
     */
    public void setTestlblOut(String text){

        lblOut.setText("Ut hur parkeings huset "+" "+text);
    }

    /**
     * Set text label total
     * @param text
     */
    public void setTestTotal(String text){

        lbltotal.setText("Antalet bilar i parkingshuset "+" "+text);
    }
}