import java.util.ArrayList;
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

    private ArrayList<LineSegment> segmentStack = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points

        if (points == null || hasDuplicate(points) || hasNull(points)) throw new IllegalArgumentException(); //если на входе null


        Point[] copy = points.clone();
        Arrays.sort(copy);

        for (int first = 0; first < copy.length - 3; first++)
            for (int second = first + 1; second < copy.length - 2; second++) {
                double slopeFS = copy[first].slopeTo(copy[second]);
                for (int third = second + 1; third < copy.length - 1; third++) {
                    double slopeFT = copy[first].slopeTo(copy[third]);
                            if (slopeFS == slopeFT) {
                                for (int forth = third + 1; forth < copy.length; forth++) {
                                    double slopeFF = copy[first].slopeTo(copy[forth]);
                                    if (slopeFS == slopeFF)
                                        segmentStack.add(new LineSegment(copy[first], copy[forth]));
                                }

                            }
                }
            }

    }

    public int numberOfSegments()  { // the number of line segments
        return segmentStack.size();
    }

    public LineSegment[] segments() { // the line segments
        return segmentStack.toArray(new LineSegment[segmentStack.size()]);
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