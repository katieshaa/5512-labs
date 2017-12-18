
public class mergeSort {
    private static Comparable[] array;
    public static void sort(Comparable[] a) {
        array = a;
        merge(0, a.length - 1);
    }
    private static void merge(int begin, int end) {
        if(begin >= end) {
            return;
        }
        int mid = (begin + end) / 2 + 1;
        merge(begin, mid - 1);
        merge(mid, end);
        Comparable[] tmp = new Comparable[end - begin + 1];
        int k = 0;
        int i = begin;
        int j = mid;
        while(k < tmp.length) {
            if(i >= mid) {
                tmp[k] = array[j];
                ++k;
                ++j;
                continue;
            }
            if( j > end) {
                tmp[k] = array[i];
                ++k;
                ++i;
                continue;
            }
            if(array[i].compareTo(array[j]) <= 0) {
                tmp[k] = array[i];
                ++i;
                ++k;
            }
            else {
                tmp[k] = array[j];
                ++j;
                ++k;
            }
        }
        System.arraycopy(tmp, 0, array, begin, tmp.length);
    }
}
