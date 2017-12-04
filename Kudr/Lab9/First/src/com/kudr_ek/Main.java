package com.kudr_ek;

public class Main {

    private static Object water = new Object();
    private static Object food = new Object();

    public static void main(String[] args) {
        Cat1 cat1 = new Cat1();
        Cat2 cat2 = new Cat2();
        cat1.start();
        cat2.start();
    }

    private static class Cat1 extends Thread {
        @Override
        public void run() {
            /*try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            synchronized (water) {
                System.out.println("Первый кот подошел к воде..");
                try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                System.out.println("Первый кот ждет пока освободится еда..");
                synchronized (food) {
                    System.out.println("Первый кот поел и попил");
                }
            }
        }
    }

    private static class Cat2 extends Thread {
        @Override
        public void run() {
           /* try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            synchronized (food) {
                System.out.println("Второй кот подошел к еде...");
                try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                System.out.println("Второй кот ждет пока освободится вода..");
                synchronized (water) {
                    System.out.println("Второй кот поел и попил");
                }

            }
        }
    }
}
