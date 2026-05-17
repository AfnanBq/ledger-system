package com.example.app.controller;

import com.example.app.model.dto.transactionsDto.DepositRequest;
import com.example.app.model.dto.transactionsDto.PaymentRequest;
import com.example.app.model.dto.transactionsDto.RefundRequest;
import com.example.app.model.dto.transactionsDto.SettlementRequest;
import com.example.app.model.dto.transactionsDto.TransactionBasic;
import com.example.app.model.dto.transactionsDto.TransferRequest;
import com.example.app.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @PostMapping("/deposit")
    public TransactionBasic deposit(@Valid @RequestBody DepositRequest deposit) {
        return transactionsService.deposit(deposit);
    }

    @PostMapping("/payment")
    public TransactionBasic payment(@Valid @RequestBody PaymentRequest payment) {
        return transactionsService.createPayment(payment);
    }

    @PostMapping("/refund")
    public TransactionBasic refund(@Valid @RequestBody RefundRequest refund) {
        return transactionsService.refund(refund);
    }

    @PostMapping("/transfer")
    public TransactionBasic refund(@Valid @RequestBody TransferRequest transfer) {
        return transactionsService.transfer(transfer);
    }

    @PostMapping("/settlement")
    public TransactionBasic settlement(@Valid @RequestBody SettlementRequest settlement) {
        return transactionsService.settlement(settlement);
    }
}
