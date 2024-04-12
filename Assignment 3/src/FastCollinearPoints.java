/* *****************************************************************************
 *  Name:              Matteus Schmidt
 *  Coursera User ID:
 *  Last modified:     March 25, 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private int numLineSegments;


    public FastCollinearPoints(Point[] points) {
        numLineSegments = 0;
        nullChecker(points);
        ArrayList<LineSegment> tempLineSegments = new ArrayList<>();
        MergeX.sort(points);
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Point[] copyPoints = new Point[points.length - i - 1];
            System.arraycopy(points, i + 1, copyPoints, 0, points.length - i - 1);

            MergeX.sort(copyPoints, p.slopeOrder());

            int lineCounter = 2;
            for (int j = 1; j < points.length - i - 1; j++) {
                if (Double.compare(p.slopeTo(copyPoints[j - 1]), p.slopeTo(copyPoints[j])) == 0) {
                    lineCounter++;
                }
                else {
                    if (lineCounter >= 4) {
                        tempLineSegments.add(new LineSegment(p, copyPoints[j - 1]));
                        numLineSegments++;
                    }
                    lineCounter = 2;
                }
            }
            if (lineCounter >= 4) {
                tempLineSegments.add(new LineSegment(p, copyPoints[copyPoints.length - 1]));
                numLineSegments++;
            }
        }
        lineSegments = tempLineSegments.toArray(new LineSegment[tempLineSegments.size()]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
