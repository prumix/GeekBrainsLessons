package Lesson1;

import java.util.*;

public class Main {
    private static final Random RANDOM = new Random();
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '.';
    private static int chips = 4;

    private static char[][] field;
    private static int fieldSizeX = 5;
    private static int fieldSizeY = 5;
    private static int winX;
    private static int winY;

    private static String playerOneName = "";


    private static int scoreHuman = 0;
    private static int scoreAI = 0;


    public static void main(String[] args) {
        System.out.print("Представьтесь пожалуйста >>> ");
        playerOneName = SCANNER.nextLine();
        while (true) {
            initField();
            printField();

            while (true) {
                humanTurn();
                printField();
                if (gameCheck(DOT_HUMAN, String.format("%s, вы великолепны! Победа!", playerOneName))) break;
                aiTurn();
                printField();
                if (gameCheck(DOT_AI, "Компьютер победил!")) break;
            }//%s - строка, %d - целое число, %f вещественное число, %c символ %b булево
            System.out.printf("SCORE IS:\n%s: %d || AI: %d\n", playerOneName, scoreHuman, scoreAI);
            System.out.println("Wanna play again? >>> Y or N >>");
            if (!SCANNER.next().toLowerCase().equals("y")) break;
        }
    }


    private static boolean gameCheck(char dot, String s) {
        if (checkWin(dot)) {
            if (dot == DOT_HUMAN) {
                scoreHuman++;
            } else {
                scoreAI++;
            }
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("DRAW!!!");
            return true;
        }
        return false;
    }


    private static boolean checkWin(char c) {
        for (int y = 0; y < fieldSizeY - chips + 1; y++) {
            for (int x = 0; x < fieldSizeX - chips + 1; x++) {
                if (checkDiagonal(c, x, y) || checkLine(c, x, y)) return true;
            }
        }
        return false;
    }

    private static boolean checkDiagonal(char c, int x, int y) {
        boolean toleft = true;
        boolean toright = true;
        for (int i = 0; i < chips; i++) {
            toright &= (field[i + x][i + y] == c);
            toleft &= (field[chips - i - 1 + x][i + y] == c);
        }
        if (toright || toleft) return true;
        return false;
    }

    private static boolean checkLine(char c, int x, int y) {
        boolean column;
        boolean row;
        for (int col = x; col < chips + x; col++) {
            column = true;
            row = true;
            for (int rows = y; rows < chips + y; rows++) {
                column &= (field[col][rows] == c);
                row &= (field[rows][col] == c);
            }

            if (column || row) return true;
        }
        return false;
    }


    private static boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    private static void humanTurn() {
        int x, y;
        do {
            System.out.printf("%s введите координаты х и у через пробел >>>>>", playerOneName);
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));

        field[y][x] = DOT_HUMAN;
    }


    public static void aiTurn() {
        int x;
        int y;

        int[] searchWin = aiSearchWin(DOT_AI);
        int[] searchBlock = aiSearchWin(DOT_HUMAN);
        int[] searchPosition = aiSearchPosition();
        if(searchWin[0]==1) {
            y = searchWin[1];
            x = searchWin[2];
        } else
        if(searchBlock[0]==1) {
            y = searchBlock[1];
            x = searchBlock[2];
        } else
        if(searchPosition[0]>=1) {
            y = searchPosition[1];
            x = searchPosition[2];
        } else
            do {
                y = RANDOM.nextInt(fieldSizeY);
                x = RANDOM.nextInt(fieldSizeX);
            } while (!isCellValid(y, x));
        field[y][x] = DOT_AI;

    }

    private   static int[] aiSearchPosition() {
        int[][] mapPosition = new int[fieldSizeX][fieldSizeY];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                mapPosition[i][j] = 0;
            }
        }

        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                //Проверка 1 победной линии
                if((i+1-chips) >= 0 && (j-1+chips) < fieldSizeY) {
                    for (int k = 0; k < chips; k++) {
                        if(field[i-k][j+k] == DOT_HUMAN) break;
                        if ((k+1) == chips) {
                            for (int l = 0; l < chips; l++) {
                                if(field[i-l][j+l] == DOT_EMPTY) {
                                    mapPosition[i-l][j+l] ++;
                                }
                            }
                        }
                    }
                }
                //Проверка второй победной линии
                if((j-1+chips) < fieldSizeX) {
                    for (int k = 0; k < chips; k++) {
                        if(field[i][j+k] == DOT_HUMAN) break;
                        if ((k+1) == chips) {
                            for (int l = 0; l < chips; l++) {
                                if(field[i][j+l] == DOT_EMPTY){
                                    mapPosition[i][j+l] ++;
                                }
                            }
                        }
                    }
                }
                //Проверка третьей победной линии
                if((i-1+chips) < fieldSizeY && (j-1+chips) < fieldSizeX) {
                    for (int k = 0; k < chips; k++) {
                        if(field[i+k][j+k] == DOT_HUMAN) break;
                        if ((k+1) == chips) {
                            for (int l = 0; l < chips; l++) {
                                if(field[i+l][j+l] == DOT_EMPTY) {
                                    mapPosition[i+l][j+l] ++;
                                }
                            }
                        }
                    }
                }
                //Проверка четвертой победной линии
                if((i-1+chips) < fieldSizeY) {
                    for (int k = 0; k < chips; k++) {
                        if(field[i+k][j] == DOT_HUMAN) break;
                        if ((k+1) == chips) {
                            for (int l = 0; l < chips; l++) {
                                if(field[i+l][j] == DOT_EMPTY) {
                                    mapPosition[i+l][j] ++;
                                }
                            }
                        }
                    }
                }
            }
        }

        int[] searchPosition = new int[]{0,0,0};

        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (mapPosition[i][j] > searchPosition[0]) {
                    searchPosition = new int[]{mapPosition[i][j],i,j};
                }
            }
        }

        return searchPosition;
    }

    private static int[] aiSearchWin(char c) {
        int[] coordBlock = new int[]{0,0,0};
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                //Проверка 1 победной линии
                if((i+1-chips) >= 0 && (j-1+chips) < fieldSizeX) {
                    int skip = 0;
                    int cordSkipX = 0;
                    int cordSkipY = 0;
                    for (int k = 0; k < chips; k++) {
                        if(field[i-k][j+k] != c && (skip == 1 || field[i-k][j+k] != DOT_EMPTY)) {
                            break;
                        } else {
                            if(field[i-k][j+k] == DOT_EMPTY) {
                                skip++;
                                cordSkipX = i - k;
                                cordSkipY = j + k;
                            }
                        }
                        if ((k+1) == chips && skip == 1) {
                            return coordBlock = new int[]{1,cordSkipX,cordSkipY};
                        }
                    }
                }
                //Проверка второй победной линии
                if((j-1+chips) < fieldSizeX) {
                    int skip = 0;
                    int cordSkipX = 0;
                    int cordSkipY = 0;
                    for (int k = 0; k < chips; k++) {
                        if(field[i][j+k] != c && (skip == 1 || field[i][j+k] != DOT_EMPTY)) {
                            break;
                        } else {
                            if(field[i][j+k] == DOT_EMPTY) {
                                skip++;
                                cordSkipX = i;
                                cordSkipY = j+k;
                            }
                        }
                        if ((k+1) == chips && skip==1) {

                            return coordBlock = new int[]{1,cordSkipX,cordSkipY};
                        }
                    }
                }
                //Проверка третьей победной линии
                if((i-1+chips) < fieldSizeY && (j-1+chips) < fieldSizeX) {
                    int skip = 0;
                    int cordSkipX = 0;
                    int cordSkipY = 0;
                    for (int k = 0; k < chips; k++) {
                        if(field[i+k][j+k] != c && (skip == 1 || field[i+k][j+k] != DOT_EMPTY)) {
                            break;
                        } else {
                            if(field[i+k][j+k] == DOT_EMPTY) {
                                skip++;
                                cordSkipX = i + k;
                                cordSkipY = j + k;
                            }
                        }
                        if ((k+1) == chips && skip==1) {
                            return coordBlock = new int[]{1,cordSkipX,cordSkipY};
                        }
                    }
                }
                //Проверка четвертой победной линии
                if((i-1+chips) < fieldSizeY) {
                    int skip = 0;
                    int cordSkipX = 0;
                    int cordSkipY = 0;
                    for (int k = 0; k < chips; k++) {
                        if(field[i+k][j] != c && (skip == 1 || field[i+k][j] != DOT_EMPTY)) {
                            break;
                        } else {
                            if(field[i+k][j] == DOT_EMPTY) {
                                skip++;
                                cordSkipX = i + k;
                                cordSkipY = j;
                            }
                        }
                        if ((k+1) == chips && skip==1) {
                            return coordBlock = new int[]{1,cordSkipX,cordSkipY};
                        }
                    }
                }
            }
        }
        return coordBlock;
    }


    private static boolean isCellValid(int x, int y) {
        return x >= 0 && y >= 0 && x < fieldSizeX && y < fieldSizeY;
    }

    private static boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }


    private static void initField() {
        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++)
            System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
        System.out.println();

        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeX; j++)
                System.out.print(field[i][j] + "|");
            System.out.println();
        }

        for (int i = 0; i <= fieldSizeX * 2 + 1; i++)
            System.out.print("-");
        System.out.println();
    }
}



