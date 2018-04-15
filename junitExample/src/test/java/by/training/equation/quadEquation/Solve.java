package by.training.equation.quadEquation;

import by.training.equation.QuadEquation;
import org.junit.Assert;
import org.junit.Test;

public class Solve {
    @Test
    public void twoSolution() {
        QuadEquation equation = new QuadEquation();
        double[] result = equation.solve(1,3,-4);
        Assert.assertEquals(2, result.length);
        Assert.assertEquals(-4, result[0], 0);
        Assert.assertEquals(1, result[1], 0);
    }

    @Test
    public void oneSolution() {
        QuadEquation equation = new QuadEquation();
        double[] result = equation.solve(1,12,36);
        Assert.assertEquals(1, result.length);
        Assert.assertEquals(-6, result[0], 0);
    }

    @Test
    public void noSolution() {
        QuadEquation equation = new QuadEquation();
        double[] result = equation.solve(4,1,2);
        Assert.assertNull(result);
    }
}
