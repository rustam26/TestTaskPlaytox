import org.apache.log4j.Logger;

import java.util.Random;

public class Main {
    final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        Account account1 = new Account(String.valueOf(random.nextInt(1000)), 10000);
        Account account2 = new Account(String.valueOf(random.nextInt(1000)), 10000);
        Account account3 = new Account(String.valueOf(random.nextInt(1000)), 10000);
        Account account4 = new Account(String.valueOf(random.nextInt(1000)), 10000);


        MyThread thread1 = new MyThread(account2, account1, random.nextInt(500));
        MyThread thread2 = new MyThread(account1, account2, random.nextInt(1000));
        MyThread thread3 = new MyThread(account3, account4, random.nextInt(100));
        MyThread thread4 = new MyThread(account4, account3, random.nextInt(110));



        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();


    }

}
