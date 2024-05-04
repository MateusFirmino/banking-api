package mateus.bankingapi.controllers;

import mateus.bankingapi.controllers.dto.ClientCreateRequest;
import mateus.bankingapi.controllers.dto.ClientCreateResponse;
import mateus.bankingapi.controllers.dto.ClientShowAll;
import mateus.bankingapi.services.ClientService;
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
public class ClienteController {

  private final ClientService clientService;

  public ClienteController(ClientService clientService) {this.clientService = clientService;}

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
  public ResponseEntity<Page<ClientShowAll>> findAll(Pageable pageable) {
    Page<ClientShowAll> page = this.clientService.findAll(pageable);
    return ResponseEntity.ok(page);
  }

}
