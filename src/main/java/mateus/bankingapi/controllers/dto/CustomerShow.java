package mateus.bankingapi.controllers.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CustomerShow {
  private Long id;
  private String name;
  private LocalDate birthdate;
  private String email;
  private BigDecimal balance;
  private String accountNumber;
}
