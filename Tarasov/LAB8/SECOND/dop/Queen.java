package lab8.dop;


public class Queen {
    private int size;
    private int result;
    private int[][] board;

    public Queen(int s) {
        size = s;
        result = 0;
        board = new int[s][s];
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

    public int getResult() {
        return result;
    }

    public void setQueen(int a)
    {
        if (a == size) {
            ++result;
            return;
        }

        for (int i = 0; i < size; ++i) {
            if (tryQueen(a, i)) {
                board[a][i] = 1;
                setQueen(a + 1);
                board[a][i] = 0;
            }
        }
    }
}
   
