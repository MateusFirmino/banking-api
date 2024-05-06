package mateus.bankingapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transaction")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "amount")
  private BigDecimal amount;
  @Column(name = "date")
  @NotNull
  private LocalDateTime date;
  @Column(name = "transaction_type")
  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;
  @Column(name = "accountNumber")
  private String accountNumber;
  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

}
