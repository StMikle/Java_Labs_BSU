package mainPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

public class MovingFigure implements Runnable {
    private static final int maxFramingSquareHalfSize = 60;
    private static final int minFramingSquareHalfSize = 10;
    private static final int minSleepTime = 1;
    private Field field;
    private int framingSquareHalfSize;
    private Color color;
    private Stroke stroke = new BasicStroke(2.0F, 0, 1, 10.0F, new float[]{6.0F, 3.0F, 12.0F, 3.0F, 3.0F, 3.0F, 12.0F, 3.0F, 6.0F, 3.0F}, 0.0F);
    private double x;
    private double y;
    private int sleepTime;
    private double shiftX;
    private double shiftY;
    private boolean isMoving;

    public MovingFigure(Field field) {
        this.field = field;
        this.framingSquareHalfSize = 10 + (new Double(Math.random() * 50.0D)).intValue();
        this.sleepTime = 16 - (new Double((double)Math.round((float)(210 / this.framingSquareHalfSize)))).intValue();
        if (this.sleepTime < minSleepTime) {
            this.sleepTime = minSleepTime;
        }

        double angle = Math.random() * 2.0D * 3.141592653589793D;
        this.shiftX = 3.0D * Math.cos(angle);
        this.shiftY = 3.0D * Math.sin(angle);
        this.color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
        this.x = (double)this.framingSquareHalfSize + Math.random() * (field.getSize().getWidth() - (double)(2 * this.framingSquareHalfSize));
        this.y = (double)this.framingSquareHalfSize + Math.random() * (field.getSize().getHeight() - (double)(2 * this.framingSquareHalfSize));
        Thread thisThread = new Thread(this);
        thisThread.start();
        this.isMoving = true;
    }

    public void run() {
        try {
            while(true) {
                this.field.canMove(this);
                if (this.x + this.shiftX <= (double)this.framingSquareHalfSize) {
                    this.shiftX = -this.shiftX;
                    this.x = (double)this.framingSquareHalfSize;
                } else if (this.x + this.shiftX >= (double)(this.field.getWidth() - this.framingSquareHalfSize)) {
                    this.shiftX = -this.shiftX;
                    this.x = (double)(new Double((double)(this.field.getWidth() - this.framingSquareHalfSize))).intValue();
                } else if (this.y + this.shiftY <= (double)this.framingSquareHalfSize) {
                    this.shiftY = -this.shiftY;
                    this.y = (double)this.framingSquareHalfSize;
                } else if (this.y + this.shiftY >= (double)(this.field.getHeight() - this.framingSquareHalfSize)) {
                    this.shiftY = -this.shiftY;
                    this.y = (double)(new Double((double)(this.field.getHeight() - this.framingSquareHalfSize))).intValue();
                } else {
                    this.x += this.shiftX;
                    this.y += this.shiftY;
                }
                Thread.sleep((long)this.sleepTime);
            }
        } catch (InterruptedException var2) {
        }
    }

    public void paint(Graphics2D canvas) {
        Ellipse2D ellipse = new java.awt.geom.Ellipse2D.Double(this.x - (double)this.framingSquareHalfSize, this.y - (double)this.framingSquareHalfSize, (double)(2 * this.framingSquareHalfSize), (double)(2 * this.framingSquareHalfSize));
        double x2 = this.x - (double)this.framingSquareHalfSize;
        double y2 = this.y - (double)this.framingSquareHalfSize;
        double h2 = 2 * this.framingSquareHalfSize;
        double w2 = this.framingSquareHalfSize;
        Rectangle2D rec2 = new java.awt.geom.Rectangle2D.Double(x2, y2, h2, w2);
        Area figure = new Area(ellipse);
        figure.subtract(new Area(rec2));

        GeneralPath ellipsHalf = new GeneralPath(figure);
        ellipsHalf.moveTo(x, y);
        ellipsHalf.append(figure, true);
        ellipsHalf.lineTo(x, y - framingSquareHalfSize);
        ellipsHalf.lineTo(x + framingSquareHalfSize, y - framingSquareHalfSize);
        ellipsHalf.lineTo(x + framingSquareHalfSize, y - framingSquareHalfSize / 2);
        ellipsHalf.lineTo(x, y - framingSquareHalfSize / 2);

        canvas.setPaint(this.color);
        canvas.fill(ellipsHalf);
        canvas.setStroke(this.stroke);
        canvas.setPaint(Color.black);
        canvas.draw(ellipsHalf);
    }

    public int getSleepTime() {
        return this.sleepTime;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }
}

