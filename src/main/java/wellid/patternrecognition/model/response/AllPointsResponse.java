package wellid.patternrecognition.model.response;

import wellid.patternrecognition.model.bo.Point;

import java.util.Set;

public class AllPointsResponse {

    private String message;
    private Set<Point> points;

    public AllPointsResponse(Set<Point> points) {
        this.message = MessageEnum.ALL_POINTS_OK.getMessage();
        this.points = points;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Set<Point> getPoints() {
        return points;
    }
}
