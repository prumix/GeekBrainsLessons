package Lesson1;

import Lesson1.animal.Animal;

public class Cat extends Animal {
    private static int catCount = 0;

    public Cat(String name) {
        super(name);
        catCount++;
    }

    public static int getCatCount() {
        return catCount;
    }

    @Override
    public void run(int length) {
        if (length <= 200) {
            super.run(length);
        }
        else {
            System.out.printf("%s не может пробежать больше 200м\n", name);
        }
    }

    @Override
    public void swim(int length) {
        System.out.printf("%s не умеет плавать\n", name);
    }


}
