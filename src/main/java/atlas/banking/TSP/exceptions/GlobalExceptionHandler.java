package atlas.banking.TSP.exceptions;


import atlas.banking.TSP.responses.ApiResponse;
import atlas.banking.TSP.responses.ApiResponseRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ApiResponse response = new ApiResponse();

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponseRecord> unauthorizedException(UnauthorizedException ex) {
        return response.of(null, ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponseRecord> badRequestException(BadRequestException ex) {
        return response.of(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseRecord> exception(Exception ex) {
        System.out.println(ex.getMessage());
        return response.of(null, "Erro na aplicação", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
