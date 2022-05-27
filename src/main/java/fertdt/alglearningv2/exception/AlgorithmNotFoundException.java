package fertdt.alglearningv2.exception;

public class AlgorithmNotFoundException extends AlgLearningException{
    public AlgorithmNotFoundException() {
    }

    public AlgorithmNotFoundException(String message) {
        super(message);
    }

    public AlgorithmNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlgorithmNotFoundException(Throwable cause) {
        super(cause);
    }
}
