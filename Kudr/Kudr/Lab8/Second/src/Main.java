

public class Main {

    public static void main(String[] args) throws Exception {

        Element[] elements = new Element[4];
        elements[0] = new Element(1, 5);
        elements[1] = new Element(2, 6);
        elements[2] = new Element(5, 4);
        elements[3] = new Element(3, 10);

        int weight = 50;

        Knapsack knapsack = new Knapsack(weight, elements);

        long l1 = System.currentTimeMillis();
        int[] result = knapsack.solveKnapsack(1);
        long l2 = System.currentTimeMillis();

        printArray(result);
        System.out.println("Вес: " + weight + " Максимальная стоимость: " + knapsack.getMaxCost());
        System.out.println("Количество потоков: 1 (время: " + (l2 - l1) + " мс)\n");


        int countThreads = 8;
        l1 = System.currentTimeMillis();
        result = knapsack.solveKnapsack(countThreads);
        l2 = System.currentTimeMillis();
        printArray(result);


        System.out.println("\nКоличество потоков: " + countThreads + " (время: " + (l2 - l1) + " мс)");

    }

    static void printArray(int[] array) {
        System.out.print("{ ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.print("}\n");
    }

}