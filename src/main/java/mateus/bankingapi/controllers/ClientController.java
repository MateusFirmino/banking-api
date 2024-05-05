package mateus.bankingapi.controllers;

import mateus.bankingapi.controllers.dto.ClientCreateRequest;
import mateus.bankingapi.controllers.dto.ClientCreateResponse;
import mateus.bankingapi.controllers.dto.ClientShow;
import mateus.bankingapi.controllers.dto.TransactionCreateRequest;
import mateus.bankingapi.controllers.dto.TransactionCreateResponse;
import mateus.bankingapi.controllers.dto.TransferCreateRequest;
import mateus.bankingapi.services.ClientService;
import mateus.bankingapi.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


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
  public ResponseEntity<ClientCreateResponse> createClient(@RequestBody ClientCreateRequest createRequest) {
    final var response = this.clientService.create(createRequest);
    final var location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(response.getId())
      .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @GetMapping()
  public ResponseEntity<Page<ClientShow>> findAll(Pageable pageable) {
    Page<ClientShow> page = this.clientService.findAll(pageable);
    return ResponseEntity.ok(page);
  }

  @PostMapping("/deposit")
  public ResponseEntity<TransactionCreateResponse> deposit(@RequestBody TransactionCreateRequest transactionCreateRequest) {
    final var response = this.transactionService.deposit(transactionCreateRequest);
    final var location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(response.getId())
      .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @PostMapping("/withdraw")
  public ResponseEntity<TransactionCreateResponse> withdraw(@RequestBody TransactionCreateRequest transactionCreateRequest) {
    final var response = this.transactionService.withdraw(transactionCreateRequest);
    final var location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(response.getId())
      .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @PostMapping("/transfer")
  public ResponseEntity<TransactionCreateResponse> transfer(@RequestBody TransferCreateRequest transferCreateRequest) {
    final var response = this.transactionService.transfer(transferCreateRequest);
    final var location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(response.getId())
      .toUri();
    return ResponseEntity.created(location).body(response);
  }

}
