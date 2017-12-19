import java.util.ArrayList;

public class Person {
    String name;
    ArrayList<Integer> otmetki = new ArrayList<>();

    public Person(String name, ArrayList<Integer> otmetki) {
        this.name = name;
        this.otmetki = otmetki;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + " ");
        int i = 1;
        for (Integer integer : otmetki) {
            sb.append(i + ":" + integer + " ");
            i++;
        }
        return sb.toString();
    }
}
