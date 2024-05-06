package mateus.bankingapi.exception;

import java.io.Serial;
import java.util.function.Supplier;

public class BusinessException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -2545309060355823897L;

  public BusinessException() {
    super();
  }

  public BusinessException(final String message) {
    super(message);
  }

  public BusinessException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  public static Supplier<BusinessException> error(final String message) {
    return () -> new BusinessException(message);
  }

}
