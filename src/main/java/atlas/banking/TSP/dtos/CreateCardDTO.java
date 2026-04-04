package atlas.banking.TSP.dtos;

import java.time.Instant;

public record CreateCardDTO(String userCpf, String pin, Instant createdAt) {
}
