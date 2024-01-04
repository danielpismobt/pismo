package io.pismo.transaction.repositories;

import io.pismo.transaction.models.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {
}
