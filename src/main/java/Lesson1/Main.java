package Lesson1;


public class Main {
    public static void main(String[] args) {

        System.out.println(one(1, 2, 3, 4));
        System.out.println(two(5, 16));
        three(-5);
        System.out.println("");
        four("Don");
        System.out.println("");
        five(2020);

    }


    public static float one(float a, float b, float c, float d) {
        float e = a * (b + (c / d));
        return e;
    }

    public static boolean two(int a, int b) {
        int c = a + b;
        if (c >= 10 && c <= 20) {
            return true;
        }
        return false;
    }

    public static void three(int c) {
        if (c >= 0) {
            System.out.printf("Число %d положительное", c);
        } else {
            System.out.printf("Число %d отрицательное", c);
        }
    }


    public static void four(String s) {
        System.out.printf("Привет, %s!", s);
    }

    public static void five(int x) {
        if (x % 4 == 0 && x%100 !=0 || x%4 !=0){
            System.out.printf("Год %d високосный", x);
        }
        else {
            System.out.printf("Год %d невисокосный", x);
        }
    }
}
