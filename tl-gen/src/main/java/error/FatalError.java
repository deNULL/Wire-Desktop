package error;

public class FatalError extends RuntimeException {

    public FatalError(String message, Throwable cause) {
        super(message, cause);
    }

    public FatalError(String message) {
        super(message);
    }
}
