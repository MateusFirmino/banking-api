package mateus.bankingapi.controllers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionCreateRequest {
  @NotNull
  @Positive
  private BigDecimal amount;
  @NotNull
  private String accountNumber;
}
