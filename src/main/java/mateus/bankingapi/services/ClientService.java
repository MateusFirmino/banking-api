package mateus.bankingapi.services;

import io.hypersistence.tsid.TSID;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mateus.bankingapi.controllers.dto.ClientBalanceResponse;
import mateus.bankingapi.controllers.dto.ClientCreateRequest;
import mateus.bankingapi.controllers.dto.ClientCreateResponse;
import mateus.bankingapi.controllers.dto.ClientShow;
import mateus.bankingapi.exception.BusinessException;
import mateus.bankingapi.models.Client;
import mateus.bankingapi.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

  private final ClientRepository repository;

  public ClientService(ClientRepository repository) {this.repository = repository;}

  @Transactional
  public ClientCreateResponse create(@Valid ClientCreateRequest clientCreateRequest) {
    Client client = new Client();
    client.setName(clientCreateRequest.getName());
    client.setBirthdate(clientCreateRequest.getBirthdate());
    verifyEmail(clientCreateRequest);
    client.setEmail(clientCreateRequest.getEmail());
    client.setAccountNumber(String.valueOf(TSID.Factory.getTsid().toLong()));
    client.setBalance(BigDecimal.ZERO);
    repository.save(client);

    return ClientCreateResponse.builder()
      .id(client.getId())
      .name(client.getName())
      .birthdate(client.getBirthdate())
      .email(client.getEmail())
      .accountNumber(client.getAccountNumber())
      .balance(client.getBalance())
      .build();
  }

  private void verifyEmail(ClientCreateRequest clientCreateRequest) {
    Optional<Client> existingClient = repository.findByEmail(clientCreateRequest.getEmail());
    if (existingClient.isPresent()) {
      throw new BusinessException("Email is already in use");
    }
  }

  public Page<ClientShow> findAll(final Pageable pageable) {
    Page<Client> clientAllPage = repository.findAll(pageable);
    return clientAllPage.map(client -> ClientShow.builder()
      .id(client.getId())
      .name(client.getName())
      .birthdate(client.getBirthdate())
      .email(client.getEmail())
      .balance(client.getBalance())
      .accountNumber(client.getAccountNumber())
      .build());
  }

  public Page<ClientBalanceResponse> getAllClientBalances(Pageable pageable) {
    Page<Client> clientsPage = repository.findAll(pageable);
    return clientsPage.map(client -> ClientBalanceResponse.builder()
      .balance(client.getBalance())
      .accountNumber(client.getAccountNumber())
      .build());
  }

}
