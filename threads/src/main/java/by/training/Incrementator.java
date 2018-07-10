package by.training;

public class Incrementator {
    private int number = 0;
    private final Object lock = new Object();

    public synchronized void increment() {
        synchronized (lock) {
            number++;
        }
    }

    public int getInt() {
        synchronized (lock) {
            return number;
        }
    }
}