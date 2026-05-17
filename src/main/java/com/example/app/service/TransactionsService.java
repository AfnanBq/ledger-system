package com.example.app.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.model.dto.transactionsDto.DepositRequest;
import com.example.app.model.dto.transactionsDto.PaymentRequest;
import com.example.app.model.dto.transactionsDto.TransactionBasic;
import com.example.app.model.entity.Accounts;
import com.example.app.model.entity.LedgerEntries;
import com.example.app.model.entity.Transactions;
import com.example.app.model.enums.AccountType;
import com.example.app.model.enums.EntryType;
import com.example.app.model.enums.TransactionStatus;
import com.example.app.model.enums.TransactionType;
import com.example.app.repository.AccountsRepository;
import com.example.app.repository.LedgerEntriesRepository;
import com.example.app.repository.TransactionsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TransactionsService {

    private final AccountsRepository accountsRepository;
    private final TransactionsRepository transactionsRepository;
    private final LedgerEntriesRepository ledgerEntriesRepository;

    public TransactionsService(AccountsRepository accountsRepository, TransactionsRepository transactionsRepository,
            LedgerEntriesRepository ledgerEntriesRepository) {
        this.accountsRepository = accountsRepository;
        this.transactionsRepository = transactionsRepository;
        this.ledgerEntriesRepository = ledgerEntriesRepository;
    }

    @Transactional
    public TransactionBasic createPayment(PaymentRequest request) { // create DTO
        // check idempotency (transaction exists for same request_id)
        Optional<TransactionBasic> existingTrx = transactionsRepository.findByRequestId(request.requestId());
        if (existingTrx.isPresent()) {
            return existingTrx.get();
        }

        // get customer, merchant and fees accounts from accounts table
        Accounts customer = accountsRepository.findByIdAndAccountType(request.fromAccountId(), AccountType.CUSTOMER_WALLET)
                .orElseThrow(() -> new EntityNotFoundException("Customer account not found"));

        Accounts merchant = accountsRepository.findByIdAndAccountType(request.toAccountId(), AccountType.MERCHANT_PAYABLE)
                .orElseThrow(() -> new EntityNotFoundException("Merchant account not found"));

        Accounts feesAccount = accountsRepository.findByAccountType(AccountType.FEES)
                .orElseThrow(() -> new EntityNotFoundException("Fees account not found"));

        // validate customer balance (ledgerEntries source of truth so the balance will
        // be derived from it)
        BigDecimal customerBalance = ledgerEntriesRepository.calculateAccountBalance(customer.getId());

        if (customerBalance.compareTo(request.amount()) < 0) {
            throw new RuntimeException("Customer has insufficient balance");
        }

        // Start processing the transaction with pending status
        Transactions newTransaction = new Transactions();
        newTransaction.setType(TransactionType.PAYMENT);
        newTransaction.setStatus(TransactionStatus.PENDING);
        newTransaction.setAmount(request.amount());
        newTransaction.setRequestId(request.requestId());
        // preserve trx in db
        newTransaction = transactionsRepository.save(newTransaction);

        // calculate merchant amount after subtracting fees from the payment amount
        BigDecimal feeAmount = request.fee();
        BigDecimal merchantAmount = request.amount().subtract(feeAmount);

        // create ledger entries
        LedgerEntries customerEntry = new LedgerEntries();
        customerEntry.setAccount(customer);
        customerEntry.setTransaction(newTransaction);
        customerEntry.setAmount(request.amount());
        customerEntry.setEntryType(EntryType.DEBIT);

        LedgerEntries merchantEntry = new LedgerEntries();
        merchantEntry.setAccount(merchant);
        merchantEntry.setTransaction(newTransaction);
        merchantEntry.setAmount(merchantAmount);
        merchantEntry.setEntryType(EntryType.CREDIT);

        LedgerEntries feesEntry = new LedgerEntries();
        feesEntry.setAccount(feesAccount);
        feesEntry.setTransaction(newTransaction);
        feesEntry.setAmount(feeAmount);
        feesEntry.setEntryType(EntryType.CREDIT);

        // preserve entries
        ledgerEntriesRepository.save(customerEntry);
        ledgerEntriesRepository.save(merchantEntry);
        ledgerEntriesRepository.save(feesEntry);

        // Mark transaction success
        newTransaction.setStatus(TransactionStatus.SUCCESS);
        transactionsRepository.save(newTransaction);
        // return transaction record
        return new TransactionBasic(newTransaction.getId(), newTransaction.getType(), newTransaction.getStatus(), newTransaction.getRequestId(), newTransaction.getAmount(), newTransaction.getCreatedAt());

    }

    @Transactional
    public TransactionBasic deposit(DepositRequest request) {
        // check idempotency (transaction exists for same request_id)
        Optional<TransactionBasic> existingTrx = transactionsRepository.findByRequestId(request.requestId());
        if (existingTrx.isPresent()) {
            return existingTrx.get();
        }

        // get customer from accounts table
        Accounts customer = accountsRepository.findById(request.accountId())
                .orElseThrow(() -> new EntityNotFoundException("Customer account not found"));

        // create transaction
        // Start processing the transaction with pending status
        Transactions transaction = new Transactions();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setAmount(request.amount());
        transaction.setRequestId(request.requestId());
        // preserve trx in db
        transaction = transactionsRepository.save(transaction);

        // create ledger entries
        LedgerEntries customerEntry = new LedgerEntries();
        customerEntry.setAccount(customer);
        customerEntry.setTransaction(transaction);
        customerEntry.setAmount(request.amount());
        customerEntry.setEntryType(EntryType.CREDIT);

        // preserve ledger entry in the table
        customerEntry = ledgerEntriesRepository.save(customerEntry);

        // Mark transaction success
        transaction.setStatus(TransactionStatus.SUCCESS);
        transactionsRepository.save(transaction);
        // return transaction record
        return new TransactionBasic(transaction.getId(), transaction.getType(), transaction.getStatus(), transaction.getRequestId(), transaction.getAmount(), transaction.getCreatedAt());

    }
}