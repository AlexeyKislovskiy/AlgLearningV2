package fertdt.alglearningv2.exception;

public class MapboxException extends AlgLearningException{
    public MapboxException() {
    }

    public MapboxException(String message) {
        super(message);
    }

    public MapboxException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapboxException(Throwable cause) {
        super(cause);
    }
}
