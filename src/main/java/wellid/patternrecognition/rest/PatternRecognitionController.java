package wellid.patternrecognition.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wellid.patternrecognition.model.bo.Point;
import wellid.patternrecognition.model.exception.InvalidPointException;
import wellid.patternrecognition.model.response.*;
import wellid.patternrecognition.services.PatternRecognitionService;

/**
 * Controller for handling HTTP requests related to pattern recognition.
 * This controller provides endpoints to add points, retrieve points, find line segments,
 * and clear the space of points.
 * <p>
 * The controller uses {@link PatternRecognitionService} to manage the points and lines.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class PatternRecognitionController {

    private final PatternRecognitionService service = new PatternRecognitionService();

    /**
     * Adds a new point to the space.
     *
     * @param point the {@link Point} object to be added to the space
     * @return A {@link ResponseEntity} containing a {@link AddPointResponse} object.
     *         The response includes a message indicating success and the details
     *         of the added point.
     * @throws InvalidPointException if the point is invalid
     */
    @PostMapping("/point")
    @Operation(
            summary = "Add a new point to the space",
            description = "Adds a new point to the space and returns the details of the added point in the response."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Point added", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddPointResponse.class))),
            @ApiResponse(responseCode = "406", description = "Not Acceptable - Invalid point", content = @Content)
    })
    public ResponseEntity addPoint(@RequestBody Point point) {
        try {
            service.addPoint(point);
            AddPointResponse response = new AddPointResponse("Point added",point);
            return ResponseEntity.ok(response);
        } catch (InvalidPointException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Retrieves all points currently present in the space.
     *
     * @return A {@link ResponseEntity} containing an {@link AllPointsResponse} object,
     *         which includes a list of all points in the space.
     */
    @GetMapping("/space")
    @Operation(
            summary = "Retrieve all points in the space",
            description = "Fetches all points currently present in the space and returns them in a JSON response."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved points",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AllPointsResponse.class))
    )
    public ResponseEntity<AllPointsResponse> getAllPoints() {
        AllPointsResponse response = new AllPointsResponse(service.getAllPoints());
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all line segments formed by points in the space that meet the specified minimum number of points.
     * This endpoint delegates the processing to the service layer, which calculates the line segments and returns the results.
     *
     * <p>The method handles HTTP GET requests directed to the "/lines/{minPointsForLine}" URL, where `minPointsForLine` is
     * the minimum number of aligned points required to form a line segment.
     *
     * @param minPointsForLine The minimum number of aligned points required to form a line segment.
     * Must be 2 or greater.
     *
     * @return {@link ResponseEntity} containing the set of line segments that meet the specified criteria, or an error message
     * if the input is invalid. The response can be an error with {@link HttpStatus#BAD_REQUEST} if `minPointsForLine` is
     * less than 2.
     */
    @GetMapping("/lines/{minPointsForLine}")
    @Operation(summary = "Retrieve line segments", description = "Returns line segments formed by at least the specified number of aligned points.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of line segments",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LineSegmentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input: less than 2 points required for a line segment",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity getLines(@PathVariable int minPointsForLine) {
        return service.getLineSegment(minPointsForLine);
    }

    /**
     * Clears all points from the space.
     *
     * This endpoint handles a DELETE request to remove all saved points
     * from the service. After performing the operation, it returns a response
     * with a message confirming that all points have been removed.
     *
     * @return an HTTP response with a 200 OK status code and a body containing
     *         a confirmation message that all points have been removed.
     */
    @DeleteMapping("/space")
    @Operation(
            summary = "Clear all points from the space",
            description = "Removes all saved points from the service and returns a confirmation message.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All points have been removed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClearSpaceResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<ClearSpaceResponse> clearSpace() {
        service.clearPoints();
        ClearSpaceResponse response = new ClearSpaceResponse("All points removed");
        return ResponseEntity.ok(response);
    }
}

