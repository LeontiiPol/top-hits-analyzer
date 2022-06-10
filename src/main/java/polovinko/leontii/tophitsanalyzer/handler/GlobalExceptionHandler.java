package polovinko.leontii.tophitsanalyzer.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import polovinko.leontii.tophitsanalyzer.dto.error.ErrorResponseBody;
import polovinko.leontii.tophitsanalyzer.exception.TopHitsAnalyzerValidationException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

   @ExceptionHandler(MethodArgumentTypeMismatchException.class)
   @ResponseStatus(BAD_REQUEST)
   public ErrorResponseBody handleException(MethodArgumentTypeMismatchException e) {
      return new ErrorResponseBody("Invalid request parameters", LocalDateTime.now());
   }

   @ExceptionHandler(MissingServletRequestParameterException.class)
   @ResponseStatus(BAD_REQUEST)
   public ErrorResponseBody handleException(MissingServletRequestParameterException e) {
      return new ErrorResponseBody("Request must contain 'colname' parameter", LocalDateTime.now());
   }

   @ExceptionHandler(TopHitsAnalyzerValidationException.class)
   @ResponseStatus(BAD_REQUEST)
   public ErrorResponseBody handleException(TopHitsAnalyzerValidationException e) {
      return new ErrorResponseBody(e.getMessage(), LocalDateTime.now());
   }

   @ExceptionHandler(RuntimeException.class)
   @ResponseStatus(INTERNAL_SERVER_ERROR)
   public ErrorResponseBody handleException(RuntimeException e) {
      log.error(e.getMessage());
      return new ErrorResponseBody("Internal Server Error", LocalDateTime.now());
   }
}
