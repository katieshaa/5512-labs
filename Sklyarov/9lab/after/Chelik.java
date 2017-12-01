package face.eshkere;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chelik {
    private static int num;
    private int number;
    private int time;

    public Chelik(int time){
        this.time = time;
        number = num+1;
        num++;
    }

    public static Chelik[] genMans(int amount){
        Chelik[] man = new Chelik[amount];
        Random random = new Random();
        for(int i = 0; i < amount;i++){
            man[i] = new Chelik(random.nextInt()%20);

        } return man;
    }
    public static Chelik genMan(){
        Chelik man;
        Random random = new Random();
        int randomNum = ThreadLocalRandom.current().nextInt(15, 119 + 1);
            man = new Chelik(randomNum);
        System.out.println(man.time);
         return man;
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
