package polovinko.leontii.tophitsanalyzer.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import polovinko.leontii.tophitsanalyzer.dto.error.ErrorResponseBody;
import polovinko.leontii.tophitsanalyzer.exception.TopHitsAnalyzerValidationException;

class GlobalExceptionHandlerUnitTest {

   private GlobalExceptionHandler handler;

   @BeforeEach
   void setUp() {
      handler = new GlobalExceptionHandler();
   }

   @Test
   void handleException_whenMethodArgumentTypeMismatchExceptionOccurred_thenErrorResponseBodyReturned() {
      MethodArgumentTypeMismatchException exception =
          new MethodArgumentTypeMismatchException(null, null, null, null, null);

      ErrorResponseBody errorResponseBody = handler.handleException(exception);

      assertEquals("Invalid request parameters", errorResponseBody.getMessage());
      assertNotNull(errorResponseBody.getHappenedAt());
   }

   @Test
   void handleException_whenTopHitsAnalyzerValidationExceptionOccurred_thenErrorResponseBodyReturned() {
      TopHitsAnalyzerValidationException exception = new TopHitsAnalyzerValidationException("Error message");

      ErrorResponseBody errorResponseBody = handler.handleException(exception);

      assertEquals(exception.getMessage(), errorResponseBody.getMessage());
      assertNotNull(errorResponseBody.getHappenedAt());
   }

   @Test
   void handleException_whenRuntimeExceptionOccurred_thenErrorMessageReturned() {
      RuntimeException exception = new RuntimeException("Error message");

      ErrorResponseBody errorResponseBody = handler.handleException(exception);

      assertEquals("Internal Server Error", errorResponseBody.getMessage());
      assertNotNull(errorResponseBody.getHappenedAt());
   }

   @Test
   void handleException_whenMissingServletRequestParameterExceptionOccurred_thenErrorMessageReturned() {
      MissingServletRequestParameterException exception = new MissingServletRequestParameterException("", "");

      ErrorResponseBody errorResponseBody = handler.handleException(exception);

      assertEquals("Request must contain 'colname' parameter", errorResponseBody.getMessage());
      assertNotNull(errorResponseBody.getHappenedAt());
   }
}
