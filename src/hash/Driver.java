package hash;

import javax.swing.JFrame;

public class Driver {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Hash Generator"); //creating a frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GuiPanel panel = new GuiPanel();
        frame.setJMenuBar(panel.setupMenu());//adding the menubar
        
        frame.add(panel);
        
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
	}

}
