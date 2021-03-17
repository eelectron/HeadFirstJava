package c12GettingGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TwoButtons {
    JFrame frame;
    JLabel label;
    
    public static void main(String[] args) {
        TwoButtons gui = new TwoButtons();
        gui.go();
    }
    
    public void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton labelButton = new JButton("Change label");
        labelButton.addActionListener(new LabelListener());
        
        JButton colorButton = new JButton("Change circle");
        colorButton.addActionListener(new ColorListener());
        
        label = new JLabel("I am label!");
        MyDrawPanel panel = new MyDrawPanel();
        
        frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
        frame.getContentPane().add(BorderLayout.EAST, labelButton);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.WEST, label);
        
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
    
    class LabelListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            label.setText("Ouch! " + (int)(Math.random() * 100));
        }
        
    }

    class ColorListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            frame.repaint();
        }
    }

}

