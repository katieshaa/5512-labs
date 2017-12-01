import kek.maxan98.core.HardMatrix;
import kek.maxan98.core.MyMatrix;
import kek.maxan98.core.MySqMatrix;
import kek.maxan98.core.ParallelMatrixProduct;
import kek.maxan98.core.exc.MatrixMulException;
import kek.maxan98.core.exc.MatrixSumException;
import kek.maxan98.core.exc.MyException;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by MaksSklyarov on 17.02.17.
 */
public class Main {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static void main(String[] args) {
        long x = System.currentTimeMillis();
    MyMatrix m = new MyMatrix(5,3);
    m.init(3);
    MyMatrix m1 = new MyMatrix(3,4);
    m1.init(4);
    MyMatrix m2 = new MyMatrix(45,5);
    m.setData(2,2,1000);
    try{
    m2 = m.mul(m1);}
    catch(MyException ex){
        int n = ex.getErrorCode();
        System.out.println(ex.getMessage() + " " + ex.getErrorCode());
    }
        //m2.print();
        System.out.println(m2);
        MySqMatrix t2 = new MySqMatrix(5);
        t2.zero();

        MySqMatrix t3 = new MySqMatrix(5);
        t3.init(3);
        MySqMatrix t4 = new MySqMatrix(5);
        t4.init(5);
        try {
            t2 = t3.mul(t4);
        }catch(MyException ex){
            System.out.println(ex.getMessage() + " " + ex.getErrorCode());
        }

      //  t2.print();
        System.out.println(t2);

        if(t2.equals(t2)){
            System.out.println("Yea");
        }
        HardMatrix hm = new HardMatrix(6, 6);
        HardMatrix hm2 = new HardMatrix(6, 6);
        MyMatrix hm3 = new MyMatrix(6,6);
        hm2.init(6);
        hm.init(4);
//            hm.normalize();
        hm.setData(1,4,5);
        hm.setData(5,1,3);
        hm.setData(5,5,7);

            hm3 = hm.sum(hm2);


        System.out.println(hm2);
        System.out.println(hm);
        System.out.println(hm3);
        long x1 = System.currentTimeMillis();
        long x2 = x1-x;
        System.out.println("All done! It took me " + x2 + "ms to complete.");
        try {
            int one = 1000;
            int two = 500;
            MyMatrix first = new MyMatrix(one, two);
            first.random();

            MyMatrix second = new MyMatrix(two, one);
            second.random();
            long start = System.currentTimeMillis();
            first.mul(second);
            long end = System.currentTimeMillis();
            System.out.println("Without thread: " + (end - start));

            ParallelMatrixProduct product = new ParallelMatrixProduct(4);
            start = System.currentTimeMillis();
            product.parallelProduct(first, second);
            end = System.currentTimeMillis();
            System.out.println("With thread: " + (end - start));
        }
        catch(InterruptedException exception) {
            System.out.println(exception);
        } catch (MyException e) {
            e.printStackTrace();
        }
        try {
            File soundFile = new File("/Users/Maks/Downloads/ExtendedMatrixDopZadanie-master/kek/maxan98/core/clip.wav"); //Звуковой файл

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();

            clip.open(ais);

            clip.setFramePosition(0); //устан-авливаем указатель на старт

            System.out.println(ANSI_RED_BACKGROUND + ANSI_RED + "                           " + ANSI_RESET);
            System.out.println(ANSI_YELLOW_BACKGROUND + ANSI_YELLOW + "                           " + ANSI_RESET);
            System.out.println(ANSI_GREEN_BACKGROUND + ANSI_GREEN + "                           " + ANSI_RESET);
            System.out.println(ANSI_BLUE_BACKGROUND + ANSI_BLUE + "                           " + ANSI_RESET);
            System.out.println(ANSI_PURPLE_BACKGROUND + ANSI_PURPLE + "                           " + ANSI_RESET);
            System.out.println("___________________¶¶¶¶¶¶¶¶ \n" +
                    "_______________¶¶¶¶¶ \n" +
                    "____________¶¶¶¶________¶¶¶¶¶¶¶ \n" +
                    "__________¶¶¶_______¶¶¶¶¶___¶¶¶¶¶¶__¶¶ \n" +
                    "________¶¶¶_______¶¶_________¶¶¶¶¶¶__¶ \n" +
                    "_______¶¶________¶¶____¶¶¶¶¶¶_____________¶¶ \n" +
                    "_____¶¶¶_____¶__¶¶¶¶¶¶¶______________¶¶¶_¶¶¶¶¶ \n" +
                    "____¶¶¶_____¶__¶¶¶___________¶¶¶¶¶__¶¶___¶¶__¶¶ \n" +
                    "___¶¶¶_____¶¶_________¶¶____¶¶______¶¶¶¶_¶¶¶¶¶ \n" +
                    "__¶¶¶____________¶¶__¶¶_¶¶__¶¶_¶¶¶__¶¶___¶¶__¶¶ \n" +
                    "__¶¶_________¶¶__¶¶__¶__¶___¶¶__¶¶__¶¶¶¶_¶ \n" +
                    "_¶¶¶___¶¶¶¶¶_¶¶__¶¶__¶¶¶¶___¶¶¶¶¶¶_________¶¶¶¶¶ \n" +
                    "_¶¶___¶¶__¶¶_¶¶__¶¶__¶__¶¶______________¶¶¶¶¶¶¶¶¶ \n" +
                    "¶¶¶___¶¶¶¶¶__¶¶__¶¶__¶____________¶¶¶__¶¶¶¶ \n" +
                    "¶¶¶___¶¶__¶¶__¶¶¶_________¶¶¶¶____¶¶¶_¶¶¶¶__¶¶¶¶¶ \n" +
                    "¶¶¶___¶¶¶¶¶¶________¶¶¶¶__¶¶¶¶¶¶__¶¶¶_¶¶¶¶__¶¶¶¶¶¶ \n" +
                    "¶¶¶___¶¶¶¶______¶¶¶_¶¶¶¶__¶¶¶¶¶¶¶_¶¶¶_¶¶¶¶____¶¶¶¶ \n" +
                    "¶¶¶________¶__¶¶¶¶__¶¶¶¶__¶¶¶__¶¶¶¶¶¶__¶¶¶¶¶¶¶¶¶¶ \n" +
                    "_¶¶¶_____¶¶¶__¶¶¶¶__¶¶¶¶__¶¶¶___¶¶¶¶¶___¶¶¶¶¶¶¶ \n" +
                    "_¶¶¶_____¶¶¶_¶¶¶____¶¶¶¶__¶¶¶____¶¶¶¶ \n" +
                    "_¶¶¶¶____¶¶¶¶¶¶¶____¶¶¶¶__¶¶¶_______________¶ \n" +
                    "__¶¶¶____¶¶¶_¶¶¶¶¶__¶¶¶¶__¶__________¶¶¶¶__¶___¶¶¶ \n" +
                    "___¶¶¶___¶¶¶__¶¶¶¶__¶¶________¶¶¶¶¶__¶¶¶__¶___¶¶¶¶¶¶ \n" +
                    "___¶¶¶¶__¶¶¶____¶¶_______¶¶¶¶¶¶______¶___¶___¶¶¶¶¶¶¶ \n" +
                    "____¶¶¶__¶¶¶_________¶¶¶¶¶_________¶________¶¶¶¶¶¶ \n" +
                    "______¶¶¶_________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶______¶¶¶¶¶¶¶ \n" +
                    "_______¶¶¶¶¶___________¶¶¶¶¶¶¶¶_________¶¶¶¶¶¶¶¶ \n" +
                    "________¶¶¶¶¶¶¶_______________________¶¶¶¶¶¶¶¶ \n" +
                    "__________¶¶¶¶¶¶¶¶¶_______________¶¶¶¶¶¶¶¶¶¶ \n" +
                    "_____________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶ \n" +
                    "________________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶ \n" +
                    "_____________________¶¶¶¶¶¶¶¶¶¶¶¶¶");
            clip.start(); //Поехали!!!


            Thread.sleep(clip.getMicrosecondLength()/1000);
            clip.stop(); //Останавливаем
            clip.close(); //Закрываем
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
        } catch (InterruptedException exc) {}
    }
}


