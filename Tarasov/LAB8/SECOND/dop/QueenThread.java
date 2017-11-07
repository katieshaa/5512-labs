package lab8.dop;

public class QueenThread extends Thread {
    private int[][] board;
    private int size;
    private int i;

    public QueenThread(int boardSize, int i) {
        size = boardSize;
        this.i = i;
        board = new int[boardSize][boardSize];
        board[0][i] = 1;
    }

    private boolean tryQueen(int a, int b) {
        for (int i = 0; i < a; ++i)
            if (board[i][b] == 1)
                return false;

        for (int i = 1; i <= a && b - i >= 0; ++i)
            if (board[a - i][b - i] == 1)
                return false;

        for (int i = 1; i <= a && b + i < size; i++)
            if (board[a - i][b + i] == 1)
                return false;

        return true;
    }

    private void setQueen(int a) {
        if (a == size) {
            NQueenWithThreads.increment();
            return;
        }

        for (int i = 0; i < size; i++) {
            if (tryQueen(a, i)) {
                board[a][i] = 1;
                setQueen(a + 1);
                board[a][i] = 0;
            }
        }
    }

    @Override
    public void run() {
        setQueen(1);
    }
}
