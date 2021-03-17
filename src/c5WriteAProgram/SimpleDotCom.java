package c5WriteAProgram;

public class SimpleDotCom {
    int[] locationCells;
    int numOfHits;
    
    String checkYourself(String strGuess) {
        int guess = Integer.parseInt(strGuess);
        String result = "miss";
        for(int i = 0; i < locationCells.length; i++) {
            if(guess == locationCells[i]) {
                result = "hit";
                numOfHits += 1;
                break;
            }
        }
        
        if(numOfHits == locationCells.length) {
            result = "kill";
        }
        
        System.out.println(result);
        return result;
    }
    
    void setLocationCells(int[] loc) {
        this.locationCells = loc;
    }
}
