package Parallel;


import javafx.util.Pair;


public class ParallelMergeSort {
    private static Comparable[] array;
    private static MergeThread[] threads;
    private static class MergeThread extends Thread {
        private int begin;
        private int end;
        private int range;
        public MergeThread(int begin, int end, int range) {
            this.begin = begin;
            this.end = end;
            this.range = range;
        }
        public void run() {
            if(begin >= end) {
                return;
            }
            int length = end - begin + 1;
            Pair[] counters;
            if(length % range == 0) {
                counters = new Pair[length / range];
            }
            else {
                counters = new Pair[length / range + 1];
            }
            for(int i = 0; i < counters.length; ++i) {
                if((i + 1) * range > length) {
                    counters[i] = new Pair(0, length % range);
                }
                else {
                    counters[i] = new Pair(0, range);
                }
            }

            Comparable[] tmp = new Comparable[length];
            Comparable[] copy = new Comparable[array.length];
            synchronized (array) {
                System.arraycopy(array, 0, copy, 0, array.length);
            }
            int k = 0;
            while(k < tmp.length) {
                tmp[k] = Integer.MAX_VALUE;
                int index = 0;
                for(int i = 0; i < counters.length; ++i) {
                    if((int)counters[i].getValue() == (int)counters[i].getKey()) {
                        continue;
                    }
                    if(tmp[k].compareTo(copy[begin + i * range + (int)counters[i].getKey()]) >= 0) {
                        index = i;
                        tmp[k] = copy[begin + i * range + (int)counters[i].getKey()];

                    }
                }
                Pair tmpPair = counters[index];
                counters[index] = new Pair((int)tmpPair.getKey() + 1, tmpPair.getValue());
                ++k;
            }
            synchronized (array) {
                System.arraycopy(tmp, 0, array, begin, tmp.length);
            }
        }
    }
    private static void merge(int begin, int end) {
        if(end <= begin) {
            return;
        }
        int length = end - begin + 1;
        int range = length / threads.length;
        if(range < 2) {
            range = 2;
        }
        if(threads.length == 1) {
            range = length / 2;
        }
        if(length == 2) {
            range = 1;
        }
        for(int i = begin + range, j = begin; ; i += range, j += range) {
            if(i > end) {
                merge(j, end);
                break;
            }
            merge(j, i - 1);
        }
        for(int i = 0; i < threads.length; ++i) {
            if(threads[i] != null) {
                try {
                    threads[i].join();
                }
                catch (InterruptedException iEx) {
                    System.out.print("Bud join with thread");
                    System.out.println(i);
                }
            }
        }
        for(int i = 0; ; i = (i + 1) % threads.length) {
            if(threads[i] != null) {
                if(!threads[i].isAlive()) {
                    threads[i] = new MergeThread(begin, end, range);
                    threads[i].start();
                    return;
                }
                else {
                    continue;
                }
            }
            else {
                threads[i] = new MergeThread(begin, end, range);
                threads[i].start();
                return;
            }
        }
    }

    public static void sort(Comparable[] a, int numberOfThreads) {
        array = a;
        threads = new MergeThread[numberOfThreads];
        merge(0, a.length - 1);
        for(int i = 0; i < threads.length; ++i) {
            if(threads[i] != null) {
                try {
                    threads[i].join();
                }
                catch (InterruptedException iEx) {
                    System.out.print("Bad join with thread: ");
                    System.out.println(i);
                }
            }
        }
    }


}
