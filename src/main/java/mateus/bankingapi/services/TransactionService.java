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
    final var customer = getCustomer(transactionCreateRequest);
    customer.setBalance(customer.getBalance().add(transactionCreateRequest.getAmount()));

    Transaction transaction = new Transaction();
    transaction.setAmount(transactionCreateRequest.getAmount());
    LocalDateTime dateNow = LocalDateTime.now();
    transaction.setDate(dateNow);
    transaction.setCustomer(customer);
    transaction.setAccountNumber(transactionCreateRequest.getAccountNumber());
    transaction.setTransactionType(TransactionType.DEPOSIT);

    customerRepository.save(customer);
    repository.save(transaction);

    return TransactionCreateResponse.builder()
      .id(transaction.getId())
      .amount(transaction.getAmount())
      .date(dateNow)
      .accountNumber(customer.getAccountNumber())
      .transactionType(transaction.getTransactionType())
      .build();
  }

  @Transactional
  public TransactionCreateResponse withdraw(@Valid TransactionCreateRequest transactionCreateRequest) {
    final var customer = getCustomer(transactionCreateRequest);
    verifyBalance(transactionCreateRequest, customer);

    customer.setBalance(customer.getBalance().subtract(transactionCreateRequest.getAmount()));

    Transaction transaction = new Transaction();
    transaction.setAmount(transactionCreateRequest.getAmount());
    LocalDateTime dateNow = LocalDateTime.now();
    transaction.setDate(dateNow);
    transaction.setCustomer(customer);
    transaction.setAccountNumber(transactionCreateRequest.getAccountNumber());
    transaction.setTransactionType(TransactionType.WITHDRAW);
    repository.save(transaction);

    customerRepository.save(customer);

    return TransactionCreateResponse.builder()
      .id(transaction.getId())
      .amount(transaction.getAmount())
      .date(dateNow)
      .accountNumber(customer.getAccountNumber())
      .transactionType(transaction.getTransactionType())
      .build();
  }

  private static void verifyBalance(
    TransactionCreateRequest transactionCreateRequest,
    Customer customer
  ) {
    if (customer.getBalance().compareTo(transactionCreateRequest.getAmount()) < 1) {
      throw new BusinessException("Insufficient balance to make the withdraw.");
    }
  }

  private Customer getCustomer(TransactionCreateRequest transactionCreateRequest) {
    return customerRepository.findByAccountNumber(transactionCreateRequest.getAccountNumber())
      .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
  }

  public Page<TransactionShow> transactionsByClient(
    String accountNumber,
    Pageable pageable
  ) {
    Page<Transaction> transactionPage = repository.getTransactionsByAccountNumber(accountNumber, pageable);
    return transactionPage.map(extract -> TransactionShow.builder()
      .amount(extract.getAmount())
      .date(extract.getDate())
      .accountNumber(accountNumber)
      .transactionType(extract.getTransactionType())
      .build());
  }

  public Page<TransactionShow> getAllTransactionsForToday(Pageable pageable) {
    final var result= repository.findTodayTransactions( pageable);
    return result.map(extract -> TransactionShow.builder()
      .amount(extract.getAmount())
      .date(extract.getDate())
      .accountNumber(extract.getAccountNumber())
      .transactionType(extract.getTransactionType())
      .build());
  }

}
