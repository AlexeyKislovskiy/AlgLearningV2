package fertdt.alglearningv2.exception;

public class CuberNotFoundException extends AlgLearningException{
    public CuberNotFoundException() {
    }

    public CuberNotFoundException(String message) {
        super(message);
    }

    public CuberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CuberNotFoundException(Throwable cause) {
        super(cause);
    }
}
