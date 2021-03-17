package writeAProgram;

import java.util.List;

public class DotCom {
    private List<String> locationCells;
    private String name;
    String checkYourself(String strGuess) {
        if(locationCells.isEmpty()) {
            return "kill";
        }
        
        String result = "miss";
        int index = locationCells.indexOf(strGuess);
        if(index >= 0) {
            locationCells.remove(index);
            if(locationCells.isEmpty()) {
                result = "kill";
            }
            else {
                result = "hit";
            }
        }
        return result;
    }
    
    void setLocationCells(List<String> loc) {
        this.locationCells = loc;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name + locationCells;
    }
}
