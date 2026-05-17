# Ledger System (Pre-Interview – VRTX Company)

This project is a pre-interview task from **VRTX Company**, designed to showcase understanding of their tech stack and business logic in a ledger-based financial system.

---

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

### Task Achievements (Completed within 2 days)

#### Core Requirements
- Accounts (User, Merchant, Fees, Settlement) ✅
- Double-entry ledger ✅
- Immutable transactions ✅

#### Supported Scenarios
- Card payment (POS / contactless) ✅
- Refund / Reversal ✅
- Wallet transfer ✅
- Merchant settlement ✅
- Fees handling ✅

#### Technical Constraints
- Balanced transactions ✅
- Idempotency via idempotency_key ✅
- Concurrency handling ✅
- Atomic operations ✅

#### Advanced Requirements
- Transaction linking ✅
- Ledger-based balance calculation ✅
- Concurrency safety ✅

#### Required APIs
- POST	/transactions/payment ✅
- POST	/transactions/refund ✅
- POST	/transactions/transfer ✅
- POST	/transactions/settlement ✅
- GET	/accounts/{id}/ledger ✅

#### Deliverables
- PostgreSQL schema ✅
- Spring Boot REST APIs ✅
- Transaction logic ✅
- Edge case handling ✅

