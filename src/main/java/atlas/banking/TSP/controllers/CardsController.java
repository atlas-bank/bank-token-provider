package atlas.banking.TSP.controllers;

import atlas.banking.TSP.dtos.CreateCardDTO;
import atlas.banking.TSP.responses.ApiResponse;
import atlas.banking.TSP.responses.ApiResponseRecord;
import atlas.banking.TSP.services.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CardsController {
    private final ApiResponse response = new ApiResponse();

    private final CardService service;

    public CardsController(CardService service) {
        this.service = service;
    }


    @GetMapping("/cards/{cpf}")
    public ResponseEntity<ApiResponseRecord> getCards(@PathVariable String cpf) {
        return response.of(service.getCardsByCpf(cpf), "Cards returned", HttpStatus.OK);
    }

    @PostMapping("/card")
    public ResponseEntity<ApiResponseRecord> createCard(@RequestBody CreateCardDTO dto) {
        return response.of(service.validateAndCreateCard(dto), "Card created!", HttpStatus.CREATED);
    }
}
