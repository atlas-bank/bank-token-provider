package atlas.banking.TSP.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tokenized_cards")
@Getter
@Setter
public class TokenizedCard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String tokenizedPan;
    private String tokenizedCVV;
    private String hashPin;
}
