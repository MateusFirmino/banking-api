package mateus.bankingapi.repositories;

import mateus.bankingapi.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  boolean existsByName(String name);


  Optional<Customer> findByAccountNumber(String accountNumber);

  Optional<Customer> findByEmail(String email);

}
