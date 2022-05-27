package fertdt.alglearningv2.handler;

import fertdt.alglearningv2.dto.response.ApiExceptionResponse;
import fertdt.alglearningv2.exception.rest.ApiException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse> handleApiException(ApiException e) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .status(e.getStatus().value())
                .error(e.getStatus().getReasonPhrase())
                .message(e.getMessage())
                .build();
        log.info("Was handled ApiException: " + apiExceptionResponse);
        return ResponseEntity
                .status(e.getStatus())
                .body(apiExceptionResponse);
    }

    @SneakyThrows
    @ExceptionHandler(Exception.class)
    public void handleAnotherException(Exception e) {
        log.warn("Exception as occurred: " + e.getMessage());
        throw e;
    }

}
