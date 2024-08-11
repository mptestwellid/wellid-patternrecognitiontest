package wellid.patternrecognition.utils;

import wellid.patternrecognition.model.bo.Point;

/**
 * Utility class for pattern recognition involving geometric calculations on points.
 * This class provides methods to validate points, calculate gradients between points,
 * and determine if points are aligned in a line segment.
 * <p>
 * The methods in this class assume that the coordinates of the points are of type {@link Float}.
 * </p>
 *
 * <p>
 * Note: This class does not handle cases where the coordinates of points are {@code null}
 * beyond checking their presence for validation.
 * </p>
 *
 * <h2>Methods Summary</h2>
 * <ul>
 *   <li>{@link #isPointValid(Point)} - Checks if a point has both coordinates set.</li>
 *   <li>{@link #calculateGradient(Point, Point)} - Calculates the gradient between two points.</li>
 *   <li>{@link #isPointsAligned(Point, Point, Point)} - Determines if three points are aligned.</li>
 * </ul>
 */
public class PatternRecognitionUtils {

    /**
     * Checks if the provided point has both coordinates set (i.e., neither coordinate is {@code null}).
     *
     * @param point the {@link Point} to be checked
     * @return {@code true} if both {@code x} and {@code y} coordinates of the point are not {@code null};
     *         {@code false} otherwise
     */
    public static boolean isPointValid(Point point) {
        return point.getX() != null && point.getY() != null;
    }

    /**
     * Calculates the gradient between two points. The gradient is calculated as (deltaY / deltaX).
     * If {@code deltaX} is zero, the method returns {@code 0.0f} to represent an undefined or infinite gradient.
     *
     * @param prev the previous {@link Point}
     * @param next the next {@link Point}
     * @return the gradient between the two points
     */
    public static float calculateGradient(Point prev, Point next) {
        Float zero = 0.0f;
        Float deltaX = next.getX() - prev.getX();
        Float deltaY = next.getY() - prev.getY();
        if(Float.compare(deltaX,zero) == 0)
            return zero;
        return deltaY/deltaX;
    }

    /**
     * Determines if three points are aligned by comparing the gradients between consecutive points.
     *
     * @param p1 the first {@link Point}
     * @param p2 the second {@link Point}
     * @param p3 the third {@link Point}
     * @return {@code true} if the three points are aligned (i.e., the gradients between consecutive points are equal);
     *         {@code false} otherwise
     */
    public static boolean isPointsAligned(Point p1, Point p2 , Point p3) {
        Float gradientP1P2 = calculateGradient(p1,p2);
        Float gradientP2P3 = calculateGradient(p2,p3);
        return Float.compare(gradientP1P2, gradientP2P3) == 0;
    }
}
