package fertdt.alglearningv2.exception;

public class WcaException extends AlgLearningException{
    public WcaException() {
    }

    public WcaException(String message) {
        super(message);
    }

    public WcaException(String message, Throwable cause) {
        super(message, cause);
    }

    public WcaException(Throwable cause) {
        super(cause);
    }
}
