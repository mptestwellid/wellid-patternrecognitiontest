package wellid.patternrecognition.model.exception;

import wellid.patternrecognition.model.bo.Point;

public class InvalidPointException extends Exception {
    public InvalidPointException(Point point){
        super("'x' and 'y' coordinates are both mandatory: " + point);
    }
    public InvalidPointException(String message){
        super(message);
    }
}
