/* 
	[Reader - Writer] Реализовать многопточную программу, которая находит
	простые числа в заданных интервалах. Есть класс TaskQueue, реализованный
	на основе LinkedList, с методами:
		MyTask pop() - взять очередное задание
		void push(MyTask t) - положить очередное задание
		void showResult - вывод результатов на экран
		
	Класс MyTask
		int start; - первое число в интервале
		int end; - последнее число в интервале
		ArrayList<int> answers; - массив с простыми числами
	//можно добавить свои поля
	
	Пред началом работы очередь наполняется заданиями. После этого потоки
	забирают задачи и складывают результаты в туже очередь.
 */


import org.suai.tasks.TaskQueue;
import org.suai.tasks.MyTask;
import org.suai.prime.PrimeThread;


public class Main {

    public static void main(String[] args) {
        try {
            TaskQueue queue = new TaskQueue(Runtime.getRuntime().availableProcessors());//возвращает количество свободных процессов
            queue.push(55, 100);
            queue.push(150, 200);
            queue.push(555, 600);
            queue.findPrimes();
            queue.showResult();
        }
        catch(Exception exception) {
            System.out.println(exception);
        }
    }

}