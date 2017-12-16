public class Deadlock extends Thread {

        private Integer first;
        private Integer second;


        public Deadlock(Integer first, Integer second) {
            this.first = first;
            this.second = second;
        }


        @Override
        public void run() {
            synchronized(this.first) {
                System.out.println("Begin run method");

                try {
                    sleep(3000);
                }
                catch(InterruptedException exception) {}

                synchronized(this.second) {
                    System.out.println("No deadlock");
                }
            }
        }


        public static void main(String[] argss) {
            Integer firstNumber = 5;
            Integer secondNumber = 10;

            Deadlock firstThread = new Deadlock(firstNumber, secondNumber);
            Deadlock secondThread = new Deadlock(secondNumber, firstNumber);

            firstThread.start();
            secondThread.start();
        }

    }
//