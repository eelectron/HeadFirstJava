package c11ExceptionHandling;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class MusicTest1 {
    public void play() {
        try {
            Sequencer seq = MidiSystem.getSequencer();
            System.out.println("success");
        }catch(MidiUnavailableException ex) {
            System.out.println("bummer");
        }
        
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MusicTest1 mt = new MusicTest1();
        mt.play();
    }
}
