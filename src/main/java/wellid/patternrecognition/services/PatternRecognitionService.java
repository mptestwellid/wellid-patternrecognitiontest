package wellid.patternrecognition.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import wellid.patternrecognition.model.bo.LineSegment;
import wellid.patternrecognition.model.bo.Point;
import wellid.patternrecognition.model.bo.Space;
import wellid.patternrecognition.model.exception.InvalidPointException;
import wellid.patternrecognition.model.response.ErrorResponse;
import wellid.patternrecognition.model.response.LineSegmentResponse;
import wellid.patternrecognition.utils.PatternRecognitionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service class for managing and processing geometric patterns using points.
 * This service allows adding points, retrieving all points, clearing points,
 * and finding line segments formed by the points in the space.
 * <p>
 * The service interacts with a singleton instance of {@link Space} to store and
 * manage the points.
 * </p>
 *
 * <h2>Methods Summary</h2>
 * <ul>
 *   <li>{@link #addPoint(Point)} - Adds a valid point to the space.</li>
 *   <li>{@link #getAllPoints()} - Retrieves all points currently stored in the space.</li>
 *   <li>{@link #clearPoints()} - Clears all points from the space.</li>
 *   <li>{@link #getLineSegment(int)} - Finds all line segments formed by at least a minimum number of points.</li>
 * </ul>
 */
public class PatternRecognitionService {

    private Space space = Space.getInstance();

    /**
     * Adds a point to the space if it is valid.
     *
     * @param point the {@link Point} to be added
     * @throws InvalidPointException if the point is not valid (e.g., one or both coordinates are {@code null})
     */
    public void addPoint(Point point) throws InvalidPointException {
        if(PatternRecognitionUtils.isPointValid(point))
            space.getPoints().add(point);
        else
            throw new InvalidPointException(point);
    }

    /**
     * Retrieves all points currently stored in the space.
     *
     * @return a {@link Set} of all {@link Point} objects currently in the space
     */
    public Set<Point> getAllPoints() {
        return space.getPoints();
    }

    /**
     * Clears all points from the space.
     * This method removes all points currently stored in the space.
     */
    public void clearPoints() {
        space.getPoints().clear();
    }

    /**
     * Retrieves all line segments formed by the points in the space that meet the specified minimum number of points.
     * A line segment is formed if there are at least two points, and the points are aligned.
     *
     * <p>This method first checks if the specified minimum number of points (`minPointsForLine`) is less than 2.
     * If so, it returns a {@link ResponseEntity} containing an error message and a {@link HttpStatus#BAD_REQUEST} status.
     *
     * <p>If the number of points in the space is less than 2, it returns an empty set of line segments with an
     * {@link HttpStatus#OK} status.
     *
     * <p>If there are sufficient points, the method iterates through the points to determine all possible line segments
     * that can be formed with at least `minPointsForLine` points. Aligned points that satisfy the minimum requirement
     * are added as line segments to the result set.
     *
     * @param minPointsForLine The minimum number of aligned points required to form a line segment. Must be 2 or greater.
     *
     * @return {@link ResponseEntity} containing a {@link LineSegmentResponse} with the set of line segments
     * that meet the specified criteria, or an error message if the input is invalid.
     */
    public ResponseEntity getLineSegment(int minPointsForLine) {
        List<Point> points = new ArrayList<>(space.getPoints());
        Set<LineSegment> lineSegments = new HashSet<>();

        if(minPointsForLine < 2){
            ErrorResponse error = new ErrorResponse("To obtain a line segment you need at least two points", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        if(points.size() < 2){
            LineSegmentResponse response = new LineSegmentResponse(lineSegments);
            return ResponseEntity.ok(response);
        }

        for (int firstPointCounter = 0; firstPointCounter < points.size(); firstPointCounter++) {
            for (int secondPointCounter = firstPointCounter + 1; secondPointCounter < points.size(); secondPointCounter++) {

                LineSegment segment = new LineSegment();
                segment.addPoint(points.get(firstPointCounter));
                segment.addPoint(points.get(secondPointCounter));
                if(minPointsForLine == 2){
                    LineSegment segment2Points = new LineSegment(segment.getPoints());
                    lineSegments.add(segment2Points);
                }


                for (int thirdPointCounter = secondPointCounter + 1; thirdPointCounter < points.size(); thirdPointCounter++) {
                    if (thirdPointCounter != firstPointCounter && thirdPointCounter != secondPointCounter && PatternRecognitionUtils.isPointsAligned(points.get(firstPointCounter), points.get(secondPointCounter), points.get(thirdPointCounter))) {
                        if (!segment.getPoints().contains(points.get(thirdPointCounter))) {
                            segment.addPoint(points.get(thirdPointCounter));
                            if (segment.getPoints().size() > 2 && segment.getPoints().size() >= minPointsForLine) {
                                LineSegment segmentToAdd = new LineSegment(segment.getPoints());
                                lineSegments.add(segmentToAdd);
                            }
                        }
                    }
                }
            }
        }

        LineSegmentResponse response = new LineSegmentResponse(lineSegments);
        return ResponseEntity.ok(response);
    }
}
