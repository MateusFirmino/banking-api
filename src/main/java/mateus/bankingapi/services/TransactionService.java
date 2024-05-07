package mateus.bankingapi.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mateus.bankingapi.controllers.dto.TransactionCreateRequest;
import mateus.bankingapi.controllers.dto.TransactionCreateResponse;
import mateus.bankingapi.controllers.dto.TransactionShow;
import mateus.bankingapi.exception.BusinessException;
import mateus.bankingapi.exception.ResourceNotFoundException;
import mateus.bankingapi.models.Customer;
import mateus.bankingapi.models.Transaction;
import mateus.bankingapi.models.TransactionType;
import mateus.bankingapi.repositories.CustomerRepository;
import mateus.bankingapi.repositories.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Validated
@Service
public class TransactionService {

  private final TransactionRepository repository;
  private final CustomerRepository customerRepository;

  public TransactionService(
    TransactionRepository repository,
    CustomerRepository customerRepository
  ) {
    this.repository = repository;
    this.customerRepository = customerRepository;
  }

  @Transactional
  public TransactionCreateResponse deposit(@Valid TransactionCreateRequest transactionCreateRequest) {
    final var client = getClient(transactionCreateRequest);
    client.setBalance(client.getBalance().add(transactionCreateRequest.getValue()));

    Transaction transaction = new Transaction();
    transaction.setAmount(transactionCreateRequest.getValue());
    LocalDateTime dateNow = LocalDateTime.now();
    transaction.setDate(dateNow);
    transaction.setCustomer(client);
    transaction.setAccountNumber(transactionCreateRequest.getAccountNumber());
    transaction.setTransactionType(TransactionType.DEPOSIT);

    customerRepository.save(client);
    repository.save(transaction);

    return TransactionCreateResponse.builder()
      .id(transaction.getId())
      .value(transaction.getAmount())
      .date(dateNow)
      .accountNumber(client.getAccountNumber())
      .transactionType(transaction.getTransactionType())
      .build();
  }

  @Transactional
  public TransactionCreateResponse withdraw(@Valid TransactionCreateRequest transactionCreateRequest) {
    final var client = getClient(transactionCreateRequest);
    verifyBalance(transactionCreateRequest, client);

    client.setBalance(client.getBalance().subtract(transactionCreateRequest.getValue()));

    Transaction transaction = new Transaction();
    transaction.setAmount(transactionCreateRequest.getValue());
    LocalDateTime dateNow = LocalDateTime.now();
    transaction.setDate(dateNow);
    transaction.setCustomer(client);
    transaction.setAccountNumber(transactionCreateRequest.getAccountNumber());
    transaction.setTransactionType(TransactionType.WITHDRAW);
    repository.save(transaction);

    customerRepository.save(client);

    return TransactionCreateResponse.builder()
      .id(transaction.getId())
      .value(transaction.getAmount())
      .date(dateNow)
      .accountNumber(client.getAccountNumber())
      .transactionType(transaction.getTransactionType())
      .build();
  }

  private static void verifyBalance(
    TransactionCreateRequest transactionCreateRequest,
    Customer customer
  ) {
    if (customer.getBalance().compareTo(transactionCreateRequest.getValue()) < 1) {
      throw new BusinessException("Insufficient balance to make the withdraw.");
    }
  }

  private Customer getClient(TransactionCreateRequest transactionCreateRequest) {
    return customerRepository.findByAccountNumber(transactionCreateRequest.getAccountNumber())
      .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
  }

  public Page<TransactionShow> transactionsByClient(
    String accountNumber,
    Pageable pageable
  ) {
    Page<Transaction> transactionPage = repository.getTransactionsByAccountNumber(accountNumber, pageable);
    return transactionPage.map(extract -> TransactionShow.builder()
      .value(extract.getAmount())
      .date(extract.getDate())
      .accountNumber(accountNumber)
      .transactionType(extract.getTransactionType())
      .build());
  }

  public Page<TransactionShow> getAllTransactionsForToday(LocalDate date, Pageable pageable) {
    final var result= repository.findByDate(date, pageable);
    return result.map(extract -> TransactionShow.builder()
      .value(extract.getAmount())
      .date(extract.getDate())
      .accountNumber(extract.getAccountNumber())
      .transactionType(extract.getTransactionType())
      .build());
  }

}
