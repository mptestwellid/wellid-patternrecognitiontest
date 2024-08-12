package wellid.patternrecognition.services;

import wellid.patternrecognition.model.bo.LineSegment;
import wellid.patternrecognition.model.bo.Point;
import wellid.patternrecognition.model.bo.Space;
import wellid.patternrecognition.model.exception.InvalidPointException;
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
     * Retrieves a set of line segments formed by aligning points in the space with at least the specified minimum number
     * of points per segment. The method identifies all possible line segments that can be created by aligning the given points.
     * <p>
     * If the total number of points in the space is less than two, an empty set is returned since it's impossible
     * to form a line segment with fewer than two points.
     * </p>
     *
     * @param minPointsForLine the minimum number of points required to form a valid line segment. If this value is 2,
     *                         all possible line segments with exactly two points will be included in the result.
     * @return a set of {@link LineSegment} objects representing the line segments that can be formed from the points
     *         in the space, where each segment contains at least the specified minimum number of aligned points.
     */
    public Set<LineSegment> getLineSegment(int minPointsForLine) {
        List<Point> points = new ArrayList<>(space.getPoints());
        Set<LineSegment> lineSegments = new HashSet<>();

        if(points.size() < 2){
            return lineSegments;
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

        return lineSegments;
    }
}
