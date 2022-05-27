package fertdt.alglearningv2.exception.rest;

import fertdt.alglearningv2.exception.AlgLearningException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends AlgLearningException {
    protected HttpStatus status;
    protected String message;
}