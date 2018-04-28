import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class Game implements ChooseBoardListener, ActionBoardListener {

    private Cell[][] stateBoard = null;
    private int row, column, totalMine;

    private MainFrame mainFrame;
    private MenuGame menuGame;
    private Board board;

    private int countMark = totalMine;
    private int countCellOpen = 0;

    public Game() {
        menuGame = new MenuGame();
        menuGame.setChooseBoardListener(this);
        board = new Board();
        board.setActionBoardListener(this);

        mainFrame = new MainFrame(menuGame, board);
        mainFrame.setAcb(this);

        mainFrame.setVisible(true);
    }

    private void eatFreeCell(int x, int y) {
        if (x < 0 || x >= column || y < 0 || y >= row) {
            return;
        }
        stateBoard[y][x].setOpen(true);
        countCellOpen++;
        if (y % 2 == 0) {
            if (y - 1 >= 0 && stateBoard[y - 1][x].getBom()) {
                return;
            }
            if (y + 1 < row && stateBoard[y + 1][x].getBom()) {
                return;
            }
            if (x - 1 >= 0 && stateBoard[y][x - 1].getBom()) {
                return;
            }
            if (x + 1 < column && stateBoard[y][x + 1].getBom()) {
                return;
            }
            if (y - 1 >= 0 && x - 1 >= 0 && stateBoard[y - 1][x - 1].getBom()) {
                return;
            }
            if (y + 1 < row && x - 1 >= 0 && stateBoard[y + 1][x - 1].getBom()) {
                return;
            }

            if (y - 1 >= 0 && !stateBoard[y - 1][x].getOpen()) {
                eatFreeCell(x, y - 1);
            }
            if (y + 1 < row && !stateBoard[y + 1][x].getOpen()) {
                eatFreeCell(x, y + 1);
            }
            if (x - 1 >= 0 && !stateBoard[y][x - 1].getOpen()) {
                eatFreeCell(x - 1, y);
            }
            if (x + 1 < column && !stateBoard[y][x + 1].getOpen()) {
                eatFreeCell(x + 1, y);
            }
            if (x - 1 >= 0 && y - 1 >= 0 && !stateBoard[y - 1][x - 1].getOpen()) {
                eatFreeCell(x - 1, y - 1);
            }
            if (x - 1 >= 0 && y + 1 < row && !stateBoard[y + 1][x - 1].getOpen()) {
                eatFreeCell(x - 1, y + 1);
            }
        } else {
            if (y - 1 >= 0 && stateBoard[y - 1][x].getBom()) {
                return;
            }
            if (y + 1 < row && stateBoard[y + 1][x].getBom()) {
                return;
            }
            if (x - 1 >= 0 && stateBoard[y][x - 1].getBom()) {
                return;
            }
            if (x + 1 < column && stateBoard[y][x + 1].getBom()) {
                return;
            }

            if (y - 1 >= 0 && x + 1 < column && stateBoard[y - 1][x + 1].getBom()) {
                return;
            }
            if (y + 1 < row && x + 1 < column && stateBoard[y + 1][x + 1].getBom()) {
                return;
            }


            if (y - 1 >= 0 && !stateBoard[y - 1][x].getOpen()) {
                eatFreeCell(x, y - 1);
            }
            if (y + 1 < row && !stateBoard[y + 1][x].getOpen()) {
                eatFreeCell(x, y + 1);
            }
            if (x - 1 >= 0 && !stateBoard[y][x - 1].getOpen()) {
                eatFreeCell(x - 1, y);
            }
            if (x + 1 < column && !stateBoard[y][x + 1].getOpen()) {
                eatFreeCell(x + 1, y);
            }

            if (x + 1 < column && y - 1 >= 0 && !stateBoard[y - 1][x + 1].getOpen()) {
                eatFreeCell(x + 1, y - 1);
            }
            if (x + 1 < column && y + 1 < row && !stateBoard[y + 1][x + 1].getOpen()) {
                eatFreeCell(x + 1, y + 1);
            }
        }

    }

    @Override
    public void onOpenCell(int x, int y) {
        if (stateBoard[y][x].getMark() || stateBoard[y][x].getOpen()) {
            return;
        }
        if (stateBoard[y][x].getBom()) {
            board.onGameOver(false);
            if (mainFrame.onGameOver(false) == JOptionPane.OK_OPTION) {
                restartGame();
            } else {
                mainFrame.showMenuGame();
            }
        } else {
            eatFreeCell(x, y);
            // mo het o trong
            if (countCellOpen == column * row - totalMine) {
                board.onGameOver(true);
                if (mainFrame.onGameOver(true) == JOptionPane.OK_OPTION) {
                    restartGame();
                } else {
                    mainFrame.showMenuGame();
                }
            }
            board.repaint();
            mainFrame.repaint();
        }
    }

    @Override
    public void onMarkCell(int x, int y) {
        if (stateBoard[y][x].getOpen()) {
            return;
        }
        if (stateBoard[y][x].getMark()) {
            stateBoard[y][x].setMark(false);
            countMark++;
        } else if (countMark != 0) {
            stateBoard[y][x].setMark(true);
            countMark--;

            if (countMark < 0) {
                countMark = 0;
                return;
            }
        }
        board.repaint();
        mainFrame.setCountFlag(countMark);
        mainFrame.repaint();
    }


    private void createBoard(int row, int column, int mine) {
        stateBoard = new Cell[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                stateBoard[i][j] = new Cell();
            }
        }

        // (Random mines)
        int countMineRand = 0;
        Random random = new Random();
        while (countMineRand < mine) {
            int y = random.nextInt(row);
            int x = random.nextInt(column);
            if (!stateBoard[y][x].getBom()) {
                stateBoard[y][x].setBom(true);
                countMineRand++;
            }
        }
        // Установление значения для клетк
        for (int i = 0; i < row; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < column; j++) {
                    if (!stateBoard[i][j].getBom()) {
                        int count = 0;
                        if (i - 1 >= 0 && stateBoard[i - 1][j].getBom()) {
                            count++;
                        }
                        if (i + 1 < row && stateBoard[i + 1][j].getBom()) {
                            count++;
                        }
                        if (j - 1 >= 0 && stateBoard[i][j - 1].getBom()) {
                            count++;
                        }
                        if (j + 1 < column && stateBoard[i][j + 1].getBom()) {
                            count++;
                        }
                        if (i - 1 >= 0 && j - 1 >= 0 && stateBoard[i - 1][j - 1].getBom()) {
                            count++;
                        }

                        if (i + 1 < row && j - 1 >= 0 && stateBoard[i + 1][j - 1].getBom()) {
                            count++;
                        }

                        stateBoard[i][j].setValue(count);
                    }
                }
            } else {
                for (int j = 0; j < column; j++) {
                    if (!stateBoard[i][j].getBom()) {
                        int count = 0;
                        if (i - 1 >= 0 && stateBoard[i - 1][j].getBom()) {
                            count++;
                        }
                        if (i + 1 < row && stateBoard[i + 1][j].getBom()) {
                            count++;
                        }
                        if (j - 1 >= 0 && stateBoard[i][j - 1].getBom()) {
                            count++;
                        }
                        if (j + 1 < column && stateBoard[i][j + 1].getBom()) {
                            count++;
                        }

                        if (i - 1 >= 0 && j + 1 < column && stateBoard[i - 1][j + 1].getBom()) {
                            count++;
                        }

                        if (i + 1 < row && j + 1 < column && stateBoard[i + 1][j + 1].getBom()) {
                            count++;
                        }
                        stateBoard[i][j].setValue(count);
                    }
                }
            }
        }
    }

    private void restartGame() {
        createBoard(row, column, totalMine);
        countMark = totalMine;
        countCellOpen = 0;
        board.setStateBoard(stateBoard);
        board.onRestartGame();
        mainFrame.setCountFlag(totalMine);
        mainFrame.onRestartGame();

    }

    @Override
    public void clickRestart() {
        restartGame();
    }



    @Override
    public void chooseBoardPerformed(int row, int column, int mines) {
        this.row = row;
        this.column = column;
        this.totalMine = mines;
        restartGame();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        Icons.loadIcon();
        Game game = new Game();
    }
}
