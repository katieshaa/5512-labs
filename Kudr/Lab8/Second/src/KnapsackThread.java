


import java.lang.Thread;


public class KnapsackThread extends Thread {

    private Knapsack knapsack;

    private int[] currentCounts;
    private int currentWeight;
    private int currentCost;

    private int[] bestCounts;
    private int bestCost;

    private int beginCounts;
    private int endCounts;


    public KnapsackThread(Knapsack knapsack, int beginCounts, int endCounts) {
        this.knapsack = knapsack;

        currentCounts = new int[knapsack.getElementsCount()];
        currentCost = 0;
        currentWeight = 0;

        bestCounts = new int[knapsack.getElementsCount()];
        bestCost = 0;

        this.beginCounts = beginCounts;
        this.endCounts = endCounts;
    }


    @Override
    public void run() {
        solve(0);
    }

    public void solve(int index) {

        if (index == currentCounts.length) {
            if (currentCost > bestCost) {
                bestCost = currentCost;
                bestCounts = currentCounts.clone();
            }
            return;
        }

        int availableCounts = getAvailableWeight() / knapsack.getItem(index).getWeight();

        if (index == 0 && beginCounts > availableCounts) {
            return;
        }

        int begin = 0;
        if (index == 0) {
            begin = beginCounts;
        }

        int end;
        if (index == 0 && endCounts <= availableCounts) {
            end = endCounts;
        } else {
            end = availableCounts;
        }

        for (int i = begin; i <= end; i++) {
            changeElement(index, i);
            solve(index + 1);
            changeElement(index, -i);
        }
    }

    private void changeElement(int index, int count) {
        if (currentCounts[index] + count >= 0) {
            currentCounts[index] += count;
            currentWeight += knapsack.getItem(index).getWeight() * count;
            currentCost += knapsack.getItem(index).getCost() * count;
        }
    }


    private int getAvailableWeight() {
        return knapsack.getMaxWeight() - currentWeight;
    }

    public int[] getBestCounts() {
        return bestCounts;
    }


    public int getBestCost() {
        return bestCost;
    }


}