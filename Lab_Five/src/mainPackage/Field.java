package mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
@SuppressWarnings("serial")
public class Field extends JPanel {
    private static final Integer MINIMUM_MOVING_FIGURES_COUNT = 3;
    private boolean paused;
    private boolean pausedByCondition;
    private final ArrayList<MovingFigure> figures = new ArrayList<>(10);
    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
            repaint();
        }
    });
    public Field() {
        setBackground(Color.WHITE);
        repaintTimer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (MovingFigure figure: figures)
            figure.paint(canvas);
    }
    public void addFigure() {
        figures.add(new MovingFigure(this));
    }
    public synchronized void pause() {
        paused = true;
        this.figures.forEach(figure -> figure.setMoving(false));
    }

    public synchronized void pauseByCondition() {
        pausedByCondition = true;
    }

    public synchronized void resume() {
        paused = false;
        pausedByCondition = false;
        this.figures.forEach(figure -> figure.setMoving(true));
        notifyAll();
    }

    public synchronized void canMove(MovingFigure figure) throws InterruptedException {
        if (pausedByCondition) {
            stopByCondition(figure);
        } else if (paused) {
            wait();
        }
    }

    private void stopByCondition(MovingFigure figure) throws InterruptedException {
        long movingFiguresCount = this.figures.stream().filter(MovingFigure::isMoving).count();
        if (movingFiguresCount > MINIMUM_MOVING_FIGURES_COUNT) {
            figure.setMoving(false);
            wait();
        }
    }
}

