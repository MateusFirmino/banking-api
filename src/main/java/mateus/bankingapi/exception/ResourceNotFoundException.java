package mateus.bankingapi.exception;

import java.io.Serial;
import java.util.function.Supplier;

public class ResourceNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 3754102367420708961L;

  public ResourceNotFoundException() {
    super();
  }

  public ResourceNotFoundException(final String message) {
    super(message);
  }

  public ResourceNotFoundException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  public static Supplier<ResourceNotFoundException> error(final String message) {
    return () -> new ResourceNotFoundException(message);
  }

}
