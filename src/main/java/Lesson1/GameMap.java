package Lesson1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GameMap extends JPanel {
    public static final int MODE_VS_AI = 0;
    public static final int MODE_VS_HUMAN = 1;
    private static final Random RANDOM = new Random();
    private static final int DOT_EMPTY = 0;
    private static final int DOT_HUMAN = 1;
    private static final int DOT_AI = 2;
    private static final int DOT_PADDING = 7;
    private static final int STATE_DRAW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;

    private int stateGameOver;
    private int gameMode;

    private int[][] field;
    private int fieldSizeX;
    private int fieldSizeY;
    private int winLength;
    private int cellWidth;
    private int cellHeight;
    private boolean isGameOver;
    private boolean initialized;

    public GameMap() {
        gameMode = SettingsWindow.getGameMode();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (gameMode == MODE_VS_AI){
                    modeAI(e);
                }
                else if (gameMode == MODE_VS_HUMAN){
                    modeHuman(e);
                }
            }
        });
        initialized = false;
    }

    /*private void update(MouseEvent e) {
        if (isGameOver || !initialized) return;

        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellWidth;
        if (!isCellValid(cellY, cellX) || !isCellEmpty(cellY, cellX)) return;
        field[cellY][cellX] = DOT_HUMAN;
        repaint();
        if (gameCheck(DOT_HUMAN, STATE_WIN_HUMAN)) return;
        aiTurn();
        repaint();
        if (gameCheck(DOT_AI, STATE_WIN_AI)) return;
    }*/

    private void modeAI(MouseEvent e){
        if (isGameOver || !initialized) return;

        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellWidth;
        if (!isCellValid(cellY, cellX) || !isCellEmpty(cellY, cellX)) return;
        field[cellY][cellX] = DOT_HUMAN;
        repaint();
        if (gameCheck(DOT_HUMAN, STATE_WIN_HUMAN)) return;
        aiTurn();
        repaint();
        if (gameCheck(DOT_AI, STATE_WIN_AI)) return;
    }

    private void modeHuman(MouseEvent e){
        if (isGameOver || !initialized) return;

        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellWidth;
        if (!isCellValid(cellY, cellX) || !isCellEmpty(cellY, cellX)) return;
        field[cellY][cellX] = DOT_HUMAN;
        repaint();
        if (gameCheck(DOT_HUMAN, STATE_WIN_HUMAN)) return;
        field[cellY][cellX] = DOT_AI;
        repaint();
        if (gameCheck(DOT_AI, STATE_WIN_AI)) return;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        if (!initialized) return;
        int width = getWidth();
        int height = getHeight();
        cellWidth = width / fieldSizeX;
        cellHeight = height / fieldSizeY;
        g.setColor(Color.BLACK);

        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, width, y);
        }
        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, height);
        }

        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(x, y)) continue;
                if (field[y][x] == DOT_HUMAN) {
                    g.setColor(new Color(1, 1, 255));
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else if (field[y][x] == DOT_AI) {
                    g.setColor(Color.RED);
                    g.fillRect(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else {
                    throw new RuntimeException("Something wrong with coordinates");
                }
            }
        }

        if (isGameOver) {
            showMessageGameOver(g);
        }
    }

    public void startNewGame(int gameMode, int fieldSize, int winLength) {
        this.fieldSizeX = fieldSize;
        this.fieldSizeY = fieldSize;
        this.winLength = winLength;
        field = new int[fieldSizeY][fieldSizeX];
        initialized = true;
        isGameOver = false;

        repaint();
        System.out.printf("New game with: %dx%d sized field, mode: %d and win length %d", fieldSize, fieldSize, gameMode, winLength);
    }

    private void showMessageGameOver(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,250, getWidth(), 100);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("TimesNewRoman", Font.BOLD, 60));
        switch (stateGameOver) {
            case STATE_DRAW:
                g.drawString("DRAW", 150, getHeight() / 2 + 25);
                break;
            case STATE_WIN_HUMAN:
                g.drawString("HUMAN Wins!", 150, getHeight()/ 2 + 25);
                break;
            case STATE_WIN_AI:
                g.drawString("AI Wins!", 150, getHeight()/ 2 + 25);
                break;
        }
    }

    private boolean gameCheck(int dot, int stateGameOver) {
        if (checkWin(dot, winLength)) {
            this.stateGameOver = stateGameOver;
            isGameOver = true;
            repaint();
            return true;
        }
        if (checkDraw()) {
            this.stateGameOver = STATE_DRAW;
            isGameOver = true;
            repaint();
            return true;
        }
        return false;
    }

    private void aiTurn() {
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

    private int[] aiSearchPosition() {
        int[][] mapPosition = new int[fieldSizeX][fieldSizeY];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                mapPosition[i][j] = 0;
            }
        }

        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                //Проверка 1 победной линии
                if((i+1-winLength) >= 0 && (j-1+winLength) < fieldSizeY) {
                    for (int k = 0; k < winLength; k++) {
                        if(field[i-k][j+k] == DOT_HUMAN) break;
                        if ((k+1) == winLength) {
                            for (int l = 0; l < winLength; l++) {
                                if(field[i-l][j+l] == DOT_EMPTY) {
                                    mapPosition[i-l][j+l] ++;
                                }
                            }
                        }
                    }
                }
                //Проверка второй победной линии
                if((j-1+winLength) < fieldSizeX) {
                    for (int k = 0; k < winLength; k++) {
                        if(field[i][j+k] == DOT_HUMAN) break;
                        if ((k+1) == winLength) {
                            for (int l = 0; l < winLength; l++) {
                                if(field[i][j+l] == DOT_EMPTY){
                                    mapPosition[i][j+l] ++;
                                }
                            }
                        }
                    }
                }
                //Проверка третьей победной линии
                if((i-1+winLength) < fieldSizeY && (j-1+winLength) < fieldSizeX) {
                    for (int k = 0; k < winLength; k++) {
                        if(field[i+k][j+k] == DOT_HUMAN) break;
                        if ((k+1) == winLength) {
                            for (int l = 0; l < winLength; l++) {
                                if(field[i+l][j+l] == DOT_EMPTY) {
                                    mapPosition[i+l][j+l] ++;
                                }
                            }
                        }
                    }
                }
                //Проверка четвертой победной линии
                if((i-1+winLength) < fieldSizeY) {
                    for (int k = 0; k < winLength; k++) {
                        if(field[i+k][j] == DOT_HUMAN) break;
                        if ((k+1) == winLength) {
                            for (int l = 0; l < winLength; l++) {
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

    private  int[] aiSearchWin(int c) {
        int[] coordBlock = new int[]{0,0,0};
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                //Проверка 1 победной линии
                if((i+1-winLength) >= 0 && (j-1+winLength) < fieldSizeX) {
                    int skip = 0;
                    int cordSkipX = 0;
                    int cordSkipY = 0;
                    for (int k = 0; k < winLength; k++) {
                        if(field[i-k][j+k] != c && (skip == 1 || field[i-k][j+k] != DOT_EMPTY)) {
                            break;
                        } else {
                            if(field[i-k][j+k] == DOT_EMPTY) {
                                skip++;
                                cordSkipX = i - k;
                                cordSkipY = j + k;
                            }
                        }
                        if ((k+1) == winLength && skip == 1) {
                            return coordBlock = new int[]{1,cordSkipX,cordSkipY};
                        }
                    }
                }
                //Проверка второй победной линии
                if((j-1+winLength) < fieldSizeX) {
                    int skip = 0;
                    int cordSkipX = 0;
                    int cordSkipY = 0;
                    for (int k = 0; k < winLength; k++) {
                        if(field[i][j+k] != c && (skip == 1 || field[i][j+k] != DOT_EMPTY)) {
                            break;
                        } else {
                            if(field[i][j+k] == DOT_EMPTY) {
                                skip++;
                                cordSkipX = i;
                                cordSkipY = j+k;
                            }
                        }
                        if ((k+1) == winLength && skip==1) {

                            return coordBlock = new int[]{1,cordSkipX,cordSkipY};
                        }
                    }
                }
                //Проверка третьей победной линии
                if((i-1+winLength) < fieldSizeY && (j-1+winLength) < fieldSizeX) {
                    int skip = 0;
                    int cordSkipX = 0;
                    int cordSkipY = 0;
                    for (int k = 0; k < winLength; k++) {
                        if(field[i+k][j+k] != c && (skip == 1 || field[i+k][j+k] != DOT_EMPTY)) {
                            break;
                        } else {
                            if(field[i+k][j+k] == DOT_EMPTY) {
                                skip++;
                                cordSkipX = i + k;
                                cordSkipY = j + k;
                            }
                        }
                        if ((k+1) == winLength && skip==1) {
                            return coordBlock = new int[]{1,cordSkipX,cordSkipY};
                        }
                    }
                }
                //Проверка четвертой победной линии
                if((i-1+winLength) < fieldSizeY) {
                    int skip = 0;
                    int cordSkipX = 0;
                    int cordSkipY = 0;
                    for (int k = 0; k < winLength; k++) {
                        if(field[i+k][j] != c && (skip == 1 || field[i+k][j] != DOT_EMPTY)) {
                            break;
                        } else {
                            if(field[i+k][j] == DOT_EMPTY) {
                                skip++;
                                cordSkipX = i + k;
                                cordSkipY = j;
                            }
                        }
                        if ((k+1) == winLength && skip==1) {
                            return coordBlock = new int[]{1,cordSkipX,cordSkipY};
                        }
                    }
                }
            }
        }
        return coordBlock;
    }


    private boolean checkWin(int c, int len) {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (checkLine(x, y, 1, 0, len, c)) return true; //Проверка горизонталь +х
                if (checkLine(x, y, 1, 1, len, c)) return true; //проверка диагонали +х +у
                if (checkLine(x, y, 0, 1, len, c)) return true; //проверка вертикали +у
                if (checkLine(x, y, 1, -1, len, c)) return true; //проверка диагонали +х -у
            }
        }
        return false;
    }

    private boolean checkLine(int x, int y, int incrementX, int incrementY, int len, int dot) {
        int endXLine = x + (len - 1) * incrementX; //Конец линии по x
        int endYLine = y + (len - 1) * incrementY; //Конец линии по y
        if (!isCellValid(endXLine, endYLine)) return false;
        for (int i = 0; i < len; i++) {
            if (field[y + i * incrementY][x + i * incrementX] != dot) return false;
        }
        return true;
    }

    private boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    private boolean isCellValid(int x, int y) {
        return x >= 0 && y >= 0 && x < fieldSizeX && y < fieldSizeY;
    }

    private boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }
}