package ir.negah.bank.domain.dto;

public record ShahkarResponseDTO(Integer response, String comment, String errorCode, String errorMessage) {

    public void errorMessage(String errorDesc) {
    }

    public void errorCode(String toString) {
    }
}
