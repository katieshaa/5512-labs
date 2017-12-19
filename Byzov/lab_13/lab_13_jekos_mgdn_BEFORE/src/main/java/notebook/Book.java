package notebook;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

class Book {
    private String inputFile;
    private ConcurrentHashMap<String, LinkedList<String>> phoneBook;

    private static Logger logger = Logger.getLogger(Book.class.getName());

    Book(String inputFile) {
        this.inputFile = inputFile;
        phoneBook = new ConcurrentHashMap<>();

    }

    void readBook() {
        try (InputStream input = new FileInputStream(inputFile)) {
            Scanner scanner = new Scanner(input);

            while (scanner.hasNext()) {

                LinkedList<String> phones;
                String string = scanner.nextLine();
                String[] strings = string.split(";");
                String name = strings[0];

                String[] arrayStrings = strings[1].split(" ");
                phones = new LinkedList<>();
                Collections.addAll(phones, arrayStrings);

                String[] cat = strings[2].split(" ");
                phoneBook.put(name, phones);
            }

        } catch (FileNotFoundException e) {
            logger.info("File not found. ");
        } catch (IOException e) {
            logger.info("Problems with the using file. ");
        }
    }

    ConcurrentHashMap<String, LinkedList<String>> getPhoneBook() {
        return phoneBook;
    }

    void add(String name, String phone) {
        if (phoneBook.containsKey(name)) {
            if (!phoneBook.get(name).contains(phone))
                phoneBook.get(name).add(phone);
        } else {
            LinkedList<String> list = new LinkedList<>();
            list.add(phone);
            phoneBook.put(name, list);

        }
    }

    void remove(String name) {
        phoneBook.remove(name);
    }

    String getBook() {
        StringBuilder str = new StringBuilder();
        ArrayList<String> list = new ArrayList<>();

        for (Map.Entry<String, LinkedList<String>> entry : phoneBook.entrySet()) {
            list.add(entry.getKey());
        }
        for (int i = 0; i < list.size(); i++)
            str.append("<p>").append(list.get(i)).append(" ").append(getPhones(phoneBook.get(list.get(i)), true)).append("</p>");

        return str.toString();
    }

    private String getPhones(LinkedList<String> list, boolean command) {
        StringBuilder str = new StringBuilder();
        ListIterator<String> it = list.listIterator();
        while (it.hasNext()) {
            str.append(it.next());
            if (it.hasNext() && command)
                str.append(", ");
            else if (it.hasNext() && !command)
                str.append(" ");
        }
        return str.toString();
    }

}


