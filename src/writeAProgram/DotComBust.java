package writeAProgram;

import java.util.ArrayList;
import java.util.List;

public class DotComBust {
    private GameHelper helper = new GameHelper();
    private int numOfGuessess = 0;
    private ArrayList<DotCom> dotComList = new ArrayList<DotCom>();
    
    public static void main(String[] args) {
        DotComBust game = new DotComBust();
        game.setUpGame();
        game.startPlaying();
    }
    
    private void setUpGame() {
        DotCom one = new DotCom();
        one.setName("Pets.com");
        
        DotCom two = new DotCom();
        two.setName("eToys.com");
        
        DotCom three = new DotCom();
        three.setName("Goqii.com");
        
        dotComList.add(one);
        dotComList.add(two);
        dotComList.add(three);
        
        System.out.println("Your goal is to sink three dot coms");
        
        for(DotCom dc : dotComList) {
            List<String> newLocation = helper.placeDotCom(3);
            dc.setLocationCells(newLocation);
        }
    }
    
    private void startPlaying() {
        while(dotComList.isEmpty() == false) {
            String guess = helper.getUserInput("Enter a number:");
            checkUserGuess(guess);
        }
        finishGame();
    }
    
    private void checkUserGuess(String userGuess) {
        numOfGuessess += 1;
        String result = "miss";
        for(int i = 0; i < dotComList.size(); i++) {
            result = dotComList.get(i).checkYourself(userGuess);
            if(result.equals("hit")) {
                break;
            }
            
            if(result.equals("kill")) {
                dotComList.remove(i);
                break;
            }
        }
        
        System.out.println(result);
    }
    
    private void finishGame() {
        System.out.println("All Dot coms are dead !");
        if(numOfGuessess <= 18) {
            System.out.println("It only took you " + numOfGuessess + " guesses");
        }
        else {
            System.out.println("Took so long ." + numOfGuessess + " guesses");
        }
    }
}
