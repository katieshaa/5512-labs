import java.util.ArrayList;

public class Book {

    private ArrayList<Person> names = new ArrayList<Person>();

    public Book() { }

    public int size(){
        return names.size();
    }

    public synchronized void addPerson(String name, String password){
        Person person = new Person(name, password);
        names.add(person);
    }
    public synchronized Person getPerson(int index) {
        return names.get(index);
    }

    public synchronized void reset() {
        names.clear();
    }
}