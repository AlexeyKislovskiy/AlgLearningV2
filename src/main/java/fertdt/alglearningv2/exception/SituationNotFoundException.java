package fertdt.alglearningv2.exception;

public class SituationNotFoundException extends AlgLearningException{
    public SituationNotFoundException() {
    }

    public SituationNotFoundException(String message) {
        super(message);
    }

    public SituationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SituationNotFoundException(Throwable cause) {
        super(cause);
    }
}
