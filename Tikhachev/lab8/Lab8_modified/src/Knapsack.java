import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by root on 20.09.17 with love.
 */
public class Knapsack {
    private int volume;
    private int maxNumber;
    private int maxCost;
    private int maxWeight;
    private Box[] mas;
    private byte[] answerMas;
    private int answerCost = 0;
    private int answerWeight = 0;

    Knapsack(int volume, int maxNumber, int maxCost, int maxWeight) {
        this.volume = volume;
        this.maxNumber = maxNumber;
        this.maxCost = maxCost;
        this.maxWeight = maxWeight;
    }

    public void generateBoxes() {
        Random random = new Random(System.currentTimeMillis());
        //int size = random.nextInt(maxNumber) + 1;
        int size = maxNumber;
        mas = new Box[size];
        for(int i = 0; i < size; i++) {
            mas[i] = new Box(random.nextInt(maxWeight) + 1, random.nextInt(maxCost));
        }
    }

    public void printBoxes() {
        System.out.println("Box№  Cost  Weight");
        for(int i = 0; i < mas.length; i++) {
            System.out.print("Box№" + (i + 1) + "   " + mas[i].getCost() + "     " + mas[i].getWeight());
            if(answerMas[i] == 1) {
                System.out.println("  +");
            } else {
                System.out.println("");
            }
        }
    }

    public byte[] solveKnapsack(int threadNum) throws InterruptedException {
        answerCost = 0;
        if(threadNum > mas.length) {
            threadNum = mas.length;
        }
        byte[] solve = new byte[mas.length];
        for(int i = 0; i < solve.length; i++) {
            solve[i] = 0;
        }
        answerMas = new byte[solve.length];

        LinkedList<KnapsackThread> threadList = new LinkedList<>();
        int count = 0;
        int countThread;
        while(count < mas.length) {
            countThread = 0;
            int temp = count;
            while(count < (temp + threadNum)) {
               if(count > mas.length - 1) {
                   break;
               }
               countThread++;
               solve[count] = 1;
               KnapsackThread thread = new KnapsackThread("Thread #" + countThread, solve, count);
               threadList.add(thread);
               thread.start();
               solve[count] = 0;
               count++;
           }
           ListIterator it = threadList.listIterator();
           while(it.hasNext()) {
               Thread tmp = (Thread) it.next();
               tmp.join();
           }
           threadList.clear();
        }

        return answerMas;
    }

    private class KnapsackThread extends Thread {

        private final byte[] tmp;
        private int beginCost;
        private int beginWeight;
        private int beginIndex;

        KnapsackThread(String name, byte[] solve, int begin) {
            super(name);
            tmp = new byte[solve.length];
            System.arraycopy(solve, 0, tmp, 0, solve.length);
            beginCost = mas[begin].getCost();
            beginWeight = mas[begin].getWeight();
            beginIndex = begin;
        }

        @Override
        public void run() {
            findAnswer(tmp, beginWeight, beginCost, beginIndex);
        }

        private void findAnswer(byte[] solve, int weight, int cost, int lastIndex) {
            if(weight > volume) {
                return;
            }
            if(cost > answerCost) {
                answerCost = cost;
                answerWeight = weight;
                System.arraycopy(solve, 0, answerMas, 0, solve.length);
            } else {
                if (cost == answerCost) {
                    if (weight < answerWeight) {
                        answerCost = cost;
                        answerWeight = weight;
                        System.arraycopy(solve, 0, answerMas, 0, solve.length);
                    }
                }
            }
            for(int i = lastIndex + 1; i < solve.length; i++) {
                solve[i] = 1;
                findAnswer(solve, (weight + mas[i].getWeight()), (cost + mas[i].getCost()), i);
                solve[i] = 0;
            }
        }
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getVolume() {
        return volume;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getAnswerCost() {
        return answerCost;
    }

    public int getAnswerWeight() {
        return answerWeight;
    }
}
