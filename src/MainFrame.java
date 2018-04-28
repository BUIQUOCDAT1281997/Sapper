import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements GameListener {

    private MenuGame menuGame;
    private Board board;

    private ActionBoardListener acb;

    private JLabel countFlag;


    public void setCountFlag(int flag) {
        countFlag.setText(String.format("%03d", flag));
    }

    public void setAcb(ActionBoardListener acb){
        this.acb=acb;
    }

    private JLabel timePlay;

    private int thoigianchoi = 0;
    private int phut = 0;
    private int giay = 0;
    private Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            thoigianchoi++;
            phut = thoigianchoi / 60;
            giay = thoigianchoi % 60;
            timePlay.setText(String.format("%02d", phut) + ":" + String.format("%02d", giay));
        }
    });

    public MainFrame(MenuGame menuGame, Board board) {
        super();
        timer.start();
        this.setTitle("Minesweeper");
        this.menuGame = menuGame;
        this.board = board;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new CardLayout(10, 10));

        this.add(menuGame, "MENUGAME");

        JPanel panelWrapBoard = new JPanel(new BorderLayout());
        panelWrapBoard.setBackground(new Color(48,56,58));
        JPanel headJPanel = new JPanel();
        headJPanel.setBorder(new LineBorder(new Color(48,56,58),2));
        headJPanel.setLayout(new BorderLayout());
        headJPanel.setBackground(new Color(249, 175, 57));
        panelWrapBoard.add(headJPanel, BorderLayout.NORTH);

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel1.setBackground(new Color(249, 175, 57));
        JButton menuJButton = new JButton("Menu");
        JButton restartJButton = new JButton("Restart");
        ActionListener ali = e -> {
            if (e.getSource() == menuJButton) {
                this.showMenuGame();
            } else {
                acb.clickRestart();
            }
        };
        menuJButton.addActionListener(ali);
        restartJButton.addActionListener(ali);
        jPanel1.add(menuJButton);
        jPanel1.add(restartJButton);
        headJPanel.add(jPanel1, BorderLayout.WEST);

        JPanel timeAndFlag = new JPanel();
        timeAndFlag.setLayout(new FlowLayout(FlowLayout.RIGHT));
        timeAndFlag.setBackground(new Color(249, 175, 57));
        countFlag = new JLabel();
        countFlag.setIcon(new ImageIcon(Icons.getFlag().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        countFlag.setText("0");
        timePlay = new JLabel();
        timePlay.setIcon(new ImageIcon(Icons.getTimer().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        timePlay.setText("00:00");
        timeAndFlag.add(countFlag);
        timeAndFlag.add(timePlay);
        headJPanel.add(timeAndFlag, BorderLayout.EAST);


        panelWrapBoard.add(board, BorderLayout.CENTER);

        this.add(panelWrapBoard, "BOARD");

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);

    }

    public void showMenuGame() {

        CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "MENUGAME");
        board.setPreferredSize(new Dimension(200, 150));
        this.pack();
    }

    private void showBoardGame() {
        CardLayout card = (CardLayout) getContentPane().getLayout();
        card.show(getContentPane(), "BOARD");
    }

    private void setTimePlay() {
        phut = 0;
        giay = 0;
        thoigianchoi = 0;
        timePlay.setText("00:00");
    }

    private int showGameOverLose() {
        return JOptionPane.showOptionDialog(this, "Chơi ngu vcl", "DIALOG",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new String[]{"Restart", "Menu"}, "default");
    }

    private int showGameOverWin() {
        return JOptionPane.showOptionDialog(this,
                "You Win\nTimePlay: " + String.format("%02d",phut) + ":" + String.format("%02d",giay), "Winer",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, new String[]{"Restart", "Menu"}, "default");
    }

    @Override
    public void onRestartGame() {
        showBoardGame();
        pack();
        setTimePlay();
        repaint();
        timer.start();
    }

    @Override
    public int onGameOver(boolean playerWin) {
        timer.stop();
        repaint();
        if (playerWin) {
            return showGameOverWin();
        } else {
            return showGameOverLose();
        }
    }
}