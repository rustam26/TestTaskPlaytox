public class Account {

    private String ID;
    private volatile int money;

    public Account(String ID, int money) {
        this.ID = ID;
        this.money = money;
    }

    public synchronized void debit(int money){
        this.money = this.money - money;
    }

    public synchronized void credit(int money){
        this.money = this.money + money;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "ID='" + ID + '\'' +
                ", money=" + money +
                '}';
    }
}
