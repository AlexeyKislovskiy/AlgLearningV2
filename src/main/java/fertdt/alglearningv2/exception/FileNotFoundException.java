package fertdt.alglearningv2.exception;

public class FileNotFoundException extends AlgLearningException {
    public FileNotFoundException() {
    }

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundException(Throwable cause) {
        super(cause);
    }
}
