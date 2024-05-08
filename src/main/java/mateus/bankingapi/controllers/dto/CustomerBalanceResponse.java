package mateus.bankingapi.controllers.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CustomerBalanceResponse {

  private String name;
  private String accountNumber;
  private BigDecimal balance;

}
