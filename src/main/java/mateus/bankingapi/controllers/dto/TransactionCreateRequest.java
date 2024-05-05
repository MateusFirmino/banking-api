package mateus.bankingapi.controllers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import mateus.bankingapi.models.Client;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionCreateRequest {
  @NotNull
  @Positive
  private BigDecimal value;
  @NotNull
  private String accountNumber;
}
