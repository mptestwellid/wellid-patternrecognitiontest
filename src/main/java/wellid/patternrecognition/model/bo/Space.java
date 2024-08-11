package wellid.patternrecognition.model.bo;

import java.util.HashSet;
import java.util.Set;

public class Space {
    private static final Space instance = new Space();
    private Set<Point> points;
    private Space() {
        points = new HashSet<>();
    }
    public static Space getInstance() {
        return instance;
    }
    public Set<Point> getPoints() {
        return points;
    }
}
