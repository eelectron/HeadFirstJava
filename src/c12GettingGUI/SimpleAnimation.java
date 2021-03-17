package c12GettingGUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimpleAnimation {
    int x = 70;
    int y = 70;
    
    public static void main(String[] args) {
        SimpleAnimation gui = new SimpleAnimation();
        gui.go();
    }
    
    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        MyDrawPanel panel = new MyDrawPanel();
        frame.getContentPane().add(panel);
        frame.setSize(400, 400);
        frame.setVisible(true);
        
        for(int i = 0; i < 128; i++) {
            x += 1;
            y += 1;
            panel.repaint();
            
            try {
                Thread.sleep(100);
            }catch(Exception ex) {
                
            }
        }
    }
    
    class MyDrawPanel extends JPanel{
        public void paintComponent(Graphics g) {
            g.setColor(Color.green);
            g.fillOval(x, y, 40, 40);
        }
    }
}
