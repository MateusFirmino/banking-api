package mateus.bankingapi.controllers.response;

import java.util.ArrayList;
import java.util.Collection;

public final class ApiCollectionResponse<T> {

  private final Boolean success;

  private final Collection<T> data;

  private ApiCollectionResponse(final Boolean success, final Collection<T> data) {
    this.success = success;
    this.data = data;
  }

  public static <T> ApiCollectionResponse<T> of(final Collection<T> data) {
    return new ApiCollectionResponse<>(true, data);
  }

  public static <T> ApiCollectionResponse<T> empty() {
    return new ApiCollectionResponse<>(true, new ArrayList<>());
  }

  public static <T> ApiCollectionResponseBuilder<T> builder() { return new ApiCollectionResponseBuilder<T>(); }

  public Boolean getSuccess() { return this.success; }

  public Collection<T> getData() { return this.data; }

  public static class ApiCollectionResponseBuilder<T> {

    private Boolean success;

    private Collection<T> data;

    ApiCollectionResponseBuilder() { }

    public ApiCollectionResponseBuilder<T> success(final Boolean success) {
      this.success = success;
      return this;
    }

    public ApiCollectionResponseBuilder<T> data(final Collection<T> data) {
      this.data = data;
      return this;
    }

    public ApiCollectionResponse<T> build() {
      return new ApiCollectionResponse<T>(this.success, this.data);
    }

  }

}
