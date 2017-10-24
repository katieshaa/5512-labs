package lab5.matrix;

/**
 * Created by Nikita on 01.05.2017.
 */
public class SquareMatrix extends UsualMatrix implements IMatrix {
    public SquareMatrix (int _size, boolean _rand) {
        super(_size, _size, _rand);
        if (!_rand) {
            for (int i = 0; i < _size; i++) {
                for (int j = 0; j < _size; j++) {
                    array[i][j] = 1;
                }
            }
        }
    }

    public SquareMatrix sum (SquareMatrix _two) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                this.array[i][j] += _two.array[i][j];
            }
        }
        return this;
    }
}
