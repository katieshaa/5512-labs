import java.util.Random;

/**
 * Created by root on 24.10.17 with love.
 */
public class Queue {
    private Tourist[] queuq;
    private int maxSize;
    private int currentAmount;
    private int front;
    private int rear;

    public Queue(int maxSize) {
        this.maxSize = maxSize;
        queuq = new Tourist[maxSize];
        Random random = new Random(System.currentTimeMillis());
        int time;
        for(int i = 0; i < maxSize; i++) {
            queuq[i] = new Tourist(i);
            time = 15 + random.nextInt(106);
            time *= 100;
            queuq[i].setTimeUseComputer(time);
        }
        front = 0;
        rear = maxSize;
        currentAmount = maxSize;
    }

    public Tourist remove() throws Exception {
        if(front == maxSize) {
            throw new Exception("Queue is empty");
        }
        Tourist tmp = queuq[front];
        front++;
        currentAmount--;
        return tmp;
    }

    public boolean isEmpty() {
        if(front >= maxSize) {
            return true;
        } else {
            return false;
        }
    }

    public void waitingForTurn() {
        for(int i = front; i < maxSize; i++) {
            System.out.println("Tourist " + i + " waiting for turn");
        }
    }

    public void printTiming() {
        for(int i = 0; i < maxSize; i++) {
            System.out.println("Tourist " + i + " = " + queuq[i].getTimeUseComputer());
        }
    }
}
