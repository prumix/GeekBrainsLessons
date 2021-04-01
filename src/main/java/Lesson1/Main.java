package Lesson1;

public class Main {
    public static void main(String[] args) {
        Bowl bowl = new Bowl();
        bowl.put(100);
        Cat[] cats = {new Cat("Barsik", 20),
                new Cat("Tom", 25),
                new Cat("Fil", 30),
                new Cat("Bob", 35)
        };
        for (Cat cat : cats) {
            cat.eat(bowl);
        }
        System.out.printf("Еды в миске осталось %d\n", bowl.getAmount());
    }
}
