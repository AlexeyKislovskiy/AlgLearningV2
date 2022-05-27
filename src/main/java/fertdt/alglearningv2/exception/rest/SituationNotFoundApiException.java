package fertdt.alglearningv2.exception.rest;

import org.springframework.http.HttpStatus;

public class SituationNotFoundApiException extends ApiException {
    public SituationNotFoundApiException() {
        this.message = "Situation not found";
        this.status = HttpStatus.NOT_FOUND;
    }
}
