package mateus.bankingapi.services;

import io.hypersistence.tsid.TSID;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mateus.bankingapi.controllers.dto.ClientCreateRequest;
import mateus.bankingapi.controllers.dto.ClientCreateResponse;
import mateus.bankingapi.controllers.dto.ClientShow;
import mateus.bankingapi.models.Client;
import mateus.bankingapi.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    client.setAccountNumber(String.valueOf(TSID.Factory.getTsid().toLong()));
    client.setBalance(BigDecimal.ZERO);
    repository.save(client);

    return ClientCreateResponse.builder()
      .id(client.getId())
      .name(client.getName())
      .age(client.getAge())
      .email(client.getEmail())
      .accountNumber(client.getAccountNumber())
      .balance(client.getBalance())
      .build();
  }

  public Page<ClientShow> findAll(final Pageable pageable) {
    Page<Client> clientAllPage = repository.findAll(pageable);
    return clientAllPage.map(client -> ClientShow.builder()
      .id(client.getId())
      .name(client.getName())
      .age(client.getAge())
      .email(client.getEmail())
      .balance(client.getBalance())
      .accountNumber(client.getAccountNumber())
      .build());
  }

}
