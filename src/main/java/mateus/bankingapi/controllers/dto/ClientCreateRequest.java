package mateus.bankingapi.controllers.dto;

import lombok.Data;

@Data
public class ClientCreateRequest {
  private String name;
  private Integer age;
  private String email;
  private String accountNumber;
}
