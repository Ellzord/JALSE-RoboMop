package robomop;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class RoboMop {

    public static void main(final String[] args) {
	// Create window and panel
	final JFrame frame = new JFrame("JALSE-RoboMop");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new BorderLayout());
	frame.add(new RoboMopPanel(), BorderLayout.CENTER);
	frame.pack();
	frame.setResizable(false);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }
}
