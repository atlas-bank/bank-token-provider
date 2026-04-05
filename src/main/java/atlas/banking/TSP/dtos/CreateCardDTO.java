package atlas.banking.TSP.dtos;


import atlas.banking.TSP.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record CreateCardDTO(
        String userCPF,
        String pin,
        String userFullName,
        String deviceId,
        String brand,
        boolean international
) {
    public void validate() {
        checkNull(userCPF, "CPF is NULL");
        checkNull(userFullName, "Username is NULL");
        checkNull(deviceId, "DeviceID is NULL");
        checkNull(brand, "Brand is NULL");
        checkNull(international, "International is NULL");
        checkPin();
        log.info("CreateCardDTO successfully validated");
    }

    private void checkNull(Object obj, String message) throws BadRequestException {
        if (obj == null) {
            log.warn("Invalid field: {} \nReason: null", message.split(" ")[0]);
            throw new BadRequestException(message);
        }
    }

    private void checkPin() throws BadRequestException {
        String error_message = "Invalid pin format";
        if (pin == null || pin.contains("\\d{4}")) {
            log.warn(error_message);
            throw new BadRequestException(error_message);
        }
        if (pin.chars().distinct().count() == 1) {
            log.warn(error_message);

            throw new BadRequestException(error_message);
        }
        if (isSequential(pin)) {
            log.warn(error_message);

            throw new BadRequestException(error_message);
        }
        if (pin.substring(0, 2).equals(pin.substring(2))) {
            log.warn(error_message);

            throw new BadRequestException(error_message);
        }
    }

    private static boolean isSequential(String pin) {
        boolean ascending = true;
        boolean descending = true;

        for (int i = 0; i < pin.length() - 1; i++) {
            int current = pin.charAt(i) - '0';
            int next = pin.charAt(i + 1) - '0';

            if (ascending && next != current + 1) {
                ascending = false;
            }

            if (descending && next != current - 1) {
                descending = false;
            }

            if (!ascending && !descending) {
                return false;
            }
        }

        return true;
    }
}
