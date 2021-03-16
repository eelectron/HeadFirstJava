package writeAProgram;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class GameHelper {
    private int gridLength = 7;
    private int gridSize = gridLength * gridLength;
    private int comCount = 0;
    private final int DOTCOM_SIZE = 3;
    private final int HORIZONTAL = 0;
    private final int VERTICAL = 1;
    private Scanner sc = new Scanner(System.in);
    private Set<Integer> occupiedCells = new HashSet<>();
    public List<String> placeDotCom(int comSize){
        List<String> cells = new ArrayList<>();
        comCount += 1;
        
        // pick a random number to place the dotCom
        boolean found = false;
        while(found == false) {
            int random = (int)(Math.random() * gridSize);
            int row = random / gridLength;
            int col = random % gridLength;
            
            List<String> hor = getVacantCell(row, col, DOTCOM_SIZE, HORIZONTAL);
            if(hor.isEmpty() == false) {
                cells = hor;
                break;
            }
            
            List<String> ver = getVacantCell(row, col, DOTCOM_SIZE, VERTICAL);
            if(ver.isEmpty() == false) {
                cells = ver;
                break;
            }
        }
        return cells;
    }
    
    private List<String> getVacantCell(int row, int col, int size, int dir) {
        // TODO Auto-generated method stub
        List<String> cells = new ArrayList<>();
        int x = row, y = col, hash;
        boolean isVacant = true;
        int vacantCell = 0;
        for(int i = 0; i < size; i++) {
            if(dir == HORIZONTAL) {
                y = col + i;   
            }
            else {
                x = row + i;
            }
            
            if(x < 0 || x >= gridLength || y < 0 || y >= gridLength) {
                isVacant = false;
                break;
            }
            
            hash = x * gridLength + (y);
            if(occupiedCells.contains(hash)) {
                isVacant = false;
                break;
            }
            
            vacantCell += 1;
        }
        
        if(isVacant == false || vacantCell < size) {
            return cells;
        }
        
        // collect cells
        for(int i = 0; i < size; i++) {
            if(dir == HORIZONTAL) {
                y = col + i;   
            }
            else {
                x = row + i;
            }
            
            String code = (char)('a' + x) + "" + y;     // "a2", "b4"
            cells.add(code);
            occupiedCells.add(x * gridLength + y);
        }
        return cells;
    }

    public String getUserInput(String prompt) {
        System.out.println(prompt + " ");
        String input = sc.next();
        input.toLowerCase();
        return input;
    }
}
