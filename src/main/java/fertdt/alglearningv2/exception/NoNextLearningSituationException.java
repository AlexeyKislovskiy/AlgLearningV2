package fertdt.alglearningv2.exception;

public class NoNextLearningSituationException extends AlgLearningException{
    public NoNextLearningSituationException() {
    }

    public NoNextLearningSituationException(String message) {
        super(message);
    }

    public NoNextLearningSituationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoNextLearningSituationException(Throwable cause) {
        super(cause);
    }
}
