package fertdt.alglearningv2.exception.rest;

import org.springframework.http.HttpStatus;

public class AlgorithmNotFoundApiException extends ApiException {
    public AlgorithmNotFoundApiException() {
        this.status = HttpStatus.NOT_FOUND;
        this.message = "Algorithm not found";
    }
}
