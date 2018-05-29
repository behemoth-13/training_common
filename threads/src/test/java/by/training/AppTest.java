package by.training;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void calculateMatrix() {
        //init
        int[][] a = {{1, 2, 7, 4},
                    {5, 6, 7, 2},
                    {6, 1, 3, 5}};
        int[][] b = {{7, 1, 8, 1, 8, 10, 4},
                    {6, 2, 8, 2, 1, 5, 1},
                    {2, 7, 6, 1, 3, 2, 2},
                    {1, 5, 1, 9, 3, 4, 1}};

        //validate
        if (a == null || a.length == 0 || a[0] == null || a[0].length == 0) {
            fail("matrix \"a\" is incorrect");
        }
        if (b == null || b.length == 0 || b[0] == null || b[0].length == 0) {
            fail("matrix \"b\" is incorrect");
        }
        if (a[0].length != b.length) {
            fail("matrixes are not coordinates");
        }

        //make result matrix
        int m = a.length;
        int q = b[0].length;
        int[][] actualResult = new int[m][q];

        Thread[] threads = new Thread[m];


        for (int i = 0; i < m; i++) {
            threads[i] = new CalcThread(a, b, actualResult, i);
            threads[i].start();
        }

        //wait
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            fail("Interrupted");
        }

        int[][] expectedResult = {{37, 74, 70, 48, 43, 50, 24},
                                 {87, 76, 132, 42, 73, 102, 42},
                                 {59, 54, 79, 56, 73, 91, 36}};

        assertArrayEquals(expectedResult, actualResult);
    }

    @Test
    public void incrementTest() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                Incrementator.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
               Incrementator.increment();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}