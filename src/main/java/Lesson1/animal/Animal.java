package Lesson1.animal;

public class Animal {
    protected String name;
    protected static int count = 0;

    public Animal(String name) {
        this.name = name;
        count++;
    }

    public static int getCount() {
        return count;
    }

    public void run(int length) {
        System.out.printf("%s пробежал %dm.\n", name, length);
    }

    public void swim(int length){
        System.out.printf("%s проплыл %dm.\n", name, length);
    }


}
