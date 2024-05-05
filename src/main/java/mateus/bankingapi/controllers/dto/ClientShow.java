package mateus.bankingapi.controllers.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ClientShow {
  private Long id;
  private String name;
  private Integer age;
  private String email;
  private BigDecimal balance;
  private String accountNumber;
}
