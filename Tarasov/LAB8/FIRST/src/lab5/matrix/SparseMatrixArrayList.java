package lab5.matrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class SparseMatrixArrayList extends UsualMatrix implements IMatrix{
    class Element {
        int  j, value;
        public Element(int j, int value) {
            this.j = j; this.value = value;
        }
    }
    private int column;
    private int row;
    private ArrayList<LinkedList<Element>> arrlist;
    public SparseMatrixArrayList (int _row, int _column) {
        super(0, 0 , false);
        arrlist = new ArrayList<>();
        row = _row;
        column = _column;
        for (int i = 0; i < _row; i++) {
            arrlist.add(new LinkedList<>());
        }
    }
    public int getEl(int i, int j) {
        LinkedList<Element> temp = arrlist.get(i);
        int value = 0;
        if (!temp.isEmpty()) {
            ListIterator<Element> it  = temp.listIterator();
            while (it.hasNext()) {
                Element tempel = it.next();
                if (tempel.j == j) {
                    value = tempel.value;
                }
            }
        }
        return value;
    }
    public void setEl(int i, int j, int value) {
        /*LinkedList<Element> temp = arrlist.get(i);
        temp.add(new Element(j, value));*/
        /*if (i >= row || j >= column || i < 0 || j < 0) {
            throw new MyException("Не верно заданы индексы в методе setEl(int i, int j, int value)");
        }
        ListIterator<Node> it = list.listIterator();
        while (it .hasNext()) {
            Node temp = it.next();
            if (temp.x == i && temp.y == j) {
                it.remove();
            }
        }
        list.add(new Node(i, j, value));
    }*/

        LinkedList<Element> temp = arrlist.get(i);
        ListIterator<Element> it = temp.listIterator();
        while (it.hasNext()) {
            Element temel = it.next();
            if (temel.j == j) {
                it.remove();
            }
        }
        temp.add(new Element(j , value));
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
}
