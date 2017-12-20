class LookSolutions {
    private boolean [][] queen;
    private int numQueen;
    private int idThread;
    static private int counter;
    LookSolutions(int numQueen, int init, int idThread){
        queen = new boolean[numQueen][numQueen];
        this.numQueen = numQueen;
        queen[0][init] = true;
        this.idThread = idThread;
    }

    private boolean isValid(int row, int column) {
        for (int i = 0; i < row; i++) {
            if (queen[i][column]) { // Столбцы
                return false;
            }
            for (int j = 0; j < numQueen; j++) {
                if (queen[i][j]) {
                    if (Math.abs((i - row)) == Math.abs((j - column))) { // Диагонали
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }

    void pushQueen(int row) {
        if (row == numQueen) {    //закончилось
            synchronized(LookSolutions.class) {
                System.out.println("Thread: " + idThread);
                counter++;
                System.out.println("Num of solution: " + counter);
            }
        }

        else if (row < numQueen) {
            for (int column = 0; column < numQueen; column++) {
                    if (isValid(row, column)) {
                    queen[row][column] = true;    // Ogo! Ti che ril nastoyawaya???
                    pushQueen(row + 1);         // Следующая! Вас много, а я одна.
                    queen[row][column] = false;   // Ne poluchilos', poprobuy ewe raz
                }
            }
        }
    }
}
