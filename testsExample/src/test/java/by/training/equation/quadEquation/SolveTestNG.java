package by.training.equation.quadEquation;

import by.training.equation.QuadEquation;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class SolveTestNG {
    @Test
    public void twoSolution() {
        QuadEquation equation = new QuadEquation();
        double[] result = equation.solve(1,3,-4);
        assertEquals(2, result.length);
        assertEquals(-4, result[0], 0);
        assertEquals(1, result[1], 0);
    }

    @Test
    public void oneSolution() {
        QuadEquation equation = new QuadEquation();
        double[] result = equation.solve(1,12,36);
        assertEquals(1, result.length);
        assertEquals(-6, result[0], 0);
    }

    @Test
    public void noSolution() {
        QuadEquation equation = new QuadEquation();
        double[] result = equation.solve(4,1,2);
        assertNull(result);
    }
}
