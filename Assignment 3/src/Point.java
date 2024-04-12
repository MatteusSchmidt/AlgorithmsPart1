/* *****************************************************************************
 *  Name:              Matteus Schmidt
 *  Coursera User ID:
 *  Last modified:     March 25, 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final double x;
    private final double y;

    /* done */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    } // constructs the point (x, y)

    public void draw() {
        StdDraw.point(x, y);
    } // draws this point

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    } // draws the line segment from this point to that point

    /* done */
    public String toString() {
        return "(" + x + ", " + y + ")";
    } // string representation

    /* needs returns */
    public int compareTo(Point that) {
        if ((y < that.y) || (equalY(that.y) && (x < that.x))) return -1;
        else if (equalY(that.y) && equalX(that.x)) return 0;
        return 1;
    } // compare two points by y-coordinates, breaking ties by x-coordinates

    /* done */
    public double slopeTo(Point that) {
        if (equalX(that.x) && equalY(that.x)) return Double.NEGATIVE_INFINITY;
        else if (equalY(that.x)) return Double.POSITIVE_INFINITY;
        else if (equalX(that.y)) return 0;
        return ((that.y - y) / (that.x - x));
    }

    private boolean equalX(double thatx) {
        return (Math.abs(thatx - x) <= 0.001);
    }

    private boolean equalY(double thaty) {
        return (Math.abs(thaty - y) <= 0.001);
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
                double s1 = slopeTo(o1);
                double s2 = slopeTo(o2);
                if (s1 < s2) return -1;
                else if (s1 == s2) return 0;
                return 1;
            }
        };
    }
}
