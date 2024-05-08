package mateus.bankingapi.controllers;

import jakarta.validation.Valid;
import mateus.bankingapi.controllers.dto.CustomerBalanceResponse;
import mateus.bankingapi.controllers.dto.CustomerCreateRequest;
import mateus.bankingapi.controllers.dto.CustomerCreateResponse;
import mateus.bankingapi.controllers.dto.CustomerShow;
import mateus.bankingapi.controllers.dto.TransactionCreateRequest;
import mateus.bankingapi.controllers.dto.TransactionCreateResponse;
import mateus.bankingapi.controllers.dto.TransactionShow;
import mateus.bankingapi.controllers.response.ApiResponse;
import mateus.bankingapi.services.CustomerService;
import mateus.bankingapi.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Validated
@RestController
@RequestMapping("/customer")
public class CustomerController {

  private final CustomerService customerService;
  private final TransactionService transactionService;

  public CustomerController(
    CustomerService customerService,
    TransactionService transactionService
  ) {
    this.customerService = customerService;
    this.transactionService = transactionService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<CustomerCreateResponse>> createCustomer(@Valid @RequestBody CustomerCreateRequest createRequest) {
    final var response = this.customerService.create(createRequest);
    final var location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(response.getId())
      .toUri();
    return ResponseEntity.created(location).body(ApiResponse.of(response));
  }

  @GetMapping()
  public ResponseEntity<ApiResponse<Page<CustomerShow>>> findAll(Pageable pageable) {
    Page<CustomerShow> page = this.customerService.findAll(pageable);
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
  public ResponseEntity<ApiResponse<Page<CustomerBalanceResponse>>> getAllClientBalances(Pageable pageable) {
    Page<CustomerBalanceResponse> balancesPage = customerService.getAllClientBalances(pageable);
    return ResponseEntity.ok(ApiResponse.of(balancesPage));
  }

  @GetMapping("/transactionsToday")
  public ResponseEntity<ApiResponse<Page<TransactionShow>>> getAllTransactionsForToday(
    Pageable pageable
  ) {
    Page<TransactionShow> transactionsPage = transactionService.getAllTransactionsForToday( pageable);
    return ResponseEntity.ok(ApiResponse.of(transactionsPage));
  }

  @PostMapping("/deposit")
  public ResponseEntity<ApiResponse<TransactionCreateResponse>> deposit(@Valid @RequestBody TransactionCreateRequest transactionCreateRequest) {
    final var response = this.transactionService.deposit(transactionCreateRequest);
    return ResponseEntity.ok(ApiResponse.of(response));
  }

  @PostMapping("/withdraw")
  public ResponseEntity<ApiResponse<TransactionCreateResponse>> withdraw(@Valid @RequestBody TransactionCreateRequest transactionCreateRequest) {
    final var response = this.transactionService.withdraw(transactionCreateRequest);
    return ResponseEntity.ok(ApiResponse.of(response));
  }

}
