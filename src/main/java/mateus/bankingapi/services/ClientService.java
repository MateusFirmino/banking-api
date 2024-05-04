package mateus.bankingapi.services;

import mateus.bankingapi.models.Client;
import mateus.bankingapi.repositories.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  private final ClientRepository repository;

  public ClientService(ClientRepository repository) {this.repository = repository;}

  public Client create(Client client) {
    return null;
  }

  public Client findByNumAccount(String numAccount) {
    return repository.findByAccountNumber(numAccount);
  }

  public void ifNameExistsThrowsException(String name) throws Exception {
    if (repository.existsByName(name)) {
      throw new Exception("Nome ja cadastrado! Nome: " + name);
    }
  }

}
