package mateus.bankingapi.controllers.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class ClientShow {
  private Long id;
  private String name;
  private Date birthdate;
  private String email;
  private BigDecimal balance;
  private String accountNumber;
}
