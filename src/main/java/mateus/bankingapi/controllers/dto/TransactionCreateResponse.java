package mateus.bankingapi.controllers.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class TransactionCreateResponse {
  private Long id;
  private BigDecimal value;
  private LocalDateTime date;
  private String accountNumber;
}
