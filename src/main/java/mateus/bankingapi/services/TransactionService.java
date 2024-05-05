package mateus.bankingapi.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mateus.bankingapi.controllers.dto.TransactionCreateRequest;
import mateus.bankingapi.controllers.dto.TransactionCreateResponse;
import mateus.bankingapi.controllers.dto.TransferCreateRequest;
import mateus.bankingapi.models.Client;
import mateus.bankingapi.models.Transaction;
import mateus.bankingapi.models.TransactionType;
import mateus.bankingapi.repositories.ClientRepository;
import mateus.bankingapi.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Validated
@Service
public class TransactionService {

  private final TransactionRepository repository;
  private final ClientRepository clientRepository;

  public TransactionService(
    TransactionRepository repository,
    ClientRepository clientRepository
  ) {
    this.repository = repository;
    this.clientRepository = clientRepository;
  }

  // depositar-sacar-transferir
  @Transactional
  public TransactionCreateResponse deposit(@Valid TransactionCreateRequest transactionCreateRequest) {
    final var client = getClient(transactionCreateRequest);
    client.setBalance(client.getBalance().add(transactionCreateRequest.getValue()));

    Transaction transaction = new Transaction();
    transaction.setValue(transactionCreateRequest.getValue());
    LocalDateTime dateNow = LocalDateTime.now();
    transaction.setDate(dateNow);
    transaction.setClient(client);
    transaction.setTransactionType(TransactionType.DEPOSIT);

    clientRepository.save(client);
    repository.save(transaction);

    return TransactionCreateResponse.builder()
      .id(transaction.getId())
      .value(transaction.getValue())
      .date(dateNow)
      .accountNumber(client.getAccountNumber())
      .build();
  }

  // verificar se tem valor para saque e não deixar negativo
  @Transactional
  public TransactionCreateResponse withdraw(@Valid TransactionCreateRequest transactionCreateRequest) {
    final var client = getClient(transactionCreateRequest);
    verifyBalance(transactionCreateRequest, client);

    client.setBalance(client.getBalance().subtract(transactionCreateRequest.getValue()));

    Transaction transaction = new Transaction();
    transaction.setValue(transactionCreateRequest.getValue());
    LocalDateTime dateNow = LocalDateTime.now();
    transaction.setDate(dateNow);
    transaction.setClient(client);
    transaction.setTransactionType(TransactionType.WITHDRAW);
    repository.save(transaction);

    clientRepository.save(client);

    return TransactionCreateResponse.builder()
      .id(transaction.getId())
      .value(transaction.getValue())
      .date(dateNow)
      .accountNumber(client.getAccountNumber())
      .build();
  }

  //sender and receiver
  // salvar quem ta depositando e quem está sacando
  @Transactional
  public TransactionCreateResponse transfer(@Valid TransferCreateRequest transferCreateRequest) {
    final var sender = getSender(transferCreateRequest);
    final var receiver = getReceiver(transferCreateRequest);

    if (sender.getBalance().compareTo(transferCreateRequest.getValue()) < 0) {
      throw new IllegalArgumentException("Insufficient balance to make the transfer.");
    }

    sender.setBalance(sender.getBalance().subtract(transferCreateRequest.getValue()));
    receiver.setBalance(receiver.getBalance().add(transferCreateRequest.getValue()));

    clientRepository.save(sender);
    clientRepository.save(receiver);

    registerWithdraw(transferCreateRequest, sender);
    registerDeposit(transferCreateRequest, receiver);

    return TransactionCreateResponse.builder()
      .id(sender.getId())
      .value(transferCreateRequest.getValue())
      .date(LocalDateTime.now())
      .accountNumber(sender.getAccountNumber())
      .build();

  }

  private void registerWithdraw(
    TransferCreateRequest transferCreateRequest,
    Client sender
  ) {
    Transaction senderTransaction = new Transaction();
    senderTransaction.setValue(transferCreateRequest.getValue());
    LocalDateTime dateNow = LocalDateTime.now();
    senderTransaction.setDate(dateNow);
    senderTransaction.setClient(sender);
    senderTransaction.setTransactionType(TransactionType.WITHDRAW);
    repository.save(senderTransaction);
  }

  private void registerDeposit(
    TransferCreateRequest transferCreateRequest,
    Client receiver
  ) {
    Transaction receiverTransaction = new Transaction();
    receiverTransaction.setValue(transferCreateRequest.getValue());
    LocalDateTime dateNow = LocalDateTime.now();
    receiverTransaction.setDate(dateNow);
    receiverTransaction.setClient(receiver);
    receiverTransaction.setTransactionType(TransactionType.DEPOSIT);
    repository.save(receiverTransaction);
  }

  private static void verifyBalance(
    TransactionCreateRequest transactionCreateRequest,
    Client client
  ) {
    if (client.getBalance().compareTo(transactionCreateRequest.getValue()) < 0) {
      throw new IllegalArgumentException("Insufficient balance to make the withdraw.");
    }
  }

  private Client getClient(TransactionCreateRequest transactionCreateRequest) {
    return clientRepository.findByAccountNumber(transactionCreateRequest.getAccountNumber())
      .orElseThrow(() -> new IllegalArgumentException("Client not found"));
  }

  private Client getSender(TransferCreateRequest transferCreateRequest) {
    return clientRepository.findByAccountNumber(transferCreateRequest.getSender())
      .orElseThrow(() -> new IllegalArgumentException("Client not found"));
  }

  private Client getReceiver(TransferCreateRequest transferCreateRequest) {
    return clientRepository.findByAccountNumber(transferCreateRequest.getReceiver())
      .orElseThrow(() -> new IllegalArgumentException("Client not found"));
  }

}
