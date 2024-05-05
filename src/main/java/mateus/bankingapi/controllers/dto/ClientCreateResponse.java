package mateus.bankingapi.controllers.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ClientCreateResponse {

  private Long id;
  private String name;
  private Integer age;
  private String email;
  private String accountNumber;
  private BigDecimal balance;

}
