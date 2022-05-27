package fertdt.alglearningv2.exception;

public class SameAlgorithmsAlreadyExistException extends AlgLearningException{
    public SameAlgorithmsAlreadyExistException() {
    }

    public SameAlgorithmsAlreadyExistException(String message) {
        super(message);
    }

    public SameAlgorithmsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public SameAlgorithmsAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
