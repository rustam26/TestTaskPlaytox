import org.apache.log4j.Logger;

import java.util.Random;

class MyThread extends Thread {

    final static Logger logger = Logger.getLogger(MyThread.class);

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

        while (true) {
            try {
                Thread.sleep(new Random().nextInt(2000 - 1000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count <= 30) {

                transferMoney(fromAcct, toAcct, money);
                countAdd();

            } else {
                break;
            }
        }
    }

    private void doTransfer(Account fromAcct, Account toAcct, int money) {

        if (fromAcct.getMoney() > 0 && money > 0 && money <= fromAcct.getMoney()) {
            fromAcct.debit(money);
            toAcct.credit(money);
            logger.info("Из счета " + fromAcct.getID() + " сделали операцию списания, остаток: " + fromAcct.getMoney() + "." +
                    " И на счет " + toAcct.getID() + " сделали операцию зачисление, остаток: " + toAcct.getMoney() + "." +
                    " Сумма перевода: " + money +
                    " Операция: " + count);
        }

    }

    public void transferMoney(Account fromAcct, Account toAcct, int money) {
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

    private void countAdd() {
        synchronized (this) {
            count++;
        }
    }
}


