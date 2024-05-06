package mateus.bankingapi.controllers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import mateus.bankingapi.models.TransactionType;

import java.math.BigDecimal;

@Data
@Builder
public class TransferCreateRequest {

  @NotNull
  @Positive
  private BigDecimal value;
  @NotNull
  private String sender;
  @NotNull
  private String receiver;

}
