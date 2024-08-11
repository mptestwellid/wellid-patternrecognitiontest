package wellid.patternrecognition.model.bo;

import java.util.HashSet;
import java.util.Set;

public class LineSegment {

    private Set<Point> points;

    public LineSegment() {
        this.points = new HashSet<>();
    }

    public LineSegment(Set<Point> points) {
        this();
        this.points.addAll(points);
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Set<Point> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "LineSegment: " + points;
    }


}
