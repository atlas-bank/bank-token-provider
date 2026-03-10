package atlas.banking.TSP.controllers;

import atlas.banking.TSP.dtos.CardDTO;
import atlas.banking.TSP.responses.ApiResponse;
import atlas.banking.TSP.responses.ApiResponseRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/tokenize")
public class CardTokenizerController {
    private final ApiResponse response = new ApiResponse();

    @PostMapping
    public ResponseEntity<ApiResponseRecord> tokenizeCard(@RequestBody CardDTO dto){
        Random random = new Random();
        String randomId = String.valueOf(random.nextInt(1000));

        System.out.println(randomId);

        return response.of(null,"Card Tokenized", HttpStatus.CREATED);
    }
}
