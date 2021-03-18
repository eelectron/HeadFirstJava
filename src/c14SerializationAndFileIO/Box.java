package c14SerializationAndFileIO;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Box implements Serializable{
    private int width, height;
    public void setWidth(int w) {
        width = w;
    }
    
    public void setHeight(int h) {
        height = h;
    }
    
    public static void main(String[] args) {
        Box myBox = new Box();
        myBox.setHeight(50);
        myBox.setWidth(20);
        
        try {
            FileOutputStream fs = new FileOutputStream("Box.ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(myBox);
            os.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        
    }
}
