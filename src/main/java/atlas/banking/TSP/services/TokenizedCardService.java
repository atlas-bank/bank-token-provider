package atlas.banking.TSP.services;

import atlas.banking.TSP.models.TokenizedCard;
import atlas.banking.TSP.repositories.TokenizedCardRepository;
import atlas.banking.TSP.utils.Generator;
import atlas.banking.TSP.utils.Hash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

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

    protected boolean validateCVV(String cpf, TokenizedCard tokenizedCard) {
        String generatedCVV = generateTokenizedCVV(cpf, tokenizedCard.getTokenizedPan(), tokenizedCard.getCreateAt());
        if (!generatedCVV.equals(tokenizedCard.getTokenizedCvv())) {
            log.warn("Cartão {} com CVV inválido. Calculado: {}, armazenado: {}",
                    tokenizedCard.getTokenizedPan(),
                    generatedCVV,
                    tokenizedCard.getTokenizedCvv());
            return false;
        }
        return true;
    }

    public TokenizedCard tokenizeCard(String cpf, String pin, Instant createdAt) {

        TokenizedCard tokenizedCard = new TokenizedCard();

        tokenizedCard.setCreateAt(createdAt);
        tokenizedCard.setUpdatedAt(createdAt);

        tokenizedCard.setTokenizedPan(generateTokenizedPan(cpf, createdAt));
        tokenizedCard.setTokenizedCvv(generateTokenizedCVV(cpf, tokenizedCard.getTokenizedPan(), createdAt));


        tokenizedCard.setHashPin(hashPin(pin));
        String id = repository.save(tokenizedCard).getId();
        log.info("Gerado cartão tokenizado com ID: {}", id);
        return tokenizedCard;
    }
}
