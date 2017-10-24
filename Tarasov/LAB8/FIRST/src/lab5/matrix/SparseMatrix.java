package lab5.matrix;

import lab5.exception.MyException;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;


public class SparseMatrix extends UsualMatrix implements IMatrix {
    private class Node {
        int x, y, value;
        Node (int x, int y ,int value) {this.x = x; this.y = y; this.value = value;}
    }

    LinkedList<Node> list;
    private int row;
    private int column;

    public SparseMatrix(int _row, int _column, boolean _rand){
        super(0, 0 , _rand);
        row = _row;
        column = _column;
        Random rnd = new Random();
        list = new LinkedList<>();
        if (_rand) {

            for (int i = 0; i < _row; i++) {
                for (int j = 0; j < _column; j++) {
                    int value = rnd.nextInt(10) + 1;
                    list.add(new Node(i, j, value));
                }
            }
        }
    }
    public int getEl(int i, int j){
        if (i >= row || j >= column || i < 0 || j < 0) {
            throw new MyException("Не верно заданы индексы в методе getEl(int i, int j)");
        }
        if (list != null){
            ListIterator<Node> it = list.listIterator();
            while (it.hasNext()) {
                Node temp = it.next();
                if (temp.x == i && temp.y == j) {
                    return temp.value;
                }
            }
        }
        return 0;
    }
    public void setEl(int i, int j, int value) {
        if (i >= row || j >= column || i < 0 || j < 0) {
            throw new MyException("Не верно заданы индексы в методе setEl(int i, int j, int value)");
        }
        if (value == 0) return;
        ListIterator<Node> it = list.listIterator();
        while (it .hasNext()) {
            Node temp = it.next();
            if (temp.x == i && temp.y == j) {
                it.remove();
            }
        }
        list.add(new Node(i, j, value));
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
}
