package mateus.bankingapi.controllers;

import mateus.bankingapi.models.Client;
import mateus.bankingapi.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClienteController {

  @Autowired
  private ClientService clientService;

  @PostMapping
  public Client createClient(@RequestBody Client client) {
    return clientService.create(client);
  }

  @GetMapping("/{numAccount}")
  public Client findClient(@PathVariable String numAccount) {
    return clientService.findByNumAccount(numAccount);
  }

}
