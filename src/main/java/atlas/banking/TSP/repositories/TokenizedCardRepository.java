package atlas.banking.TSP.repositories;

import atlas.banking.TSP.models.TokenizedCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenizedCardRepository extends JpaRepository<TokenizedCard, Long> {
    List<TokenizedCard> findByhashUserCpf(String hash);
}
