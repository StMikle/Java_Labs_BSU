package mainPackage;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;
    private JMenuItem pauseWithoutThree;
    private Field field = new Field();

    public MainFrame() {
        super("Программирование и синхронизация потоков");
        this.setSize(700, 500);
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((kit.getScreenSize().width - 700) / 2, (kit.getScreenSize().height - 500) / 2);
        this.setExtendedState(6);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu figuresMenu = new JMenu("Фигуры");
        Action addFigureAction = new AbstractAction("Добавить фигуру") {
            public void actionPerformed(ActionEvent event) {
                field.addFigure();
                if (!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled()) {
                    pauseMenuItem.setEnabled(true);
                    pauseWithoutThree.setEnabled(true);
                }

            }
        };
        menuBar.add(figuresMenu);
        figuresMenu.add(addFigureAction);
        JMenu controlMenu = new JMenu("Управление");
        menuBar.add(controlMenu);

        Action pauseWithoutTreeAction = new AbstractAction("Выборочная пауза") {
            public void actionPerformed(ActionEvent event) {
                field.pauseByCondition();
                pauseWithoutThree.setEnabled(false);
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        };

        pauseWithoutThree = controlMenu.add(pauseWithoutTreeAction);
        pauseWithoutThree.setEnabled(false);
        Action pauseAction = new AbstractAction("Приостановить движение") {
            public void actionPerformed(ActionEvent event) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                pauseWithoutThree.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        };
        pauseMenuItem = controlMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);

        Action resumeAction = new AbstractAction("Возобновить движение") {
            public void actionPerformed(ActionEvent event) {
                field.resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
                pauseWithoutThree.setEnabled(true);
            }
        };
        resumeMenuItem = controlMenu.add(resumeAction);
        resumeMenuItem.setEnabled(false);
        getContentPane().add(this.field, "Center");
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}


