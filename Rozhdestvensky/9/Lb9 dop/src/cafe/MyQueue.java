package cafe;

import java.util.Random;

/**
 * Created by Vengr on 05.11.2017.
 */

public class MyQueue {

    private Tourist[] queue;
    private int size;
    private int curr;

    public MyQueue(int size) {

        this.size = size;
        queue = new Tourist[size];
        curr = 0;

        int time;
        Random random = new Random(System.currentTimeMillis());

        for(int i = 0; i < size; i++) {
            queue[i] = new Tourist(i);
            time = 15 + random.nextInt(106);
            time *= 100;
            queue[i].setTime(time);
        }

    }

    public Tourist remove() throws Exception {
        if(curr == size){
            throw new Exception("Queue is empty");
        }

        Tourist me = queue[curr];
        curr++;

        return me;
    }

    public Tourist currentTourist() throws Exception {
        if(curr == size){
            throw new Exception("Queue is empty");
        }

        return queue[curr];
    }

    public boolean isEmpty() {
        if(curr >= size) {
            return true;
        } else {
            return false;
        }
    }



}
