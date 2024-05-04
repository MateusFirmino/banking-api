package mateus.bankingapi.repositories;

import mateus.bankingapi.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  boolean existsByName(String name);



  Client findByAccountNumber(String accountNumber);

}