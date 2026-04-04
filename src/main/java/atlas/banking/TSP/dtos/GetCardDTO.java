package atlas.banking.TSP.dtos;

import java.time.Instant;

public record GetCardDTO(String userCpf, Instant createdAt) {
}
