# Tiny Ledger

A minimal ledger API. Record money movements, check the balance, view transaction history. In-memory only. No database.

## Requirements

- Java 25.
- No local Maven install needed. Use the bundled `./mvnw` (or `mvnw.cmd` on Windows).

## Running

```bash
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`.

## API

### Create an account

The server assigns the id.

```bash
curl -X POST "http://localhost:8080/api/accounts"
```

Response:
```json
{
  "id": 1,
  "currency": "EUR",
  "balance": 0,
  "createdAt": "...",
  "updatedAt": "..."
}
```

### Get an account

Includes the current balance.

```bash
curl "http://localhost:8080/api/accounts/1"
```

### Record a money movement

Deposit or withdrawal.

```bash
# deposit 100
curl -X POST http://localhost:8080/api/accounts/1/transactions \
  -H "Content-Type: application/json" \
  -d '{"amount": "100.00", "currency": "EUR"}'

# withdraw 30
curl -X POST http://localhost:8080/api/accounts/1/transactions \
  -H "Content-Type: application/json" \
  -d '{"amount": "-30.00", "currency": "EUR"}'

# currency is optional, defaults to EUR
curl -X POST http://localhost:8080/api/accounts/1/transactions \
  -H "Content-Type: application/json" \
  -d '{"amount": "10.00"}'
```

A positive `amount` is a deposit. A negative `amount` is a withdrawal. There is no separate `type` field — the sign of `amount` carries that meaning.

### View transaction history

Newest transactions first.

```bash
curl http://localhost:8080/api/accounts/1/transactions
```

## Errors

Domain errors (bad input, missing account, business rule violations) return a JSON body with an error code and a message:

```json
{"errorCode": "acc-ex-02", "errorMessage": "Account does not exist"}
```

| Status | Code | Meaning |
|---|---|---|
| 400 | `acc-ex-00` | Invalid account id |
| 404 | `acc-ex-02` | Account does not exist |
| 400 | `tx-ex-00` | Invalid amount |
| 400 | `tx-ex-01` | Account currency does not match |
| 400 | `tx-ex-02` | Insufficient funds |
| 500 | `int-err-00` | Unexpected server error |

Framework-level errors return Spring's standard `ProblemDetail` body instead.

## Assumptions

- **Account id is server-generated.** `POST /api/accounts` returns the created account with its assigned id.
- **Accounts must be created explicitly.** Unknown account id → `404 acc-ex-02` on any operation.
- **Amount is signed, no separate type field.** Positive is a deposit, negative a withdrawal; zero is rejected (`400 tx-ex-00`).
- **Only EUR is supported.** A missing `currency` defaults to EUR, an unrecognised one is rejected (`400 tx-ex-01`).
- **Balance lives on the account**; each transaction also stores its own `balanceAfter` snapshot.
- **A withdrawal can't take the balance below zero** (`400 tx-ex-02`).
- **No separate response DTOs.** `Account`/`Transaction` domain models are serialized directly as API responses.
- **Two error body shapes.** Domain error format is not unified with framework errors for a sake of time.

## Known limitations

- **Data is in-memory only.** It is lost on restart. Storage sits behind the `AccountRepository` / `TransactionRepository` interfaces; the in-memory implementations are the only ones for now.
- **The balance update is atomic per account** (`ConcurrentMap.compute()`); recording the transaction is a separate step. A failure between them leaves balance and history inconsistent.
- **Transaction amount not bound on scale or magnitude.** out of scope for an in-memory demo.
- **Transaction history is not paginated.** `GET .../transactions` always returns the full history for an account. 

## Possible next steps

- **Database storage.** Add JPA/Postgres implementations of the repository interfaces; wrap balance update and transaction insert in one `@Transactional`.
- Pagination for transaction history.
- Response DTOs and a unified error body.
