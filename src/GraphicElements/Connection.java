/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphicElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author rajan
 */
public class Connection {

        Line[] lines;
        int noOfSegs;
        private Color color;
        public Connection(Point p1, Point p2, int[] types, double[] factors) {
        this.color = Color.BLACK;
            noOfSegs = types.length;
            lines = new Line[noOfSegs];
            int dx = p2.x - p1.x;
            int dy = p2.y - p1.y;
            for (int i = 0; i < noOfSegs; i++) {
                int stretch;
                if ((types[i] == Line.LEFTARROW) || (types[i] == Line.RIGHTARROW) || (types[i] == Line.HORIZONTAL)) {
                    stretch = (int) (dx * factors[i]);
                } else {
                    stretch = (int) (dy * factors[i]);
                }

                lines[i] = new Line(types[i], p1, stretch);
                p1 = lines[i].getEnd();
            }

        }

        public void update(double xfactor,double yfactor) {
            for (int i = 0; i < noOfSegs; i++) {
                lines[i].update(xfactor,yfactor);
            }
        }

        public void draw(Graphics2D g) {

            for (int i = 0; i < noOfSegs; i++) {
                lines[i].draw(g);
            }
        }

        public void setColor(Color color) {
            for (int i = 0; i < noOfSegs; i++) {
                lines[i].setColor(color);
            }
        }
    }
