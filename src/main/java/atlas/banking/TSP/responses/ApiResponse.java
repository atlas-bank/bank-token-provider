package atlas.banking.TSP.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {
    public ResponseEntity<ApiResponseRecord> of(Object data, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponseRecord(data, message, status));
    }
}
