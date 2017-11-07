
public class Knapsack {

    private int maxWeight;
    private Element[] elements;
    private int maxCost = 0;

    public Knapsack(int maxWeight, Element[] elements) {
        this.maxWeight = maxWeight;
        this.elements = elements;
    }

    public int getMaxWeight() {
        return this.maxWeight;
    }


    public Element getItem(int index) {
        if (index < 0 || index >= this.elements.length) {
            throw new RuntimeException("Такого элемента не существует (index: " + index + ")");
        }

        return elements[index];
    }

    public int getElementsCount() {
        return elements.length;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public int[] solveKnapsack(int countThreads) throws InterruptedException {
        maxCost = 0;
        int result[] = new int[elements.length];

        int availableCounts = maxWeight / elements[0].getWeight();
        int beginCounts = 0;

        KnapsackThread[] knapsackThreads = new KnapsackThread[countThreads];

        for (int i = 0; i < knapsackThreads.length; i++) {

            int count;
            if (i == knapsackThreads.length - 1) {
                count = availableCounts - beginCounts;
            } else {
                count = availableCounts / countThreads;
            }

            knapsackThreads[i] = new KnapsackThread(this, beginCounts, beginCounts + count);
            knapsackThreads[i].start();

            beginCounts += count;
        }

        for (int i = 0; i < knapsackThreads.length; i++) {
            knapsackThreads[i].join();

            if (knapsackThreads[i].getBestCost() > maxCost) {
                maxCost = knapsackThreads[i].getBestCost();
                result = knapsackThreads[i].getBestCounts().clone();
            }
        }

        return result;
    }

}
