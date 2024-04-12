/* *****************************************************************************
 *  Name:              Matteus Schmidt
 *  Coursera User ID:
 *  Last modified:     April 9, 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET { // complete
    private SET<Point2D> pointSet;

    public PointSET() { // complete
        pointSet = new SET<Point2D>();
    } // construct an empty set of points

    public boolean isEmpty() { // complete
        return size() == 0;
    } // is the set empty?

    public int size() { // complete
        return pointSet.size();
    } // number of points in the set

    public void insert(Point2D p) { // complete
        checkException(p);
        pointSet.add(p);
    } // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) { // complete
        checkException(p);
        return pointSet.contains(p);
    } // does the set contain point p?

    public void draw() { // complete
        for (Point2D point : pointSet) {
            StdDraw.point(point.x(), point.y());
        }
    } // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) { // complete
        checkException(rect);
        if (pointSet.isEmpty()) return null;
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        for (Point2D point : pointSet) if (rect.contains(point)) list.add(point);
        return list;
    } // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) { // complete
        checkException(p);
        if (pointSet.isEmpty()) return null;
        Point2D pointer;
        double min;
        if (!pointSet.min().equals(p)) {
            min = p.distanceSquaredTo(pointSet.min());
            pointer = pointSet.min();
        }
        else {
            min = p.distanceSquaredTo(pointSet.max());
            pointer = pointSet.max();
        }

        for (Point2D point : pointSet) {
            if (p.distanceSquaredTo(point) < min) {
                min = p.distanceSquaredTo(point);
                pointer = point;
            }
        }
        return pointer;
    } // a nearest neighbor in the set to point p; null if the set is empty

    private void checkException(Object object) { // complete
        if (object == null) throw new IllegalArgumentException();
    }

    public static void main(String[] args) {

    } // unit testing of the methods (optional)
}
