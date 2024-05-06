package mateus.bankingapi.repositories;

import mateus.bankingapi.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  Page<Transaction> findByDate(LocalDate date,Pageable pageable);

  Page<Transaction> getTransactionsByAccountNumber(String accountNumber,
                                              Pageable pageable
                                              );

  Page<Transaction> getTransactionsByDate(LocalDate date, Pageable pageable);

}
