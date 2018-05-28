package by.training;

public class CalcThread extends Thread {
    private int row;
    private int[][] a;
    private int[][] b;
    private int[][] result;
    private int n;

    public CalcThread(int[][] a, int[][] b, int[][] result, int row) {
        super();
        this.a = a;
        this.b = b;
        this.row = row;
        this.result = result;
        this.n = b.length;
    }

    @Override
    public void run() {
        for (int col = 0; col < result[row].length; col++) {
            result[row][col] = calcSingleValue(row, col);
        }
    }

    private int calcSingleValue(int row, int col) {
        int c = 0;
        for (int i = 0; i < n; i++) {
            c += a[row][i] * b[i][col];
        }
        return c;
    }
}