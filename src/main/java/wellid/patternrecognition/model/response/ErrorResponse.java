package wellid.patternrecognition.model.response;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String error;
    private LocalDateTime timestamp;
    private int status;
    private String path;

    public ErrorResponse(String error, LocalDateTime timestamp, int status, String path) {
        this.error = error;
        this.timestamp = timestamp;
        this.status = status;
        this.path = path;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}


