package atlas.banking.TSP.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponseRecord(Object data, String message, HttpStatus status) {
}
