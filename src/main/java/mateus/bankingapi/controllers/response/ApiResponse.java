package mateus.bankingapi.controllers.response;

public final class ApiResponse<T> {

  private final Boolean success;

  private final String message;

  private final T data;

  private ApiResponse(final Boolean success, final String message, final T data) {
    this.success = success;
    this.message = message;
    this.data = data;
  }

  public static <T> ApiResponse<T> of(final T data) {
    return new ApiResponse<>(true, null, data);
  }

  public static ApiResponse<Void> empty() {
    return new ApiResponse<>(true, null, null);
  }

  public static ApiResponse<Void> failure(final String message) {
    return new ApiResponse<>(false, message, null);
  }

  public static <T> ApiResponseBuilder<T> builder() { return new ApiResponseBuilder<T>(); }

  public Boolean getSuccess() { return this.success; }

  public String getMessage() { return this.message; }

  public T getData() { return this.data; }

  public static class ApiResponseBuilder<T> {

    private Boolean success;

    private String message;

    private T data;

    ApiResponseBuilder() { }

    public ApiResponseBuilder<T> success(final Boolean success) {
      this.success = success;
      return this;
    }

    public ApiResponseBuilder<T> message(final String message) {
      this.message = message;
      return this;
    }

    public ApiResponseBuilder<T> data(final T data) {
      this.data = data;
      return this;
    }

    public ApiResponse<T> build() {
      return new ApiResponse<T>(this.success, this.message, this.data);
    }

  }

}
