package atlas.banking.TSP.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tokenized_cards")
@Getter
@Setter
public class TokenizedCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JsonIgnore
    @OneToOne(mappedBy = "tokenizedCard")
    private Card card;

    @Column(unique = true)
    private String tokenizedPan;

    @Column(unique = true)
    private String tokenizedCvv;

    @JsonIgnore
    private String hashPin;

    @JsonIgnore
    private LocalDate createAt;
    @JsonIgnore
    private LocalDate updatedAt;

    public TokenizedCard() {
        LocalDate date = LocalDate.now();
        this.createAt = date;
        this.updatedAt = date;
    }


}
