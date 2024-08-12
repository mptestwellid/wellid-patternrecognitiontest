package wellid.patternrecognition.model.response;

public enum MessageEnum {
    ADD_POINT_OK("Point added"),
    ADD_POINT_ERROR_POINT_NULL("The point is mandatory"),
    ALL_POINTS_OK("All points retrived"),
    CLEAR_SPACE_OK("All points removed"),
    LINE_SEGMENT_OK("All line segment retrived"),
    LINE_SEGMENT_ERROR_LESS_TWO_POINTS("To obtain a line segment you need at least two points"),
    LINE_SEGMENT_ERROR_NULL_PARAM("The parameter 'minPointsForLine' is mandatory"),
    LINE_SEGMENT_ERROR_INVALID_PARAM("Invalid number format: ");

    private final String message;

    private MessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
