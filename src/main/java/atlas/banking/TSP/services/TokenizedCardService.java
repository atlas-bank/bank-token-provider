package atlas.banking.TSP.services;

import atlas.banking.TSP.dtos.CreateCardDTO;
import atlas.banking.TSP.dtos.GetCardDTO;
import atlas.banking.TSP.models.TokenizedCard;
import atlas.banking.TSP.repositories.TokenizedCardRepository;
import atlas.banking.TSP.utils.Generator;
import atlas.banking.TSP.utils.Hash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class TokenizedCardService {

    private final TokenizedCardRepository repository;
    private final Generator generator;
    private final Hash hash;


    public TokenizedCardService(TokenizedCardRepository repository, Generator generator, Hash hash) {
        this.repository = repository;
        this.generator = generator;
        this.hash = hash;
    }

    private String generateTokenizedPan(String cpf, Instant createdAt) {
        return generator.generatePan(cpf, createdAt);
    }

    private String generateTokenizedCVV(String cpf, String pan, Instant createdAt) {
        return generator.generateCVV(cpf, pan, createdAt);
    }

    private String hashPin(String pin) {
        return hash.encode(pin);
    }

    private String hashUserCpf(String cpf) {

        return generator.deterministicHashCpf(cpf);
    }

    public List<TokenizedCard> getTokenizedCards(GetCardDTO dto) {
        String hashCpf = generator.deterministicHashCpf(dto.userCpf());

        List<TokenizedCard> cards = repository.findByhashUserCpf(hashCpf);

        cards.removeIf(card -> {
            String calculatedCvv = generator.generateCVV(dto.userCpf(), card.getTokenizedPan(), dto.createdAt());
            if (!calculatedCvv.equals(card.getTokenizedCvv())) {
                log.warn("Cartão {} com CVV inválido. Calculado: {}, armazenado: {}",
                        card.getTokenizedPan(),
                        calculatedCvv,
                        card.getTokenizedCvv());
                return true;
            }
            return false;
        });
        return cards;
    }

    public String tokenizeCard(CreateCardDTO card) {

        TokenizedCard tokenizedCard = new TokenizedCard();
        tokenizedCard.setHashUserCpf(hashUserCpf(card.userCpf()));

        tokenizedCard.setTokenizedPan(generateTokenizedPan(card.userCpf(), card.createdAt()));
        tokenizedCard.setTokenizedCvv(generateTokenizedCVV(card.userCpf(), tokenizedCard.getTokenizedPan(), card.createdAt()));


        tokenizedCard.setHashPin(hashPin(card.pin()));
        return repository.save(tokenizedCard).getId();
    }
}
