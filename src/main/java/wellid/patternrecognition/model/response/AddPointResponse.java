package wellid.patternrecognition.model.response;

import wellid.patternrecognition.model.bo.Point;

public class AddPointResponse {
    private String message;
    private Point point;

    public AddPointResponse(String message, Point point) {
        this.message = message;
        this.point = point;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
