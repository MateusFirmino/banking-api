package mateus.bankingapi.controllers.dto;

import lombok.Builder;
import lombok.Data;
import mateus.bankingapi.models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionCreateResponse {
  private Long id;
  private BigDecimal amount;
  private LocalDateTime date;
  private String accountNumber;
  private TransactionType transactionType;
}
