package mateus.bankingapi.controllers.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CustomerCreateResponse {

  private Long id;
  private String name;
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private LocalDate birthdate;
  private String email;
  private String accountNumber;
  private BigDecimal balance;

}
