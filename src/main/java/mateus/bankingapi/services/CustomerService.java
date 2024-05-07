package mateus.bankingapi.services;

import io.hypersistence.tsid.TSID;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mateus.bankingapi.controllers.dto.CustomerBalanceResponse;
import mateus.bankingapi.controllers.dto.CustomerCreateRequest;
import mateus.bankingapi.controllers.dto.CustomerCreateResponse;
import mateus.bankingapi.controllers.dto.CustomerShow;
import mateus.bankingapi.exception.BusinessException;
import mateus.bankingapi.models.Customer;
import mateus.bankingapi.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CustomerService {

  private final CustomerRepository repository;

  public CustomerService(CustomerRepository repository) {this.repository = repository;}

  @Transactional
  public CustomerCreateResponse create(@Valid CustomerCreateRequest customerCreateRequest) {
    Customer customer = new Customer();
    customer.setName(customerCreateRequest.getName());
    customer.setBirthdate(customerCreateRequest.getBirthdate());
    verifyEmail(customerCreateRequest);
    customer.setEmail(customerCreateRequest.getEmail());
    customer.setAccountNumber(String.valueOf(TSID.Factory.getTsid().toLong()));
    customer.setBalance(BigDecimal.ZERO);
    repository.save(customer);

    return CustomerCreateResponse.builder()
      .id(customer.getId())
      .name(customer.getName())
      .birthdate(customer.getBirthdate())
      .email(customer.getEmail())
      .accountNumber(customer.getAccountNumber())
      .balance(customer.getBalance())
      .build();
  }

  private void verifyEmail(CustomerCreateRequest customerCreateRequest) {
    Optional<Customer> existingClient = repository.findByEmail(customerCreateRequest.getEmail());
    if (existingClient.isPresent()) {
      throw new BusinessException("Email is already in use");
    }
  }

  public Page<CustomerShow> findAll(final Pageable pageable) {
    Page<Customer> clientAllPage = repository.findAll(pageable);
    return clientAllPage.map(client -> CustomerShow.builder()
      .id(client.getId())
      .name(client.getName())
      .birthdate(client.getBirthdate())
      .email(client.getEmail())
      .balance(client.getBalance())
      .accountNumber(client.getAccountNumber())
      .build());
  }

  public Page<CustomerBalanceResponse> getAllClientBalances(Pageable pageable) {
    Page<Customer> clientsPage = repository.findAll(pageable);
    return clientsPage.map(client -> CustomerBalanceResponse.builder()
      .balance(client.getBalance())
      .accountNumber(client.getAccountNumber())
      .build());
  }

}
