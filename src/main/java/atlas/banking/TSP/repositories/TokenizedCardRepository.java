package atlas.banking.TSP.repositories;

import atlas.banking.TSP.models.TokenizedCard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenizedCardRepository extends JpaRepository<TokenizedCard, Long> {
}
