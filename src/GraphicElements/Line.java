/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicElements;

import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

/**
 *
 * @author rajan
 */
public class Line {

    static final int UPARROW = 0, DOWNARROW = 1, LEFTARROW = 2, RIGHTARROW = 3, HORIZONTAL = 4, VERTICAL = 5;
    static final int NORMAL = 3, THICK = 6;
    private final int type;
    private final Point normStart, normEnd, start, end;
    private int stroke = NORMAL;
    private Color color = Color.BLACK;

    public Line(int type, Point start, int stretch) {
        this.type = type;
        this.normStart = start;
        this.start = new Point(0, 0);
        this.end = new Point(0, 0);
        normEnd = new Point(0, 0);
        if ((type == LEFTARROW) || (type == RIGHTARROW) || (type == HORIZONTAL)) {
            this.normEnd.x = normStart.x + stretch;
            this.normEnd.y = normStart.y;
        } else {
            this.normEnd.x = normStart.x;
            this.normEnd.y = normStart.y + stretch;
        }

    }

    private Point[] getTriPoints() {
        int arrowSize = 5;
        Point[] points = new Point[3];
        for (int i = 0; i < 3; i++) {
            points[i] = new Point(0, 0);
        }
        switch (type) {
            case UPARROW:
                points[0].x = start.x;
                points[1].x = start.x + arrowSize;
                points[2].x = start.x - arrowSize;
                points[0].y = start.y;
                points[1].y = start.y + arrowSize;
                points[2].y = start.y + arrowSize;
                break;
            case DOWNARROW:
                points[0].x = end.x;
                points[1].x = end.x + arrowSize;
                points[2].x = end.x - arrowSize;
                points[0].y = end.y;
                points[1].y = end.y - arrowSize;
                points[2].y = end.y - arrowSize;
                break;
            case LEFTARROW:
                points[0].x = start.x;
                points[1].x = start.x + arrowSize;
                points[2].x = start.x + arrowSize;
                points[0].y = start.y;
                points[1].y = start.y - arrowSize;
                points[2].y = start.y + arrowSize;
                break;
            case RIGHTARROW:
                points[0].x = end.x;
                points[1].x = end.x - arrowSize;
                points[2].x = end.x - arrowSize;
                points[0].y = end.y;
                points[1].y = end.y - arrowSize;
                points[2].y = end.y + arrowSize;
        }
        return points;
    }

    public void draw(Graphics2D g) {
        Color color1 = g.getColor();
        g.setColor(color);
        if (stroke == NORMAL) {
            g.drawLine(start.x, start.y, end.x, end.y);
            Point[] points = getTriPoints();
            int[] xarr = {points[0].x, points[1].x, points[2].x};
            int[] yarr = {points[0].y, points[1].y, points[2].y};
            g.fillPolygon(xarr, yarr, 3);
        } else {
            Stroke s = g.getStroke();
            g.setStroke(new BasicStroke(stroke));
            g.drawLine(start.x, start.y, end.x, end.y);
            g.setStroke(s);
        }
        g.setColor(color1);
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

    public Point getEnd() {
        return normEnd;
    }

    public void update(double xfactor, double yfactor) {

        start.x = (int) (normStart.x * xfactor);
        start.y = (int) (normStart.y * yfactor);//To change body of generated methods, choose Tools | Templates.
        end.x = (int) (normEnd.x * xfactor);
        end.y = (int) (normEnd.y * yfactor);
    }

    public void setColor(Color color) {
        this.color = color; //To change body of generated methods, choose Tools | Templates.
    }
}
