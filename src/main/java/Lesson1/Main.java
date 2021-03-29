package Lesson1;

import Lesson1.animal.Animal;

public class Main {
    public static void main(String[] args) {

        Dog dog = new Dog("Bob");
        dog.run(300);
        dog.swim(5);

        Cat cat = new Cat("bob2");
        cat.swim(500);
        cat.run(300);

        Cat cat1 = new Cat("bob3");
        cat1.swim(500);
        cat1.run(150);


        System.out.printf("Котов: %d\nСобак:%d\nЖивотных%d", Cat.getCatCount(), Dog.getDogCount(),Animal.getCount());


    }
}
