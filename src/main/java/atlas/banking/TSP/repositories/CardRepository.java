package atlas.banking.TSP.repositories;

import atlas.banking.TSP.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    Card getCardById(Long id);

    List<Card> getCardByuserCPF(String userCPF);
}
