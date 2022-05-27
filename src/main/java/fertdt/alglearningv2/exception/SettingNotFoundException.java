package fertdt.alglearningv2.exception;

public class SettingNotFoundException extends AlgLearningException{
    public SettingNotFoundException() {
    }

    public SettingNotFoundException(String message) {
        super(message);
    }

    public SettingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SettingNotFoundException(Throwable cause) {
        super(cause);
    }
}
