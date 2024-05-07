package mateus.bankingapi.controllers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CustomerCreateRequest {

  @NotNull
  private String name;
  @NotNull
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date birthdate;
  @NotNull
  private String email;

}
