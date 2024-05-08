package mateus.bankingapi.controllers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CustomerCreateRequest {

  @NotNull
  private String name;
  @NotNull
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @Past(message = "Birthdate must be in the past")
  private LocalDate birthdate;
  @NotNull
  @Email
  private String email;

}
