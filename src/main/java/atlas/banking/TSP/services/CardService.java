package atlas.banking.TSP.services;


import atlas.banking.TSP.dtos.CreateCardDTO;
import atlas.banking.TSP.models.Card;
import atlas.banking.TSP.models.TokenizedCard;
import atlas.banking.TSP.repositories.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
public class CardService {

    private final CardRepository repository;
    private final TokenizedCardService tokenizationService;

    public CardService(CardRepository repository, TokenizedCardService tokenizationService) {
        this.repository = repository;
        this.tokenizationService = tokenizationService;
    }


    public Card validateAndCreateCard(CreateCardDTO dto) {
        dto.validate();
        return createCard(dto);
    }

    private String getCardHolderName(String fullname) {
        // pega o primeiro e último nome do usuário
        String[] fullnameArray = fullname.trim().split(" ");
        String first = fullnameArray[0];
        String last = fullnameArray[fullnameArray.length - 1];
        return (first + " " + last).toUpperCase();
    }

    private Card createCard(CreateCardDTO dto) {
        Instant createdAt = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        TokenizedCard tokenizedCard = tokenizationService.tokenizeCard(dto.userCPF(), dto.pin(), createdAt);
        Card card = new Card(
                tokenizedCard,
                dto.userCPF(),
                dto.brand(),
                dto.userFullName(),
                getCardHolderName(dto.userFullName()),
                dto.deviceId(),
                dto.international(),
                createdAt
        );

        repository.save(card);
        log.info("Gerado cartão com PAN: {}", card.getTokenizedCard().getTokenizedPan());
        return card;
    }

    public List<Card> getCardsByCpf(String cpf) {
        List<Card> cards = repository.getCardByuserCPF(cpf);
        System.out.println(cards.toArray().length);
        cards.removeIf(card -> {
            TokenizedCard tokenizedCard = card.getTokenizedCard();
            return !tokenizationService.validateCVV(
                    card.getUserCPF(),
                    tokenizedCard
            );
        });

        return cards;
    }
}
