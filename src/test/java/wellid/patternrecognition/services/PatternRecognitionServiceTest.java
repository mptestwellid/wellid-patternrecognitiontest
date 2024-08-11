package wellid.patternrecognition.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import wellid.patternrecognition.model.bo.LineSegment;
import wellid.patternrecognition.model.bo.Point;
import wellid.patternrecognition.model.bo.Space;
import wellid.patternrecognition.model.exception.InvalidPointException;
import wellid.patternrecognition.model.response.LineSegmentResponse;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatternRecognitionServiceTest {

    private PatternRecognitionService service;
    private Space space;

    @BeforeEach
    public void setUp() {
        service = new PatternRecognitionService();
        space = Space.getInstance();
        space.getPoints().clear();
    }

    @Test
    public void testAddPointValidPoint() throws InvalidPointException {
        Point point = new Point(1.0f, 1.0f);
        service.addPoint(point);

        Set<Point> points = service.getAllPoints();
        assertTrue(points.contains(point));
    }

    @Test
    public void testAddPointInvalidPoint() {
        Point invalidPoint = new Point(null, 1.0f);

        assertThrows(InvalidPointException.class, () -> {
            service.addPoint(invalidPoint);
        });

        Set<Point> points = service.getAllPoints();
        assertTrue(points.isEmpty());
    }

    @Test
    public void testGetAllPoints() throws InvalidPointException {
        Point point1 = new Point(1.0f, 1.0f);
        Point point2 = new Point(2.0f, 2.0f);
        service.addPoint(point1);
        service.addPoint(point2);

        Set<Point> points = service.getAllPoints();
        assertTrue(points.contains(point1) && points.contains(point2));
    }

    @Test
    public void testClearPoints() throws InvalidPointException {
        Point point1 = new Point(1.0f, 1.0f);
        Point point2 = new Point(2.0f, 2.0f);
        service.addPoint(point1);
        service.addPoint(point2);

        service.clearPoints();
        Set<Point> points = service.getAllPoints();
        assertTrue(points.isEmpty());
    }

    @Test
    public void testGetLineSegmentsNoPoints() {
        ResponseEntity<LineSegmentResponse> lines = service.getLineSegment(2);
        assertTrue(lines.getBody().getLineSegments().isEmpty());
    }

    @Test
    public void testGetLineSegmentsNotEnoughPoints() throws InvalidPointException {
        Point point1 = new Point(1.0f, 1.0f);
        Point point2 = new Point(2.0f, 2.0f);
        service.addPoint(point1);
        service.addPoint(point2);

        ResponseEntity<LineSegmentResponse> lines = service.getLineSegment(3);
        assertTrue(lines.getBody().getLineSegments().isEmpty());
    }

    @Test
    public void testGetLineSegmentsValidLines() throws InvalidPointException {
        Point p1 = new Point(1.0f, 1.0f);
        Point p2 = new Point(2.0f, 2.0f);
        Point p3 = new Point(3.0f, 3.0f);
        Point p4 = new Point(4.0f, 5.0f);

        service.addPoint(p1);
        service.addPoint(p2);
        service.addPoint(p3);
        service.addPoint(p4);

        ResponseEntity<LineSegmentResponse> response = service.getLineSegment(3);
        List<LineSegment> lines = response.getBody().getLineSegments().stream().toList();


        assertTrue(lines.size()>0 && lines.get(0).getPoints().contains(p1) && lines.get(0).getPoints().contains(p2) && lines.get(0).getPoints().contains(p3));
    }
}