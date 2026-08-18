# Ledger System

## Getting Started

1. **Clone the repository:**
```bash
git clone https://github.com/AfnanBq/ledger-system-task.git
```
2. Run the Docker containers:
```bash
   docker compose up --build
```
## Project Overview

This system contains 3 main APIs:

- Users
- Accounts
- Transactions

#### To explore the system:

- Create a user.
- Specify the account type. Supported account types:
   - CUSTOMER_WALLET
   - MERCHANT_PAYABLE
   - FEES
   - SETTLEMENT
- Accounts endpoints provide insights into account balances.
- Transactions endpoints allow the following operations:

- Customer → Merchant payment
- Merchant → Customer refund
- Wallet → Wallet transfer
- Account top-up (deposit)
- Merchant → Settlement (temporary, for payouts to merchant bank accounts)

