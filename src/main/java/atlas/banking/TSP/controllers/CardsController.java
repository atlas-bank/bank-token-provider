package atlas.banking.TSP.controllers;

import atlas.banking.TSP.dtos.GetCardDTO;
import atlas.banking.TSP.responses.ApiResponse;
import atlas.banking.TSP.responses.ApiResponseRecord;
import atlas.banking.TSP.services.TokenizedCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {
    private final ApiResponse response = new ApiResponse();

    private final TokenizedCardService service;


    public CardsController(TokenizedCardService service) {
        this.service = service;
    }

    @GetMapping("/cards")
    public ResponseEntity<ApiResponseRecord> getCards(@RequestBody GetCardDTO dto) {
        return response.of(service.getTokenizedCards(dto), "Cards returned", HttpStatus.OK);
    }
}
