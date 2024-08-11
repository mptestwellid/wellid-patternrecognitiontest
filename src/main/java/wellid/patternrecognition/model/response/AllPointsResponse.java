package wellid.patternrecognition.model.response;

import wellid.patternrecognition.model.bo.Point;

import java.util.Set;

public class AllPointsResponse {

    private Set<Point> points;

    public AllPointsResponse(Set<Point> points) {
        this.points = points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Set<Point> getPoints() {
        return points;
    }
}
