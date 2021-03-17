package c12GettingGUI;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SimpleGUI1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JButton button = new JButton("click me");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(button);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
    
    public void changeIt() {
        //button.setText("I've been clicked");
    }
}
