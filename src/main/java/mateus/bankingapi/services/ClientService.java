package mateus.bankingapi.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mateus.bankingapi.controllers.dto.ClientCreateRequest;
import mateus.bankingapi.controllers.dto.ClientCreateResponse;
import mateus.bankingapi.controllers.dto.ClientShowAll;
import mateus.bankingapi.models.Client;
import mateus.bankingapi.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  private final ClientRepository repository;

  public ClientService(ClientRepository repository) {this.repository = repository;}

  @Transactional
  public ClientCreateResponse create(@Valid ClientCreateRequest clientCreateRequest) {
    Client client = new Client();
    client.setName(clientCreateRequest.getName());
    client.setAge(clientCreateRequest.getAge());
    client.setEmail(clientCreateRequest.getEmail());
    client.setAccountNumber(clientCreateRequest.getAccountNumber());
    repository.save(client);

    return ClientCreateResponse.builder()
      .id(client.getId())
      .name(client.getName())
      .age(client.getAge())
      .email(client.getEmail())
      .accountNumber(client.getAccountNumber())
      .build();
  }

  public Client findByNumAccount(String numAccount) {
    return repository.findByAccountNumber(numAccount);
  }

  public Page<ClientShowAll> findAll(final Pageable pageable) {
     Page<Client> clientAllPage = repository.findAll(pageable);
     return clientAllPage.map( client -> ClientShowAll.builder()
       .id(client.getId())
       .name(client.getName())
       .age(client.getAge())
       .email(client.getEmail())
       .accountNumber(client.getAccountNumber())
       .build());
  }

  public void ifNameExistsThrowsException(String name) throws Exception {
    if (repository.existsByName(name)) {
      throw new Exception("Nome ja cadastrado! Nome: " + name);
    }
  }

}
