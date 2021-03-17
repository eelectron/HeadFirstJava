package c12GettingGUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MiniMusicPlayer3 {
    private JFrame f = new JFrame("My first music video");
    private MyDrawPanelRect ml;
    private final int VELOCITY = 120;
    private final int NOTE_ON = 144;
    private final int NOTE_OFF = 128;
    public static void main(String[] args) {
        MiniMusicPlayer3 music = new MiniMusicPlayer3();
        music.go();
    }
    
    public void go() {
        setUpGui();
        
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener(ml, new int[] {127});
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();
            
            int r = 0;
            for(int i = 0; i < 128; i += 5) {
                r = (int)(Math.random() * 128);
                track.add(makeEvent(NOTE_ON,    1,      r,      VELOCITY,   i));
                track.add(makeEvent(176,        1,      127,    0,          i + 2));
                track.add(makeEvent(NOTE_OFF,   1,      r,      VELOCITY,   i + 4));
            }
            
            sequencer.setSequence(seq);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        }catch(Exception ex) {ex.printStackTrace();}
    }
    
    public void setUpGui() {
        ml = new MyDrawPanelRect();
        f.setContentPane(ml);
        f.setBounds(30, 30, 300, 300);
        f.setVisible(true);
    }
    
    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        
        try {
            ShortMessage sm = new ShortMessage();
            sm.setMessage(comd, chan, one, two);
            event = new MidiEvent(sm, tick);
        } catch (InvalidMidiDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return event;
    }
}

class MyDrawPanelRect extends JPanel implements ControllerEventListener{
    boolean msg = false;
    int note = 0, velocity = 0;
    
    @Override
    public void controlChange(ShortMessage event) {
        note = event.getData1();
        velocity = event.getData2();
        //System.out.println(note + " " + velocity);
        msg = true;
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        if(msg) {
            int r = (int)(Math.random() * 256);
            int gr = (int)(Math.random() * 256);
            int b = (int)(Math.random() * 256);
            
            g.setColor(new Color(r, gr, b));
            
            // create random size rectangle
            int ht = (int)(Math.random() * 120 + 10);
            int wt = (int)(Math.random() * 120 + 10);
            int x = (int)(Math.random() * 40 + 10);
            int y = (int)(Math.random() * 40 + 10);
            
            g.fillRect(x, y, ht, wt);
            msg = false;
        }
    }
}
