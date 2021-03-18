package c14SerializationAndFileIO;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Pond {
    Duck duck = new Duck();
    
    public static void main(String[] args) {
        Pond pond = new Pond();
        try {
            FileOutputStream fs = new FileOutputStream("Pond.ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(pond);
            os.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

class Duck{
    
}
