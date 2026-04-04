package atlas.banking.TSP.controllers;

import atlas.banking.TSP.dtos.CreateCardDTO;
import atlas.banking.TSP.responses.ApiResponse;
import atlas.banking.TSP.responses.ApiResponseRecord;
import atlas.banking.TSP.services.TokenizedCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokenize")
public class CardTokenizerController {
    private final ApiResponse response = new ApiResponse();

    private final TokenizedCardService service;

    public CardTokenizerController(TokenizedCardService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponseRecord> tokenizeCard(@RequestBody CreateCardDTO dto) {

        return response.of(service.tokenizeCard(dto), "Card Tokenized", HttpStatus.CREATED);
    }
}
