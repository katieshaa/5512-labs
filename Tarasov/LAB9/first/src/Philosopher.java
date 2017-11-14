    import java.util.concurrent.*;
import java.util.*;

public class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;
    private final int id;
    private final int ponderFactor;
    private Random rand = new Random(47);

    private void pause() throws InterruptedException {
        if(ponderFactor == 0) return;
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
    }
    public Philosopher(Chopstick left, Chopstick right, int id, int ponder) {
        this.left = left;
        this.right = right;
        this.id = id;
        ponderFactor = ponder;
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                System.out.println(this + " " + "думает");
                pause();
                // Philosopher becomes hungry
                System.out.println(this + " " + "берет правую");
                right.take();
                System.out.println(this + " " + "берет левую");
                left.take();
                System.out.println(this + " " + "кушает");
                pause();
                right.drop();
                left.drop();
            }
        } catch(InterruptedException e) {
            System.out.println(this + " " + "завис (InterruptedException)");
        } }
    public String toString() { return "Философ " + id; }
}