package lab8;

public class ThreadsCell extends Thread {
    private UsualMatrix firstMatrix;
    private UsualMatrix secondMatrix;
    private UsualMatrix rezultMatrix;
    private int firstIND;
    private int secondIND;

    public ThreadsCell(UsualMatrix firstMatrix, UsualMatrix secondMatrix, UsualMatrix rezultMatrix, int firstIND, int secondIND) {
        this.firstIND = firstIND;
        this.secondIND = secondIND;
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.rezultMatrix = rezultMatrix;
    }

    @Override
    public void run() {
        int endIND = rezultMatrix.getRow() * rezultMatrix.getColumn();
        for (int IND = firstIND; (IND < secondIND) && (IND < endIND); IND++ ) {

            int i = IND / rezultMatrix.getColumn();
            int j = IND % rezultMatrix.getColumn();

            rezultMatrix.setEl(i, j, 0);

            for(int k = 0; k < firstMatrix.getColumn(); k++) {
                rezultMatrix.setEl(i, j, rezultMatrix.getEl(i, j) + (firstMatrix.getEl(i, k) * secondMatrix.getEl(k, j)));
            }

        }
    }
}
