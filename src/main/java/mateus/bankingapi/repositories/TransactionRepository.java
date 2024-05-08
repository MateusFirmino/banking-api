package mateus.bankingapi.repositories;

import mateus.bankingapi.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @Query("""
        select t from Transaction t 
        where date(t.date) = date(current_date) 
         """)
  Page<Transaction> findTodayTransactions(Pageable pageable);

  Page<Transaction> getTransactionsByAccountNumber(String accountNumber,
                                              Pageable pageable
                                              );

}
