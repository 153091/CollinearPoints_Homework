import edu.princeton.cs.algs4.Stack;

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

    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points

        if (points == null || hasDuplicate(points) || hasNull(points)) throw new IllegalArgumentException(); //если на входе null

        Stack<LineSegment> segmentStack = new Stack<>();

        Arrays.sort(points);

        for (int first = 0; first < points.length - 3; first++)
            for (int second = first + 1; second < points.length - 2; second++) {
                double slopeFS = points[first].slopeTo(points[second]);
                for (int third = second + 1; third < points.length - 1; third++) {
                    double slopeFT = points[first].slopeTo(points[third]);
                            if (slopeFS == slopeFT) {
                                for (int forth = third + 1; forth < points.length; forth++) {
                                    double slopeFF = points[first].slopeTo(points[forth]);
                                    if (slopeFS == slopeFF)
                                        segmentStack.push(new LineSegment(points[first], points[forth]));
                                }

                            }
                }
            }

        int size = segmentStack.size();
        segments = new LineSegment[size];
        for (int i = 0; i < size; i++)
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
    { for (int i = 0; i < points.length - 1; i++)
        for (int j = i+1; j < points.length; j++)
        if (points[i].compareTo(points[j]) == 0) return true;

        return false;
    }

    //есть ли среди точек Null ?
    private static boolean hasNull(Point[] points) {
        for (Point point : points) if (point == null) return true;
            return false;
    }
}