package fertdt.alglearningv2.exception;

public class CubeNotFoundException extends AlgLearningException {
    public CubeNotFoundException() {
    }

    public CubeNotFoundException(String message) {
        super(message);
    }

    public CubeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CubeNotFoundException(Throwable cause) {
        super(cause);
    }
}
