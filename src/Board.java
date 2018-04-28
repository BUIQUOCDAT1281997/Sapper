import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;

public class Board extends JPanel implements MouseMotionListener, MouseListener, GameListener {
    private static final int SIZE_CELL_1 = 20;
    private static final int SIZE_CELL_2 = (int) (SIZE_CELL_1 * Math.sqrt(3));
    private static final Color COLOR_CELL_NOT_OPEN = new Color(249, 175, 57);
    private static final Color COLOR_CELL_OPEN = new Color(11, 164, 195);

    private GeneralPath generalPath;


    private void patinGeneralPath(double x, double y, Graphics2D gr2d, Color color) {
        generalPath = new GeneralPath();

        generalPath.moveTo(x, y);
        generalPath.lineTo(x, y + SIZE_CELL_1);
        generalPath.lineTo(x + SIZE_CELL_2 / 2, y + 1.5 * SIZE_CELL_1);
        generalPath.lineTo(x + SIZE_CELL_2, y + SIZE_CELL_1);
        generalPath.lineTo(x + SIZE_CELL_2, y);
        generalPath.lineTo(x + SIZE_CELL_2 / 2, y - SIZE_CELL_1 / 2);
        generalPath.closePath();
        gr2d.setColor(color);
        gr2d.fill(generalPath);
        gr2d.setColor(new Color(220, 236, 240));
        gr2d.draw(generalPath);
    }

    private Cell[][] stateBoard = null;


    private ActionBoardListener actionBoardListener;

    private Image[] imagesNums = null;

    private boolean isEndGame = false;

    public Board() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        imagesNums = Icons.getArrayNum();
    }

    public void setStateBoard(Cell[][] stateBoard) {
        this.stateBoard = stateBoard;

        int widthBoard =  stateBoard.length * SIZE_CELL_2 + SIZE_CELL_1;

        this.setPreferredSize(new Dimension(widthBoard, 3*SIZE_CELL_1*stateBoard.length/2+SIZE_CELL_1/2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // рисовать клетки
        for (int i = 0; i < stateBoard.length; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < stateBoard[i].length; j++) {
                    int xFrom = (SIZE_CELL_2 * j);
                    int yFrom = (3 * SIZE_CELL_1) * i / 2 + SIZE_CELL_1 / 2;
                    patinGeneralPath(xFrom, yFrom, g2, COLOR_CELL_NOT_OPEN);
                    if (stateBoard[i][j].getMark()) {
                        // рисовать флаги
                        g2.drawImage(Icons.getFlag(), xFrom, yFrom, SIZE_CELL_2, SIZE_CELL_1, null);
                    } else if (stateBoard[i][j].getOpen() && !stateBoard[i][j].getBom()) {
                        // рисовать цвет клетк
                        patinGeneralPath(xFrom, yFrom, g2, COLOR_CELL_OPEN);
                        if (stateBoard[i][j].getValue() > 0) {
                            patinGeneralPath(xFrom, yFrom, g2, new Color(48, 56, 58));
                            g2.drawImage(imagesNums[stateBoard[i][j].getValue() - 1], xFrom, yFrom, SIZE_CELL_2, SIZE_CELL_1, null);
                        }
                    }
                }
            } else {
                for (int j = 0; j < stateBoard[i].length; j++) {
                    int xf = SIZE_CELL_2 * j + SIZE_CELL_2 / 2;
                    int yf = (int) ((1.5 * SIZE_CELL_1) * i + SIZE_CELL_1 / 2);
                    patinGeneralPath(xf, yf, g2, COLOR_CELL_NOT_OPEN);
                    if (stateBoard[i][j].getMark()) {
                        // рисовать флаги
                        g2.drawImage(Icons.getFlag(), xf, yf, SIZE_CELL_2, SIZE_CELL_1, null);
                    } else if (stateBoard[i][j].getOpen() && !stateBoard[i][j].getBom()) {
                        // рисовать цвет клетк
                        patinGeneralPath(xf, yf, g2, COLOR_CELL_OPEN);
                        if (stateBoard[i][j].getValue() > 0) {
                            patinGeneralPath(xf, yf, g2, new Color(48, 56, 58));
                            g2.drawImage(imagesNums[stateBoard[i][j].getValue() - 1], xf, yf, SIZE_CELL_2, SIZE_CELL_1, null);
                        }
                    }
                }
            }
        }


        // игра закончена
        if (isEndGame) {
            for (int i = 0; i < stateBoard.length; i++) {
                if (i % 2 == 0) {
                    for (int j = 0; j < stateBoard[i].length; j++) {
                        int xFrom = (int) (SIZE_CELL_2 * j);
                        int yFrom = (3 * SIZE_CELL_1) * i / 2 + SIZE_CELL_1 / 2;

                        if (stateBoard[i][j].getBom()) {
                            g2.drawImage(Icons.getMine(), xFrom, yFrom, SIZE_CELL_2, SIZE_CELL_1, null);
                        }
                    }
                } else {
                    for (int j = 0; j < stateBoard[i].length; j++) {
                        int xf = SIZE_CELL_2 * j + SIZE_CELL_2 / 2;
                        int yf = (3 * SIZE_CELL_1) * i / 2 + SIZE_CELL_1 / 2;
                        if (stateBoard[i][j].getBom()) {
                            g2.drawImage(Icons.getMine(), xf, yf, SIZE_CELL_2, SIZE_CELL_1, null);
                        }
                    }
                }
            }
        }
    }

    public void setActionBoardListener(ActionBoardListener actionBoardListener) {
        this.actionBoardListener = actionBoardListener;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int yCell = 2 * e.getY() / (3 * SIZE_CELL_1);
        int xCell;
        if (yCell % 2 == 0) {
            xCell = e.getX() / (SIZE_CELL_2);
        } else {
            xCell = (e.getX() - SIZE_CELL_2 / 2) / SIZE_CELL_2;
        }

        if (SwingUtilities.isRightMouseButton(e)) {
            actionBoardListener.onMarkCell(xCell, yCell);
        } else {
            actionBoardListener.onOpenCell(xCell, yCell);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void onRestartGame() {
        isEndGame = false;
    }

    @Override
    public int onGameOver(boolean playerWin) {
        this.isEndGame = true;
        repaint();
        return 0;
    }
}
