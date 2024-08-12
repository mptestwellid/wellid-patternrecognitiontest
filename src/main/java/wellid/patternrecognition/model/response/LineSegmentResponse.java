package wellid.patternrecognition.model.response;

import wellid.patternrecognition.model.bo.LineSegment;

import java.util.Set;

public class LineSegmentResponse {
    private String message;
    private Set<LineSegment> lineSegments;

    public LineSegmentResponse(Set<LineSegment> lineSegments) {
        this.message = MessageEnum.LINE_SEGMENT_OK.getMessage();
        this.lineSegments = lineSegments;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<LineSegment> getLineSegments() {
        return lineSegments;
    }

    public void setLineSegments(Set<LineSegment> lineSegments) {
        this.lineSegments = lineSegments;
    }
}
