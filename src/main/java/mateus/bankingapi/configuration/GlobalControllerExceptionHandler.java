package mateus.bankingapi.configuration;

import mateus.bankingapi.controllers.response.ApiResponse;
import mateus.bankingapi.exception.BusinessException;
import mateus.bankingapi.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ApiResponse<?>> handleConflict(final RuntimeException exception) {
    exception.printStackTrace();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.failure(exception.getMessage()));
  }

  @ExceptionHandler({RuntimeException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ApiResponse<?>> handleUnexpectedException(final RuntimeException exception) {
    exception.printStackTrace();
    return ResponseEntity.internalServerError().body(ApiResponse.failure(exception.getMessage()));
  }

  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiResponse<?>> handleBusinessException(final BusinessException exception) {
    exception.printStackTrace();
    return ResponseEntity.badRequest().body(ApiResponse.failure(exception.getMessage()));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(final ResourceNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure(exception.getMessage()));
  }

  @Override
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    final MethodArgumentNotValidException ex,
    final HttpHeaders headers,
    final HttpStatusCode status,
    final WebRequest request
  ) {
    final var fieldErrors = ex.getBindingResult().getFieldErrors();
    final var message = fieldErrors.stream().findFirst()
      .map(error -> error.getField() + ": " + Objects.requireNonNull(error.getDefaultMessage()))
      .orElse(null);
    return ResponseEntity.badRequest().body(ApiResponse.failure(message));
  }

  @Override
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
    final HttpMessageNotReadableException ex,
    final HttpHeaders headers,
    final HttpStatusCode status,
    final WebRequest request
  ) {
    return ResponseEntity.badRequest().body(ApiResponse.failure(ex.getMessage()));
  }

}