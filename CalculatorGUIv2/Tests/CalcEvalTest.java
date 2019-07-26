/**
 * Author: Curtis Noonan
 * September 18, 2018
 * COMP 585 GUI
 * CalcEvalTest.java
 * The purpose of this class is to test the
 * evaluate method within CalcEval.
 */
import org.junit.Test;

import static org.junit.Assert.*;

public class CalcEvalTest {

    @Test
    public void evaluate() {
        CalcEval testCalcEval = new CalcEval();
        assertEquals(6,testCalcEval.evaluate("1 + 2 + 3"));
        assertEquals(0,testCalcEval.evaluate("1 + 2 - 3"));
        assertEquals(-1,testCalcEval.evaluate("1 - 1 - 1"));
        assertEquals(8,testCalcEval.evaluate("2 + 2 * 3"));
        assertEquals(2,testCalcEval.evaluate("1 + 3 / 3"));
        assertEquals(2,testCalcEval.evaluate("2 % 10"));
        assertNotEquals(5,testCalcEval.evaluate("1 + 2 + 3"));
        assertNotEquals(9,testCalcEval.evaluate("2 + 2 * 3"));
    }

}