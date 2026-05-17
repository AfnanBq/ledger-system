package com.example.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.app.model.dto.ledgerEntriesDto.LedgerEntryBasic;
import com.example.app.model.entity.LedgerEntries;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;

public interface LedgerEntriesRepository extends JpaRepository<LedgerEntries, Long> {
    // crud to return ledger based on id
    Optional<LedgerEntryBasic> findById(UUID id);

    // crud to return ledger based on account_id
    Page<LedgerEntryBasic> findByAccountId(UUID accountId, Pageable pageable);

    // get account balance by account id derived from ledgerEntries
    @Query("""
                SELECT COALESCE(SUM(
                    CASE
                        WHEN le.entryType = 'CREDIT'
                        THEN le.amount
                        ELSE -le.amount
                    END
                ), 0)
                FROM LedgerEntries le
                WHERE le.account.Id = :accountId
            """)
    BigDecimal calculateAccountBalance(@Param("accountId") UUID accountId);

    // get ledger entry by accountId and transactionId
    Optional<BigDecimal> findByAccountIdAndTransactionId(UUID accountId, UUID transactionId);

}
