package org.wireBarley.transaction.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wireBarley.transaction.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

}
