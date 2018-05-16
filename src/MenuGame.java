import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuGame extends JPanel {

    private final Color COLORBK = new Color(249, 175, 57);
    private final Color COLORLINE = new Color(220, 236, 240);

    private Game game;

    private JRadioButton jRadioMin4;
    private JRadioButton jRadioMin5;

    private int sizeBoard = 8;
    private int mines = 10;


    public MenuGame() {

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setPreferredSize(new Dimension(250, 320));
        this.setBackground(new Color(48, 56, 58));
        initComponents();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private void initComponents() {
        JPanel selectionSize = new JPanel();
        selectionSize.setBorder(new LineBorder(COLORLINE, 3));
        selectionSize.setPreferredSize(new Dimension(200, 120));
        selectionSize.setBackground(COLORBK);
        JLabel chonkt = new JLabel("Select the size of the board");
        JRadioButton choose1 = new JRadioButton("Size 8x8");
        JRadioButton choose2 = new JRadioButton("Size 16x16");
        JRadioButton choose3 = new JRadioButton("Size 20x20");
        choose1.setBackground(COLORBK);
        choose2.setBackground(COLORBK);
        choose3.setBackground(COLORBK);
        choose1.setSelected(true);
        choose2.setSelected(false);
        choose3.setSelected(false);
        ButtonGroup buttonGroup = new ButtonGroup();
        selectionSize.add(chonkt);
        buttonGroup.add(choose1);
        buttonGroup.add(choose2);
        buttonGroup.add(choose3);
        selectionSize.add(choose1);
        selectionSize.add(choose2);
        selectionSize.add(choose3);
        selectionSize.setLayout(new BoxLayout(selectionSize, BoxLayout.Y_AXIS));
        ActionListener listener = e -> {
            String command = e.getActionCommand();
            switch (command) {
                case "lc1": {
                    sizeBoard = 8;
                    jRadioMin4.setEnabled(false);
                    jRadioMin5.setEnabled(false);
                    break;
                }
                case "lc2": {
                    sizeBoard = 16;
                    jRadioMin4.setEnabled(true);
                    jRadioMin5.setEnabled(false);
                    break;
                }
                case "lc3": {
                    sizeBoard = 20;
                    jRadioMin4.setEnabled(true);
                    jRadioMin5.setEnabled(true);
                    break;
                }
            }
        };
        choose1.addActionListener(listener);
        choose1.setActionCommand("lc1");
        choose2.addActionListener(listener);
        choose2.setActionCommand("lc2");
        choose3.addActionListener(listener);
        choose3.setActionCommand("lc3");
        this.add(selectionSize);

        JPanel selectnMin = new JPanel();
        selectnMin.setBorder(new LineBorder(COLORLINE, 3));
        selectnMin.setPreferredSize(new Dimension(200, 150));
        selectnMin.setLayout(new BoxLayout(selectnMin, BoxLayout.Y_AXIS));
        selectnMin.setBackground(COLORBK);
        JLabel head = new JLabel("Select the number of mines");
        selectnMin.add(head);
        JRadioButton jRadioMin1 = new JRadioButton("10 mines");
        JRadioButton jRadioMin2 = new JRadioButton("20 mines");
        JRadioButton jRadioMin3 = new JRadioButton("40 mines");
        jRadioMin4 = new JRadioButton("100 mines");
        jRadioMin5 = new JRadioButton("120 mines");
        jRadioMin1.setBackground(COLORBK);
        jRadioMin2.setBackground(COLORBK);
        jRadioMin3.setBackground(COLORBK);
        jRadioMin4.setBackground(COLORBK);
        jRadioMin5.setBackground(COLORBK);
        jRadioMin1.setSelected(true);
        jRadioMin4.setEnabled(false);
        jRadioMin5.setEnabled(false);
        ButtonGroup bg = new ButtonGroup();
        bg.add(jRadioMin1);
        bg.add(jRadioMin2);
        bg.add(jRadioMin3);
        bg.add(jRadioMin4);
        bg.add(jRadioMin5);
        ActionListener actionListener = e -> {
            switch (e.getActionCommand()) {
                case "lm1": {
                    mines = 10;
                    break;
                }
                case "lm2": {
                    mines = 20;
                    break;
                }
                case "lm3": {
                    mines = 40;
                    break;
                }
                case "lm4": {
                    mines = 100;
                    break;
                }
                case "lm5": {
                    mines = 120;
                    break;
                }
            }
        };
        jRadioMin1.addActionListener(actionListener);
        jRadioMin1.setActionCommand("lm1");
        jRadioMin2.addActionListener(actionListener);
        jRadioMin2.setActionCommand("lm2");
        jRadioMin3.addActionListener(actionListener);
        jRadioMin3.setActionCommand("lm3");
        jRadioMin4.addActionListener(actionListener);
        jRadioMin4.setActionCommand("lm4");
        jRadioMin5.addActionListener(actionListener);
        jRadioMin5.setActionCommand("lm5");
        selectnMin.add(jRadioMin1);
        selectnMin.add(jRadioMin2);
        selectnMin.add(jRadioMin3);
        selectnMin.add(jRadioMin4);
        selectnMin.add(jRadioMin5);

        this.add(selectnMin);

        JPanel start = new JPanel();
        start.setPreferredSize(new Dimension(200, 50));
        start.setBackground(new Color(48, 56, 58));
        JButton jbstart = new JButton("Start");
        jbstart.setBackground(new Color(146, 166, 170));
        start.add(jbstart);
        ActionListener aL = e -> {
            game.chooseBoardPerformed(sizeBoard, sizeBoard, mines);
        };
        jbstart.addActionListener(aL);
        start.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(start);
    }
}