package c5WriteAProgram;

import java.util.Scanner;

public class SimpleDotComGame {
    public static void main(String[] args) {
        int numOfGuesses = 0;
        
        SimpleDotCom dotCom = new SimpleDotCom();
        int random = (int)(Math.random() * 5);
        int[] loc = {random, random + 1, random + 2};
        dotCom.setLocationCells(loc);
        
        boolean isAlive = true;
        
        Scanner sc = new Scanner(System.in);
        while(isAlive == true) {
            String guess = sc.next();
            String result = dotCom.checkYourself(guess);
            numOfGuesses += 1;
            if(result.equals("kill")) {
                isAlive = false;
                System.out.println("You took " + numOfGuesses + " guesses");
            }
        }
    }
}
