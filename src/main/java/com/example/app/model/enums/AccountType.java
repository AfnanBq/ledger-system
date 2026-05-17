package com.example.app.model.enums;

public enum AccountType {
    CUSTOMER_WALLET, // Customer wallet/account
    MERCHANT_PAYABLE, // Merchant's account where owed the money
    FEES,
    SETTLEMENT // buffer account before sending money to the merchant's bank account 
}
