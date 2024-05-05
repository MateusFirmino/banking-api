package mateus.bankingapi.controllers.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionShow {
  private Long id;
  private String value;
  private LocalDateTime date;
  private String accountNumber;
  private BigDecimal balance;
  private ClientShow client;

}
