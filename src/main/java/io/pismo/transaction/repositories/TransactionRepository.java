package io.pismo.transaction.repositories;

import io.pismo.transaction.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}