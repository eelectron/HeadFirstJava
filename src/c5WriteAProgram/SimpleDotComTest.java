package c5WriteAProgram;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SimpleDotComTest {
    /**
     * Tests that assertions are enabled.
     */
    /*@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }*/
    
    @Test
    void testCheckYourself() {
        //fail("Not yet implemented");
        SimpleDotCom dot = new SimpleDotCom();
        
        int[] loc = {2, 3, 4};
        dot.setLocationCells(loc);
        
        String userGuess = "2";
        String result = dot.checkYourself(userGuess);
        assertEquals("hit", result);
    }

    @Test
    void testSetLocationCells() {
        //fail("Not yet implemented");
    }

}
