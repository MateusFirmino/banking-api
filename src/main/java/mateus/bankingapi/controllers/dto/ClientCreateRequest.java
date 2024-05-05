package mateus.bankingapi.controllers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientCreateRequest {

  @NotNull
  private String name;
  @NotNull
  private Integer age;
  @NotNull
  private String email;

}
