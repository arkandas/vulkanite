# Vulkanite

![vulkanite banner](./backend/assets/banner.png)

This is a simple toy app I wrote over a weekend when I started learning Android. It serves as a proof of concept for exchanging a simulated currency called vulkanite.

When an account is created successfully, the user receives some starting currency and a unique wallet.

The app allows a user to:

- Transfer currency by wallet ID
- Transfer currency by QR
- Exchange QR vouchers for currency/rewards
- Generate a unique QR code to request currency

## Android Screenshots
### Login 
![login](./android/assets/v1.1.0_screens/grouped/Login.png "Login Screen")
### Home
![Home](./android/assets/v1.1.0_screens/grouped/Home.png "Home Screen")
### Voucher
![Voucher](./android/assets/v1.1.0_screens/grouped/Voucher.png "Voucher Screen")
### Transactions
![Transactions](./android/assets/v1.1.0_screens/grouped/Send.png)

### Requests
![Requests](./android/assets/v1.1.0_screens/grouped/Request.png)

## Vulkanite API

- **URL:** http://localhost:8080/
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **Swagger API Docs:** http://localhost:8080/v3/api-docs

## API Endpoints and Parameters

### 1. User

#### 1.1 Login

- **Description:** Takes a username and a password (SHA512) as input and returns a JWT token for the user.

- **Type:** POST

- **Request URL:** http://localhost:8080/login

![login](./backend/assets/images/login.png)

- **Query Response:**

![login response](./backend/assets/images/loginResponse.png)

- **Supported Response Codes:**

| Code | Description        |
| ---- | :----------------- |
| 200  | Ok                 |
| 400  | Bad request        |
| 405  | Method Not Allowed |

- **Example:**

```bash
curl -X POST "http://localhost:8080/login" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"password\": \"testpwd\", \"username\": \"johndoe\"}"
```

#### 1.2 Register

- **Description:** Creates a new user.

- **Type:** POST

- **Request URL:** http://localhost:8080/register

![login](./backend/assets/images/register.png)

- **Query Response:**

Empty body with 200 Ok response code.

- **Supported Response Codes:**

| Code | Description        |
| ---- | :----------------- |
| 200  | Ok                 |
| 400  | Bad request        |
| 405  | Method Not Allowed |

- **Example:**

```bash
curl -X POST "http://localhost:8080/register" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"email\": \"testmail@test.com\", \"fullname\": \"John Doe\", \"password\": \"e8603172175d138e1724d75c91f788738fcbcc74fc98ee68837075bbb0aaf0f6db567cca1b61ec57ad646d79e39b3b2ed452b8a048f22b265fb74b8a244a828c\", \"username\": \"johndoe\"}"
```

#### 1.3 Account Information

- **Description:** Returns wallet id and currency for the user.

- **Type:** GET

- **Request URL:** http://localhost:8080/accountInfo

- **Headers:**

| Header        | Description  |
| ------------- | :----------- |
| token | Bearer token |

![login](./backend/assets/images/accountInfo.png)

- **Query Response:**

![login](./backend/assets/images/accountInfoResponse.png)

- **Supported Response Codes:**

| Code | Description        |
| ---- | :----------------- |
| 200  | Ok                 |
| 400  | Bad request        |
| 401  | Unauthorized       |
| 405  | Method Not Allowed |

- **Example:**

```bash
curl -X GET "http://localhost:8080/accountInfo" -H "accept: */*" -H "token: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnY2FycmVyYXQiLCJleHAiOjE2MDc5ODYzMzgsImlhdCI6MTYwNzk2ODMzOH0.1CnwCWGePeZQl9kd_bRGdIxdVWGWHNarH8qEITTsRy6BFVUAYfE0VPQFpSd54X7_ii_1KZrOU6m1uHrYryiwww"
```

### 2. Transactions

#### 2.1 User Transactions

- **Description:** Returns all transactions for the user.

- **Type:** GET

- **Headers:**

| Header        | Description  |
| ------------- | :----------- |
| token | Bearer token |

- **Request URL:** http://localhost:8080/transactions

![transactions](./backend/assets/images/transactions.png)

- **Query Response:**

![transactions response](./backend/assets/images/transactionsResponse.png)

- **Supported Response Codes:**

| Code | Description        |
| ---- | :----------------- |
| 200  | Ok                 |
| 400  | Bad request        |
| 401  | Unauthorized       |
| 405  | Method Not Allowed |

- **Example:**

```bash
curl -X GET "http://localhost:8080/transactions" -H "accept: */*" -H "token: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnY2FycmVyYXQiLCJleHAiOjE2MDc5ODYzMzgsImlhdCI6MTYwNzk2ODMzOH0.1CnwCWGePeZQl9kd_bRGdIxdVWGWHNarH8qEITTsRy6BFVUAYfE0VPQFpSd54X7_ii_1KZrOU6m1uHrYryiwww"
```

#### 2.2 Transaction by Id

- **Description:** Returns a single user transaction by its id.

- **Type:** GET

- **Headers:**

| Header        | Description  |
| ------------- | :----------- |
| token | Bearer token |

- **Parameters:**

| Parameter     | Description    |
| ------------- | :------------- |
| transactionId | Transaction Id |

- **Request URL:** http://localhost:8080/transactions/{transactionId}

![transactions](./backend/assets/images/transactionsById.png)

- **Query Response:**

![transactions response](./backend/assets/images/transactionsByIdResponse.png)

- **Supported Response Codes:**

| Code | Description        |
| ---- | :----------------- |
| 200  | Ok                 |
| 400  | Bad request        |
| 401  | Unauthorized       |
| 404  | Not Found          |
| 405  | Method Not Allowed |

- **Example:**

```bash
curl -X GET "http://localhost:8080/transactions/22" -H "accept: */*" -H "token: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnY2FycmVyYXQiLCJleHAiOjE2MDc5ODYzMzgsImlhdCI6MTYwNzk2ODMzOH0.1CnwCWGePeZQl9kd_bRGdIxdVWGWHNarH8qEITTsRy6BFVUAYfE0VPQFpSd54X7_ii_1KZrOU6m1uHrYryiwww"
```

#### 2.3 Send Currency Transaction

- **Description:** Transfers currency to another user wallet.

- **Type:** POST

- **Headers:**

| Header        | Description  |
| ------------- | :----------- |
| token | Bearer token |

- **Request URL:** http://localhost:8080/transactions/sendCurrency

![transactions](./backend/assets/images/sendCurrency.png)

- **Query Response:**

Empty body with 200 Ok response code.

- **Supported Response Codes:**

| Code | Description        |
| ---- | :----------------- |
| 200  | Ok                 |
| 400  | Bad request        |
| 401  | Unauthorized       |
| 405  | Method Not Allowed |

- **Example:**

```bash
curl -X POST "http://localhost:8080/transactions/sendCurrency" -H "accept: */*" -H "token: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnY2FycmVyYXQiLCJleHAiOjE2MDc5ODYzMzgsImlhdCI6MTYwNzk2ODMzOH0.1CnwCWGePeZQl9kd_bRGdIxdVWGWHNarH8qEITTsRy6BFVUAYfE0VPQFpSd54X7_ii_1KZrOU6m1uHrYryiwww" -H "Content-Type: application/json" -d "{ \"amount\": 10, \"note\": \"Test transaction\", \"walletDestination\": \"8f0b86952c58f2\"}"
```

### 3. Vouchers

#### 3.1 User Vouchers

- **Description:** Returns all vouchers for the user.

- **Type:** GET

- **Headers:**

| Header        | Description  |
| ------------- | :----------- |
| token | Bearer token |

- **Request URL:** http://localhost:8080/vouchers

![transactions](./backend/assets/images/vouchers.png)

- **Query Response:**

![transactions response](./backend/assets/images/vouchersResponse.png)

- **Supported Response Codes:**

| Code | Description        |
| ---- | :----------------- |
| 200  | Ok                 |
| 400  | Bad request        |
| 401  | Unauthorized       |
| 405  | Method Not Allowed |

- **Example:**

```bash
curl -X GET "http://localhost:8080/vouchers" -H "accept: */*" -H "token: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnY2FycmVyYXQiLCJleHAiOjE2MDc5ODYzMzgsImlhdCI6MTYwNzk2ODMzOH0.1CnwCWGePeZQl9kd_bRGdIxdVWGWHNarH8qEITTsRy6BFVUAYfE0VPQFpSd54X7_ii_1KZrOU6m1uHrYryiwww"
```

#### 3.2 Redeem Voucher

- **Description:** Adds a voucher to the current user.

- **Type:** POST

- **Headers:**

| Header        | Description  |
| ------------- | :----------- |
| token | Bearer token |

- **Parameters:**

| Parameter   | Description  |
| ----------- | :----------- |
| voucherCode | Voucher Code |

- **Request URL:** http://localhost:8080/vouchers/redeem/{voucherCode}

![voucher redemption](./backend/assets/images/redeemVoucher.png)

- **Query Response:**

Empty body with 200 Ok response code.

- **Supported Response Codes:**

| Code | Description        |
| ---- | :----------------- |
| 200  | Ok                 |
| 400  | Bad request        |
| 401  | Unauthorized       |
| 405  | Method Not Allowed |

- **Example:**

```bash
curl -X POST "http://localhost:8080/vouchers/redeem/5513146043" -H "accept: */*" -H "token: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnY2FycmVyYXQiLCJleHAiOjE2MDc5ODYzMzgsImlhdCI6MTYwNzk2ODMzOH0.1CnwCWGePeZQl9kd_bRGdIxdVWGWHNarH8qEITTsRy6BFVUAYfE0VPQFpSd54X7_ii_1KZrOU6m1uHrYryiwww"
```
