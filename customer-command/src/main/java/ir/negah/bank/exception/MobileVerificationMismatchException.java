package ir.negah.bank.exception;

public class MobileVerificationMismatchException extends RuntimeException {
    public MobileVerificationMismatchException(String errorMessage) {
        super(errorMessage);
    }
}
