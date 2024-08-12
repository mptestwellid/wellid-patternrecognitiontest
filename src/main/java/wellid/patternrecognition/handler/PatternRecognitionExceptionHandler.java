package wellid.patternrecognition.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wellid.patternrecognition.model.exception.InvalidInputException;
import wellid.patternrecognition.model.exception.InvalidPointException;
import wellid.patternrecognition.model.exception.ResourceNotFoundException;
import wellid.patternrecognition.model.response.ErrorResponse;
import wellid.patternrecognition.model.response.MessageEnum;

import java.time.LocalDateTime;

/**
 * Global exception handler for the Pattern Recognition API.
 * <p>
 * This class is annotated with {@link RestControllerAdvice}, which makes it a centralized
 * exception handling component for all controllers within the application. It catches specific exceptions
 * and returns appropriate HTTP responses with custom error messages.
 * </p>
 * <p>
 * The handled exceptions include:
 * <ul>
 *   <li>{@link InvalidInputException}: Thrown when an input validation fails.</li>
 *   <li>{@link InvalidPointException}: Thrown when a point-related validation fails.</li>
 *   <li>{@link ResourceNotFoundException}: Thrown when a requested resource is not found.</li>
 *   <li>{@link NumberFormatException}: Thrown when a number format conversion fails.</li>
 * </ul>
 * </p>
 */
@RestControllerAdvice
public class PatternRecognitionExceptionHandler {

    /**
     * Handles {@link InvalidInputException} and returns a response with a 400 Bad Request status.
     *
     * @param ex      the exception that was thrown
     * @param request the HTTP request during which the exception occurred
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with the error details
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link InvalidPointException} and returns a response with a 400 Bad Request status.
     *
     * @param ex      the exception that was thrown
     * @param request the HTTP request during which the exception occurred
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with the error details
     */
    @ExceptionHandler(InvalidPointException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPointException(InvalidPointException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ResourceNotFoundException} and returns a response with a 404 Not Found status.
     *
     * @param ex      the exception that was thrown
     * @param request the HTTP request during which the exception occurred
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with the error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                MessageEnum.LINE_SEGMENT_ERROR_NULL_PARAM.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link NumberFormatException} and returns a response with a 400 Bad Request status.
     *
     * @param ex      the exception that was thrown
     * @param request the HTTP request during which the exception occurred
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with the error details
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponse> handleNumberFormatException(NumberFormatException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                MessageEnum.LINE_SEGMENT_ERROR_INVALID_PARAM.getMessage() + ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
