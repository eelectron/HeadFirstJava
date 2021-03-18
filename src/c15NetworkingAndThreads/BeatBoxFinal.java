package c15NetworkingAndThreads;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BeatBoxFinal {
    JPanel mainPanel;
    List<JCheckBox> checkboxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame theFrame;
    String userName = "";
    JList incomingList;
    JTextField userMessage;
    int nextNum;
    ObjectInputStream in;
    ObjectOutputStream out;
    Vector<String> listVector = new Vector<String>();
    Map<String, boolean[]> otherSeqsMap = new HashMap<>();
    
    String[] instrumentsName    = {"Bass Drum", "Closed Hi-Hat", "Open hi hat", "Acoustic", "Crash", "Hand Clap", 
            "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell",
            "Vibraslap", "Low mid tom", "High Agogo"};
    
    int[] instrumentsCode       = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};
    
    public static void main(String[] args) {
        BeatBoxFinal bb = new BeatBoxFinal();
        bb.startUp(args[0]);
    }
    
    public void startUp(String name) {
        userName = name;
        
        // open connection to server
        try {
            Socket sock = new Socket("127.0.0.1", 4242);
            out = new ObjectOutputStream(sock.getOutputStream());
            in  = new ObjectInputStream(sock.getInputStream());
            Thread remoteReader = new Thread(new RemoteReader());
            remoteReader.start();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            System.out.println("Could not connect");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        setUpMidi();
        buildGui();
    }
    
    public void buildGui() {
        theFrame = new JFrame("Beat Box");
        theFrame.setDefaultCloseOperation(theFrame.EXIT_ON_CLOSE);
        
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        checkboxList = new ArrayList<>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);
        
        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);
        
        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);
        
        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);
        
        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);
        
        JButton sendIt = new JButton("Send");
        sendIt.addActionListener(new MySendListener());
        buttonBox.add(sendIt);
        
        JLabel userLabel = new JLabel(userName);
        buttonBox.add(userLabel);
        
        userMessage = new JTextField();
        buttonBox.add(userMessage);
        
        incomingList = new JList();
        incomingList.addListSelectionListener(new MyListSelectionListener());
        incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane theList = new JScrollPane(incomingList);
        buttonBox.add(theList);
        incomingList.setListData(listVector);
        
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for(int i = 0; i < instrumentsName.length; i++) {
            nameBox.add(new Label(instrumentsName[i]));
        }
        
        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);
        
        theFrame.getContentPane().add(background);
        
        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);
        for(int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }
        
        theFrame.setBounds(50, 50, 500, 500);
        theFrame.pack();
        theFrame.setVisible(true);
    }
    
    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (MidiUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void buildTrackAndStart() {
        int[] trackList = null;
        sequence.deleteTrack(track);
        track = sequence.createTrack();
        
        for(int i = 0; i < 16; i++) {
            trackList = new int[16];
            int key = instrumentsCode[i];
            for(int j = 0; j < 16; j++) {
                JCheckBox jc = checkboxList.get(i * 16 + j);
                if(jc.isSelected()) {
                    trackList[j] = key;
                }
                else {
                    trackList[j] = 0;
                }
            }
            
            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        }
        
        track.add(makeEvent(192, 9, 1, 0, 15));
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        }catch(Exception ex) {}
    }
    
    public void makeTracks(int[] list) {
        for(int i = 0; i < 16; i++) {
            int key = list[i];
            if(key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
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

    public void changeSequence(boolean[] checkboxState) {
        for(int i = 0; i < checkboxList.size(); i++) {
            JCheckBox cb = checkboxList.get(i);
            if(checkboxState[i] == true) {
                cb.setSelected(true);
            }
            else {
                cb.setSelected(false);
            }
        }
    }
    
    public class RemoteReader implements Runnable{
        boolean[] cbState = null;
        String name = null;
        Object obj = null;
        
        @Override
        public void run() {
            try {
                while((obj = in.readObject()) != null) {
                    System.out.println("Got an object from server " + obj.getClass());
                    name = (String)obj;
                    cbState = (boolean[])in.readObject();
                    otherSeqsMap.put(name, cbState);
                    listVector.add(name);
                    incomingList.setListData(listVector);
                }
            }catch(Exception ex) {
                System.out.println("Could not read the beat pattern sent from server");
                //ex.printStackTrace();
            }
        }
        
    }
    
    public class MySendListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean[] checkboxState = new boolean[256];
            for(int i = 0; i < checkboxList.size(); i++) {
                JCheckBox cb = (JCheckBox)checkboxList.get(i);
                if(cb.isSelected()) {
                    checkboxState[i] = true;
                }
            }
            
            try {
                String msg = userName + nextNum++ + ": " + userMessage.getText();
                //String msg = "hello";
                out.writeObject(msg);
                out.writeObject(checkboxState);
                //out.close();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
            userMessage.setText("");
        }
    }

    public class MyStartListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            buildTrackAndStart();
        }
    }
    
    public class MyStopListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            sequencer.stop();
        }
    }
    
    public class MyUpTempoListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            float tempoFacter = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFacter * 1.03));
        }
    }
    
    public class MyDownTempoListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            float tempoFacter = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFacter * 1.03));
        }
    }
    
    public class MyListSelectionListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()) {
                String val = (String)incomingList.getSelectedValue();
                if(val != null) {
                    boolean[] selState = otherSeqsMap.get(val);
                    changeSequence(selState);
                    sequencer.stop();
                    buildTrackAndStart();
                }
            }
        }
    }
}
