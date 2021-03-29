package Lesson1;

import Lesson1.animal.Animal;

public class Dog extends Animal {
    private static int dogCount = 0;

    public Dog(String name) {
        super(name);
        dogCount++;
    }

    public static int getDogCount() {
        return dogCount;
    }

    @Override
    public void run(int length) {
        if (length <= 500) {
            super.run(length);
        }
        else {
            System.out.printf("%s не может пробежать больше 500м\n",name);
        }
    }

    @Override
    public void swim(int length) {
        if (length <= 10) {
            super.swim(length);
        }
        else {
            System.out.printf("%s не может проплыть больше 10м\n",name);
        }
    }

}
