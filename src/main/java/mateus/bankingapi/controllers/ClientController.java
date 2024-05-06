package mateus.bankingapi.controllers;

import mateus.bankingapi.controllers.dto.ClientBalanceResponse;
import mateus.bankingapi.controllers.dto.ClientCreateRequest;
import mateus.bankingapi.controllers.dto.ClientCreateResponse;
import mateus.bankingapi.controllers.dto.ClientShow;
import mateus.bankingapi.controllers.dto.TransactionCreateRequest;
import mateus.bankingapi.controllers.dto.TransactionCreateResponse;
import mateus.bankingapi.controllers.dto.TransactionShow;
import mateus.bankingapi.controllers.response.ApiResponse;
import mateus.bankingapi.services.ClientService;
import mateus.bankingapi.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;


@RestController
@RequestMapping("/clients")
public class ClientController {

  private final ClientService clientService;
  private final TransactionService transactionService;

  public ClientController(
    ClientService clientService,
    TransactionService transactionService
  ) {
    this.clientService = clientService;
    this.transactionService = transactionService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<ClientCreateResponse>> createClient(@RequestBody ClientCreateRequest createRequest) {
    final var response = this.clientService.create(createRequest);
    final var location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(response.getId())
      .toUri();
    return ResponseEntity.created(location).body(ApiResponse.of(response));
  }

  @GetMapping()
  public ResponseEntity<ApiResponse<Page<ClientShow>>> findAll(Pageable pageable) {
    Page<ClientShow> page = this.clientService.findAll(pageable);
    return ResponseEntity.ok(ApiResponse.of(page));
  }

  @GetMapping("/{accountNumber}")
  public ResponseEntity<ApiResponse<Page<TransactionShow>>> extractTransaction(
    @PathVariable final String accountNumber,
    Pageable pageable
  ) {
    Page<TransactionShow> page = this.transactionService.transactionsByClient(accountNumber, pageable);
    return ResponseEntity.ok(ApiResponse.of(page));
  }

  @GetMapping("/balances")
  public ResponseEntity<ApiResponse<Page<ClientBalanceResponse>>> getAllClientBalances(Pageable pageable) {
    Page<ClientBalanceResponse> balancesPage = clientService.getAllClientBalances(pageable);
    return ResponseEntity.ok(ApiResponse.of(balancesPage));
  }

  @GetMapping("/transactionsToday")
  public ResponseEntity<ApiResponse<Page<TransactionShow>>> getAllTransactionsForToday(
    @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
    Pageable pageable
  ) {
    Page<TransactionShow> transactionsPage = transactionService.getAllTransactionsForToday(date, pageable);
    return ResponseEntity.ok(ApiResponse.of(transactionsPage));
  }

  @PostMapping("/deposit")
  public ResponseEntity<ApiResponse<TransactionCreateResponse>> deposit(@RequestBody TransactionCreateRequest transactionCreateRequest) {
    final var response = this.transactionService.deposit(transactionCreateRequest);
    return ResponseEntity.ok(ApiResponse.of(response));
  }

  @PostMapping("/withdraw")
  public ResponseEntity<ApiResponse<TransactionCreateResponse>> withdraw(@RequestBody TransactionCreateRequest transactionCreateRequest) {
    final var response = this.transactionService.withdraw(transactionCreateRequest);
    return ResponseEntity.ok(ApiResponse.of(response));
  }

}
