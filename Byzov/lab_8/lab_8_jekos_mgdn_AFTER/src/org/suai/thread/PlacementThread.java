package org.suai.thread;


import java.lang.Thread;


public class PlacementThread extends Thread {

    private int n;
    private int k;
    private int start; //первое число в последоваельности
    private int end;

    private boolean[] used;//используется ли элемент, чтобы не было повторов
    private int[] sequence;//нужная последовательность

    private long count = 0; //сколько последовательностей получилось


    public PlacementThread(int n, int k, int start, int end) {
        this.n = n;
        this.k = k;
        this.start = start;
        this.end = end;

        this.used = new boolean[n];
        this.sequence = new int[k];
    }


    public long getCount() {
        return this.count;
    }


    private int settingValueSequence(int i) { //есть ли у последоват с таким инд предыд элемент, если в не нулевом, то перех в пред индекс
        if(i != 0) i--;
        this.used[this.sequence[i]] = false;
        this.sequence[i]++;

        return i;
    }


    @Override
    public void run() {
        int i = 0;
        boolean find = false;

        this.sequence[0] = this.start; //первое число
        while(this.sequence[0] != this.end) { //пока первый элемент не достигнет предела
            find = false;

            if(i == k) {
                this.count++;

                i = settingValueSequence(i);
            }
            else {
                for(int j = this.sequence[i]; j < this.n; j++) {//перебор всех возможных значений
                    if(this.used[j] != true) {
                        this.used[j] = true;
                        this.sequence[i] = j;
                        find = true;

                        i++;
                        if(i < k) this.sequence[i] = 0;

                        break;
                    }
                }

                if(find == false) {
                    i = settingValueSequence(i);
                }
            }
        }
    }

}