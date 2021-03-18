package Lesson1;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] words = {"apple", "orange", "lemon", "banana",
                "apricot", "avocado", "broccoli", "carrot",
                "cherry", "garlic", "grape", "melon", "leak",
                "kiwi", "mango", "mushroom", "nut", "olive", "pea",
                "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};

        Random random = new Random();
        StringBuilder result;
        System.out.println(Arrays.toString(words));
        String randomWord = words[random.nextInt(words.length)].toLowerCase();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Угадайте слово: ");
        String scanWord;
        do {
            result = new StringBuilder("###############");
            scanWord = scanner.nextLine().toLowerCase();
            int shortWords = Math.min(randomWord.length(), scanWord.length());
            for (int i = 0; i < shortWords; i++) {
                if (scanWord.charAt(i) == randomWord.charAt(i)) {
                    result.setCharAt(i,scanWord.charAt(i));
                }
            }
            System.out.println(result);
            if (scanWord.equals(randomWord)){
                System.out.println("Слово " + randomWord + " угадано");
                break;
            }
            System.out.println("Попробуйте сново");
            System.out.println(Arrays.toString(words));
        }
        while (true);


    }

}
