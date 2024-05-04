package mateus.bankingapi.controllers.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientShowAll {
  private Long id;
  private String name;
  private Integer age;
  private String email;
  private String accountNumber;
}
