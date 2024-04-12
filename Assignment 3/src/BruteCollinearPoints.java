/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;
    private int numLineSegments;

    public BruteCollinearPoints(Point[] points) {
        numLineSegments = 0;

        nullChecker(points);

        Point[] p = new Point[points.length];
        System.arraycopy(points, 0, p, 0, points.length);
        lineSegments = analyzeSegments(p);
    } // finds all line segments containing 4 points

    private LineSegment[] analyzeSegments(Point[] points) {
        ArrayList<LineSegment> tempSegments = new ArrayList<>();
        MergeX.sort(points);
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int d = j + 1; d < points.length - 1; d++) {
                    for (int f = d + 1; f < points.length; f++) {
                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[d])
                                && points[i].slopeTo(points[j]) == points[d].slopeTo(points[f])) {
                            tempSegments.add(new LineSegment(points[i], points[f]));
                            numLineSegments++;
                        }
                    }
                }
            }
        }
        return tempSegments.toArray(new LineSegment[tempSegments.size()]);
    }

    public int numberOfSegments() {
        return numLineSegments;
    } // the number of line segments

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, numLineSegments);
    } // the line segments

    private static void nullChecker(Point[] points) {
        if (points == null) throw new NullPointerException();
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) throw new NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment.toString());
            segment.draw();
        }
        StdDraw.show();
    }
}
