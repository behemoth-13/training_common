package by.training;

public class Incrementator {
    private static Integer number = 0;

    public static void increment() {
        synchronized (number) {
            number++;
            System.out.println(number);
        }
    }
}
