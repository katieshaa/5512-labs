/**
 * Created by root on 16.09.17.
 */
public class Main {
    public static void main(String[] args) {
        Object firstObject = new Object();
        Object secondObject = new Object();

        Thread thread1 = new Thread("Thread 1") {
            @Override
            public void run() {
                synchronized (firstObject) {
                    System.out.println(Thread.currentThread().getName() + " Взял первый объект");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " Пытаюсь взять второй...");
                    synchronized (secondObject) {
                        System.out.println(Thread.currentThread().getName() + " Взял второй объект");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread thread2 = new Thread("Thread 2"){
            @Override
            public void run() {
                synchronized (secondObject) {
                    System.out.println(Thread.currentThread().getName() + " Взял второй объект");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " Пытаюсь взять первый...");
                    synchronized (firstObject) {
                        System.out.println(Thread.currentThread().getName() + " Взял первый объект");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " закончил");
    }
}
