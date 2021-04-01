package Lesson1;

public class Cat {
    private  String name;
    private  int appetite;
    private boolean satiety;

    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
        this.satiety = false;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAppetite() {
        return appetite;
    }

    public void setAppetite(int appetite) {
        this.appetite = appetite;
    }

    public boolean isSatiety() {
        return satiety;
    }

    public void setSatiety(boolean satiety) {
        this.satiety = satiety;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", appetite=" + appetite +
                '}';
    }

    public void eat(Bowl bowl){
        if (bowl.getAmount() > 0 && bowl.getAmount() < appetite){
            System.out.printf("Коту %s мало еды. Сытость: %b\n", this.name, satiety);
            return;
        }
        else{
            bowl.decrease(appetite);
            satiety = true;
            System.out.printf("Кот %s поел. Сытость: %b\n", this.name , satiety);
        }
    }
}
