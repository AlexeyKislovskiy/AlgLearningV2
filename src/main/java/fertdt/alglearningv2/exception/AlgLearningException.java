package fertdt.alglearningv2.exception;

public class AlgLearningException extends RuntimeException {
    public AlgLearningException() {
    }

    public AlgLearningException(String message) {
        super(message);
    }

    public AlgLearningException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlgLearningException(Throwable cause) {
        super(cause);
    }
}
