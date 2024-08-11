package wellid.patternrecognition.model.response;

import wellid.patternrecognition.model.bo.LineSegment;

import java.util.Set;

public class LineSegmentResponse {
    private Set<LineSegment> lineSegments;

    public LineSegmentResponse(Set<LineSegment> lineSegments) {
        this.lineSegments = lineSegments;
    }

    public Set<LineSegment> getLineSegments() {
        return lineSegments;
    }

    public void setLineSegments(Set<LineSegment> lineSegments) {
        this.lineSegments = lineSegments;
    }
}
