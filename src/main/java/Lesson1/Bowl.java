package Lesson1;

public class Bowl {
    private int amount;

    public void put(int amount) {
        this.amount += amount;
    }
    public void decrease(int amount) {
        this.amount -= amount;
    }

    public int getAmount() {
        return amount;
    }
}
