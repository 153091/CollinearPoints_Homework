import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segmentStack = new ArrayList<>();

    public FastCollinearPoints(Point[] points)  {   // finds all line segments containing 4 or more points
        if(points==null || hasDuplicate(points) || hasNull(points)) throw new IllegalArgumentException();



        Point[] copy = points.clone();
        Arrays.sort(copy);

        for (int i =0; i<copy.length-3; i++){

            // Sort the points according to the slopes they makes with p.
            // Check if any 3 (or more) adjacent points in the sorted order
            // have equal slopes with respect to p. If so, these points,
            // together with p, are collinear.

            Insertion.sort(copy, copy[i].slopeOrder());

            for (int p =0,first =1, last =2; last<copy.length; last++) {
                // find last collinear to p point
                while (last < copy.length && (copy[p].slopeTo(copy[first]) == copy[p].slopeTo(copy[last])))
                    last++;

                // if found at least 3 elements, make segment if it's unique
                if (last -first >=3 && copy[p].compareTo(copy[first])<0)
                    segmentStack.add(new LineSegment(copy[p], copy[last-1])); // -1 так как последний Ласт фальшивый

                // Try to find next
                first = last;
            }
        }

    }

    public           int numberOfSegments() {   // the number of line segments
        return segmentStack.size();
    }

    public LineSegment[] segments() {         // the line segments
        return segmentStack.toArray(new LineSegment[segmentStack.size()]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}