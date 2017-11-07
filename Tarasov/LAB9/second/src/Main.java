/*
* Реализовать программу, которая подсчитывает статистику употребления слов в заданных тексстовых файлах. Программа получает список текстовых
* файлов в качестве параметров командной строки. Каждый файл должен обрабатываться в отдельном потоке. Для подсчета числа уникальных слов используется общий для всех потоков
* HashMap (ключ --- слово, значение --- количество употреблений)*/

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length > 0) {

            String[] filenames = new String[args.length];

            for (int i = 0; i < args.length; i++) {
                filenames[i] = args[i];
            }

            ReaderWriter readerWriter = new ReaderWriter(filenames);

            readerWriter.scan();

            readerWriter.printMap();
        } else {
            System.out.println("Error. Input filenames!");
            System.exit(0);
        }

    }
}
