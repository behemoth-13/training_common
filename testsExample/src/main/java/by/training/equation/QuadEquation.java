package by.training.equation;

public class QuadEquation {
    public double[] solve(double a, double b, double c) {
        double[] result;
        double D = Math.pow(b, 2) - 4 * a * c;
        if (D < 0) {
            result = null;
        } else if (D == 0) {
            result = new double[1];
            result[0] = -b/(2*a);
        } else {
            result = new double[2];
            result[0] = (-b - Math.sqrt(D))/(2*a);
            result[1] = (-b + Math.sqrt(D))/(2*a);
        }
        return result;
    }
}
