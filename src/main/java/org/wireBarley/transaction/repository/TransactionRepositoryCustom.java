package org.wireBarley.transaction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.wireBarley.transaction.dto.TransactionDTO;
import org.wireBarley.transaction.dto.TransactionFilterDTO;

public interface TransactionRepositoryCustom {
    Page<TransactionDTO> findByFilter(TransactionFilterDTO filterDTO, Pageable pageable);

}
