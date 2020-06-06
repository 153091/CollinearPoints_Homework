import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


/**
 * Write a program BruteCollinearPoints.java
 * that examines 4 points at a time and checks whether they all
 * lie on the same line segment, returning all such line segments.
 *
 * To check whether the 4 points p, q, r, and s are collinear,
 * check whether the three slopes between p and q, between p and r,
 * and between p and s are all equal.
 */

public class BruteCollinearPoints {

    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points

        if (points==null || hasDuplicate(points) || hasNull(points)) throw new IllegalArgumentException(); //если на входе null

        Stack<LineSegment> segmentStack = new Stack<>();

        Point[] copy = points.clone(); //скопировал массив
        Arrays.sort(copy);

        for (int first = 0; first<copy.length-3; first++)
            for (int second = first+1; second <copy.length-2; second++) {
                double slopeFS = copy[first].slopeTo(copy[second]);
                for (int third = second + 1; third< copy.length-1; third++) {
                    double slopeFT = copy[first].slopeTo(copy[third]);
                            if(slopeFS==slopeFT){
                                for (int forth = third+1; forth<copy.length; forth++) {
                                    double slopeFF = copy[first].slopeTo(copy[forth]);
                                    if (slopeFS==slopeFF)
                                        segmentStack.push(new LineSegment(copy[first], copy[forth]));
                                }

                            }
                }
            }

        int size = segmentStack.size();
        segments = new LineSegment[size];
        for (int i = 0 ; i<size; i++)
            this.segments[i] = segmentStack.pop();
    }

    public int numberOfSegments()  { // the number of line segments
        return this.segments.length;
    }

    public LineSegment[] segments() { // the line segments
        return this.segments.clone();
    }




    //есть ли копии???
    private static boolean hasDuplicate(Point[] points)
    { for (int i =0; i< points.length - 1; i++)
        if (points[i].compareTo(points[i+1])==0) return true;

        return false;
    }

    //есть ли среди точек Null ?
    private static boolean hasNull(Point[] points) {
        for (int i = 0; i< points.length; i++)
            if (points[i] == null) return true;
            return false;
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
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}