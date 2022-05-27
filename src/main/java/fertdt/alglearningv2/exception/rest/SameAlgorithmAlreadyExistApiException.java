package fertdt.alglearningv2.exception.rest;

import org.springframework.http.HttpStatus;

public class SameAlgorithmAlreadyExistApiException extends ApiException {
    public SameAlgorithmAlreadyExistApiException() {
        this.message = "Algorithm for this situation with this text already exist";
        this.status = HttpStatus.CONFLICT;
    }
}
