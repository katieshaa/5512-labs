
public interface IMatrix {
      IMatrix sum(IMatrix two);
      IMatrix mul(IMatrix two);
      int getEl(int i, int j);
      void setEl(int i, int j, int value);
      int getRow();
      int getColumn();
}
