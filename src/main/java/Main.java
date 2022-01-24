import java.util.Random;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        Account account1 = new Account(String.valueOf(random.nextInt(10)), 10000);
        Account account2 = new Account(String.valueOf(random.nextInt(10)), 10000);
        Account account3 = new Account(String.valueOf(random.nextInt(10)), 10000);
        Account account4 = new Account(String.valueOf(random.nextInt(10)), 10000);


        MyThread thread1 = new MyThread(account2, account1, 1);
        MyThread thread2 = new MyThread(account1, account2, 1000);
        MyThread thread3 = new MyThread(account1, account2, 1000);
        MyThread thread4 = new MyThread(account2, account4, 1000);
        MyThread thread5 = new MyThread(account2, account4, 1000);
        MyThread thread6 = new MyThread(account3, account2, 1000);


        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();


        thread1.join();
        thread2.join();
        thread3.join();


        System.out.println(account1.getMoney());
        System.out.println(account2.getMoney());
        System.out.println(account3.getMoney());
        System.out.println(account4.getMoney());
    }

}

class MyThread extends Thread {

    static volatile int count = 0;
    Account fromAcct;
    Account toAcct;
    int money;

    public MyThread(Account fromAcct, Account toAcct, int money) {
        this.fromAcct = fromAcct;
        this.toAcct = toAcct;
        this.money = money;
    }

    @Override
    public void run() {


        while (count <= 30 ) {
            try {
                Thread.sleep(1000);
                countAdd();
                System.out.println(count);
                transferMoney(fromAcct, toAcct, money);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }

    private  void doTransfer(Account fromAcct, Account toAcct, int money) {
        if (fromAcct.getMoney() == 0 || toAcct.getMoney() == 0) {

        } else {
            fromAcct.debit(money);
            toAcct.credit(money);

        }
    }

    public  void transferMoney(Account fromAcct, Account toAcct, int money) {
        String fromId = fromAcct.getID();
        String toId = toAcct.getID();
        if (fromId.compareTo(toId) < 0) {
            synchronized (fromAcct) {
                synchronized (toAcct) {
                    doTransfer(fromAcct, toAcct, money);
                }
            }
        } else {
            synchronized (toAcct) {
                synchronized (fromAcct) {
                    doTransfer(fromAcct, toAcct, money);
                }
            }
        }

    }

    private void countAdd(){
        synchronized (this){
            count++;
        }
    }
}

