package io.pismo.transaction.repositories;

import io.pismo.transaction.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}