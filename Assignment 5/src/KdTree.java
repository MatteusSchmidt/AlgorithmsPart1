/* *****************************************************************************
 *  Name:              Matteus Schmidt
 *  Coursera User ID:
 *  Last modified:     April 9, 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private final class Node { // complete
        private final Point2D key;
        private final int level;
        private Node left, right;

        public Node(Point2D key, int level) {
            this.key = key;
            this.level = level;
        }
    }

    private final class KDTree { // complete
        private Node root;
        private int size;

        private KDTree() { // complete
            root = null;
            size = 0;
        }

        private void insert(Point2D key) { // complete
            if (size == 0) {
                root = new Node(key, 1);
                size++;
            }
            else insertHelper(root, key, 1);
        }

        // A recursive function to insert a new key in BST
        private Node insertHelper(Node pointer, Point2D key, int level) { // complete
            // If the tree is empty, return a new node
            if (pointer == null) {
                pointer = new Node(key, level);
                // System.out.println(pointer.key.x() + " " + pointer.key.y() + ", " + pointer.level);
                size++;
                return pointer;
            }
            int newLevel = level + 1;

            // if the level in level order is odd, meaning, it is partitioned by x values
            if (!pointer.key.equals(key) && level % 2 != 0) {
                if (key.x() < pointer.key.x())
                    pointer.left = insertHelper(pointer.left, key, newLevel);
                else pointer.right = insertHelper(pointer.right, key, newLevel);
            }
            else if (!pointer.key.equals(key)) { // compare by y values
                if (key.y() < pointer.key.y())
                    pointer.left = insertHelper(pointer.left, key, newLevel);
                else pointer.right = insertHelper(pointer.right, key, newLevel);
            }

            return pointer;
        }

        private boolean contains(Point2D p) { // complete
            return containsHelper(root, p);
        }

        private boolean containsHelper(Node pointer, Point2D p) { // complete
            if (pointer == null) return false;
            // System.out.println("Node level: " + pointer.level);
            if (pointer.key.equals(p)) return true;

            if (pointer.level % 2 != 0) { // comparing x-coordinate
                if (p.x() < pointer.key.x()) return containsHelper(pointer.left, p);
                else return containsHelper(pointer.right, p);
            }
            else { // comparing y-coordinate
                if (p.y() < pointer.key.y()) return containsHelper(pointer.left, p);
                else return containsHelper(pointer.right, p);
            }
        }

        private ArrayList<Node> inOrderTraversal(Node root2) { // complete
            ArrayList<Node> returnList = new ArrayList<Node>();
            inOrderHelper(root2, returnList);
            return returnList;
        }

        private void inOrderHelper(Node root2, ArrayList<Node> returnList) { // complete
            if (root2 == null) return;
            inOrderHelper(root2.left, returnList);
            returnList.add(root2);
            inOrderHelper(root2.right, returnList);
        }
    }

    private KDTree tree;

    public KdTree() { // complete
        tree = new KDTree();
    } // construct an empty set of points

    public boolean isEmpty() { // complete
        return size() == 0;
    } // is the set empty?

    public int size() { // complete
        return tree.size;
    } // number of points in the set

    public void insert(Point2D p) { // complete
        checkException(p);
        tree.insert(p);
    } // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) { // complete
        checkException(p);
        return tree.contains(p);
    } // does the set contain point p?

    public void draw() { // complete
        ArrayList<Node> inOrderTraversal = tree.inOrderTraversal(tree.root);
        for (Node node : inOrderTraversal) {
            StdDraw.point(node.key.x(), node.key.y());
        }
    } // draw all points to standard draw

    /* for testing
        public void print() {
            ArrayList<Node> inOrderTraversal = tree.inOrderTraversal(tree.root);
            System.out.println("*************************");
            for (Node node : inOrderTraversal) {
                StdOut.println("(" + node.key.x() + ", " + node.key.y() + ")");
            }
            System.out.println("*************************");
        } // prints all points to standard output
     */

    public Iterable<Point2D> range(RectHV rect) { // TODO?
        checkException(rect);
        ArrayList<Point2D> returnList = new ArrayList<>();
        rangeHelper(tree.root, rect, returnList);
        return returnList;
    } // all points that are inside the rectangle (or on the boundary)

    private void rangeHelper(Node pointer, RectHV rect, List<Point2D> points) {
        if (pointer == null)
            return;

        if (rect.contains(pointer.key))
            points.add(pointer.key);

        if (pointer.level % 2 != 0) { // vertical
            if (pointer.key.x() >= rect.xmin() && pointer.key.x() <= rect.xmax()) {
                rangeHelper(pointer.left, rect, points);
                rangeHelper(pointer.right, rect, points);
            }
            else if (pointer.key.x() <= rect.xmin()) {
                rangeHelper(pointer.right, rect, points);
            }
            else if (pointer.key.x() >= rect.xmax()) {
                rangeHelper(pointer.left, rect, points);
            }
        }
        else { // horizontal
            if (pointer.key.y() >= rect.ymin() && pointer.key.y() <= rect.ymax()) {
                rangeHelper(pointer.left, rect, points);
                rangeHelper(pointer.right, rect, points);
            }
            else if (pointer.key.y() <= rect.ymin()) {
                rangeHelper(pointer.right, rect, points);
            }
            else if (pointer.key.y() >= rect.ymax()) {
                rangeHelper(pointer.left, rect, points);
            }
        }
    }

    public Point2D nearest(Point2D p) { // complete
        checkException(p);
        if (tree.size == 0) return null;
        return nearestHelper(tree.root, null, p, Double.POSITIVE_INFINITY);

    } // a nearest neighbor in the set to point p; null if the set is empty

    private Point2D nearestHelper(Node pointer, Node maxMin, Point2D p, double min) {
        if (pointer == null) return maxMin.key;

        double temp = p.distanceSquaredTo(pointer.key);
        if (temp < min) {
            min = temp;
            maxMin = pointer;
        }

        if (pointer.level % 2 != 0) {
            if (p.x() < pointer.key.x()) nearestHelper(pointer.left, maxMin, p, min);
            else if (p.x() > pointer.key.x()) nearestHelper(pointer.right, maxMin, p, min);
            else {
                nearestHelper(pointer.right, maxMin, p, min);
                nearestHelper(pointer.left, maxMin, p, min);
            }
        }
        
        else { // compare by y values
            if (p.y() < pointer.key.y()) nearestHelper(pointer.left, maxMin, p, min);
            else if (p.y() > pointer.key.y()) nearestHelper(pointer.right, maxMin, p, min);
            else {
                nearestHelper(pointer.right, maxMin, p, min);
                nearestHelper(pointer.left, maxMin, p, min);
            }
        }
        return maxMin.key;
    }

    private void checkException(Object object) {
        if (object == null) throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        KdTree kdtree = new KdTree();
        kdtree.insert(new Point2D(0.875, 0.5));
        kdtree.insert(new Point2D(0.125, 0.6875));
        kdtree.insert(new Point2D(0.9375, 0.875));
        kdtree.insert(new Point2D(0.75, 0.4375));
        kdtree.insert(new Point2D(0.375, 0.75));
        kdtree.insert(new Point2D(0.1875, 0.1875));
        kdtree.insert(new Point2D(0.5625, 1.0));
        kdtree.insert(new Point2D(1.0, 0.625));
        kdtree.insert(new Point2D(0.625, 0.375));
        kdtree.insert(new Point2D(0.4375, 0.25));
        System.out.println(kdtree.size());

        System.out.println(kdtree.contains(new Point2D(0.875, 0.5)));
        System.out.println(kdtree.contains(new Point2D(0.9375, 0.875)));
        System.out.println(kdtree.contains(new Point2D(0.125, 0.6875)));
        System.out.println(kdtree.contains(new Point2D(0.5625, 1.0)));
        System.out.println(kdtree.contains(new Point2D(0.625, 0.375)));
        System.out.println(kdtree.contains(new Point2D(0.4375, 0.25)));
    } // unit testing of the methods (optional)
}
