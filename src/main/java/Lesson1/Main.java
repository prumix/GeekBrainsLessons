package Lesson1;

public class Main {
    public static void main(String[] args) {
        invertArray();
        System.out.println();
        createArray();
        System.out.println();
        multiply();
        System.out.println();
        fillDiagonal(3);
        System.out.println();
        findMinMax(new int[]{1, 2, 3, 5, 7, 9, 2, 10, 50});
        System.out.println();
        System.out.println(checkBalance(new int[]{2, 2, 2, 1, 2, 2, 10, 1}));
        System.out.println(checkBalance(new int[]{1, 1, 1, 2, 1}));
        System.out.println(checkBalance(new int[]{10, 10, 1}));
        System.out.println();
        shiftArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 2);
        System.out.println();
        shiftArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, -2);
    }

    private static void invertArray() {
        int[] array = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                array[i] = 0;
            } else {
                array[i] = 1;
            }
        }
        for (int i : array) {
            System.out.print(i + " ");
        }
    }

    private static void createArray() {
        int[] array = new int[8];
        for (int i = 0, j = 0; i < array.length; i++, j += 3) {
            array[i] = j;
        }
        for (int i : array) {
            System.out.print(i + " ");
        }
    }

    private static void multiply() {
        int[] array = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 6) array[i] *= 2;
        }
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private static void fillDiagonal(int size) {
        int[][] array = new int[size][size];
        for (int i = 0; i < array.length; i++) {
            array[i][i] = 1;
            array[i][size - i - 1] = 1;
        }
        for (int[] ints : array) {
            for (int anInt : ints) {
                System.out.print(anInt);
            }
            System.out.println();
        }
    }

    private static void findMinMax(int[] array) {
        int min = array[0];
        int max = array[0];
        for (int j : array) {
            min = Math.min(min, j);
            max = Math.max(min, j);
        }
        System.out.println("Min element: " + min + "\n" + "Max element: " + max);
    }

    private static boolean checkBalance(int[] array) {
        if (array == null || array.length < 2) return false;
        int sumArray = 0, leftArray = 0;
        for (int i : array) {
            sumArray += i;
        }
        for (int i = 0; i < array.length - 1; i++) {
            leftArray += array[i];
            if (leftArray * 2 == sumArray) {
                wrightArrayWithBorder(array, i);
                return true;
            }
        }
        return false;
    }

    private static void wrightArrayWithBorder(int[] array, int border) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
            if (i == border) System.out.print("|| ");
        }
    }

    private static void shiftArray(int[] array, int n) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                int tmp = array[array.length - 1];
                for (int j = array.length - 1; j > 0; j--) {
                    array[j] = array[j - 1];
                }
                array[0] = tmp;
            }
        }
        if (n < 0) {
            for (int i = 0; i > n; i--) {
                int tmp = array[0];
                for (int j = 0; j < array.length - 1; j++) {
                    array[j] = array[j + 1];
                }
                array[array.length - 1] = tmp;
            }
        }
        System.out.println();
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

   /* private static void shiftArray(int[] array, int n) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                int tmp = array[array.length - 1];
                System.arraycopy(array, 0, array, 1, array.length - 1);
                array[0] = tmp;
            }
        }
        if (n < 0) {
            for (int i = 0; i > n; i--) {
                int tmp = array[0];
                System.arraycopy(array, 1, array, 0, array.length - 1);
                array[array.length - 1] = tmp;
            }
        }
        System.out.println();
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }*/

}
