package mateus.bankingapi.controllers.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class ClientCreateResponse {

  private Long id;
  private String name;
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date birthdate;
  private String email;
  private String accountNumber;
  private BigDecimal balance;

}
