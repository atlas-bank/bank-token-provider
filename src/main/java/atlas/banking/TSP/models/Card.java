package atlas.banking.TSP.models;


import atlas.banking.TSP.enums.CardLevelEnum;
import atlas.banking.TSP.enums.CardStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;

@Entity
@Table(name = "cards")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userCPF;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tokenized_card_id", referencedColumnName = "id", unique = true)
    private TokenizedCard tokenizedCard;

    private LocalDate expirationDate;

    private String brand;

    private String fullname;

    private String cardholderName;

    //dispositivo de origem da transação
    private String deviceId;

    //tentativas de senha inválida
    private int failedAttempts;

    @Enumerated(EnumType.STRING)
    private CardStatusEnum status;

    @Enumerated(EnumType.STRING)
    private CardLevelEnum level;

    private boolean international;
    @JsonIgnore
    private Instant createdAt;
    @JsonIgnore
    private Instant updatedAt;


    public Card(
            TokenizedCard tokenizedCard,
            String userCPF,
            String brand,
            String fullname,
            String cardholderName,
            String deviceId,
            boolean international,
            Instant date

    ) {

        this.userCPF = userCPF;
        this.expirationDate = YearMonth.now().plusMonths(30).atEndOfMonth();
        this.brand = brand;
        this.fullname = fullname;
        this.cardholderName = cardholderName;
        this.deviceId = deviceId;
        this.failedAttempts = 0;
        this.status = CardStatusEnum.NOT_ACTIVE;
        this.level = CardLevelEnum.STANDARD;
        this.international = international;
        this.createdAt = date;
        this.updatedAt = date;
        this.tokenizedCard = tokenizedCard;

    }


}
