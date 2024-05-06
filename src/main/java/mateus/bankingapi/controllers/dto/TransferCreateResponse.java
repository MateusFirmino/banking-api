package mateus.bankingapi.controllers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import mateus.bankingapi.models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransferCreateResponse {

  @NotNull
  @Positive
  private BigDecimal value;
  @NotNull
  private String sender;
  @NotNull
  private String receiver;
  private LocalDateTime date;

}
