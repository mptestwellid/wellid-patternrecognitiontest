package wellid.patternrecognition.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wellid.patternrecognition.model.bo.Point;
import wellid.patternrecognition.model.exception.InvalidInputException;
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
            @ApiResponse(responseCode = "400", description = "Not Acceptable - Invalid point", content = @Content)
    })
    public ResponseEntity addPoint(@RequestBody Point point) throws InvalidPointException {
            if(point == null) throw new InvalidPointException(MessageEnum.ADD_POINT_ERROR_POINT_NULL.getMessage());
            service.addPoint(point);
            AddPointResponse response = new AddPointResponse(MessageEnum.ADD_POINT_OK.getMessage(), point);
            return ResponseEntity.ok(response);
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
     * Retrieves line segments formed by at least the specified number of aligned points.
     * <p>
     * This method takes a minimum number of points required to form a valid line segment
     * and returns all the line segments that meet or exceed this threshold.
     * If the provided `minPointsForLine` is less than 2, an {@link InvalidInputException} is thrown,
     * indicating that it's impossible to form a line segment with fewer than two points.
     * </p>
     *
     * @param minPointsForLine the minimum number of points required to form a valid line segment.
     *                         Must be at least 2.
     * @return a {@link ResponseEntity} containing a {@link LineSegmentResponse} with the list of line segments.
     * @throws InvalidInputException if the provided `minPointsForLine` is less than 2.
     */
    @GetMapping("/lines/{minPointsForLine}")
    @Operation(
            summary = "Retrieve line segments",
            description = "Returns line segments formed by at least the specified number of aligned points."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieval of line segments",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LineSegmentResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input: less than 2 points required for a line segment",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity getLines(@PathVariable int minPointsForLine) {
        if(minPointsForLine < 2) throw new InvalidInputException(MessageEnum.LINE_SEGMENT_ERROR_LESS_TWO_POINTS.getMessage());
        LineSegmentResponse response = new LineSegmentResponse(service.getLineSegment(minPointsForLine));
        return ResponseEntity.ok(response);
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
        ClearSpaceResponse response = new ClearSpaceResponse(MessageEnum.CLEAR_SPACE_OK.getMessage());
        return ResponseEntity.ok(response);
    }
}

