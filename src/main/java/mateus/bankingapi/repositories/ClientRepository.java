package mateus.bankingapi.repositories;

import mateus.bankingapi.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  boolean existsByName(String name);


  Optional<Client> findByAccountNumber(String accountNumber);

}
