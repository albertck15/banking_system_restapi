## Banking System REST Api


## RegistrationController

Endpoint: `/api/register`

Method: **POST**

Description: Used for registering a new user. Accessible without logging in.

Request Body:
- username
- firstName
- lastName
- password
- email
- dateOfBirth

Return: ResponseEntity with a String information.


## UserController

Endpoint: `/api/users/{id}`

Method: **GET**

Description: Retrieves user data. If not an ADMIN role, only data for the logged-in user's ID is provided. Otherwise, it throws an AccessDeniedException.

Return: ResponseEntity with a UserDto object.

---

Endpoint: `/api/users/{id}`

Method: **PUT**

Description: Expects JSON similar to the registration request and updates the registered user in the database if authorized (logged in) or has an ADMIN role.

Return: ResponseEntity with the updated UserDto object.

---

Endpoint: `/api/users/{id}`

Method: **DELETE**

Description: Deletes the user from the database along with associated accounts if logged into the specified user or has an ADMIN role.

Return: ResponseEntity with a String information.


## AccountController

Endpoint: `/api/accounts/create`

Method: **GET**

Description: Creates a new account for the user after logging in. A user can have multiple accounts.

Return: HttpStatus.

---

Endpoint: `/api/accounts/delete/{account-number}`

Method: **DELETE**

Description: Deletes the specified account if it belongs to the logged-in user or has an ADMIN role.

Return: HttpStatus.

---

Endpoint: `/api/accounts/get-my-accounts`

Method: **GET**

Description: Returns AccountDtos associated with the logged-in user.

Return: List of AccountDto.

---

Endpoint: `/api/accounts/{account-number}`

Method: **GET**

Description: Returns an AccountDto object based on the account number. If it does not belong to the logged-in user, the balance will not be visible (null). ADMIN role allows full access.

Return: AccountDto.

---

Endpoint: `/api/accounts/get-all`

Method: **GET**

Description: Returns all accessible account data from other users in the form of a map containing user names as keys and lists of associated account numbers.

Return: Map<String, List<Long>>.


## TransactionController

Endpoint: `/api/transactions/deposit`

Method: **POST**

Description: Deposits an amount into an account. It does not require the user to be logged in. Expected inputs include the account number and amount.

Return: ResponseEntity<TransactionDto> representing the transaction object.

---

Endpoint: `/api/transactions/withdraw`

Method: **POST**

Description: Withdraws an amount from an account. Requires the user to be logged in and own the account. Expected inputs include the account number and amount.

Return: ResponseEntity<TransactionDto> representing the transaction object.

---

Endpoint: `/api/transactions/transfer`

Method: **POST**

Description: Initiates a transaction from one account to another. The sender account must be owned by the logged-in user. Expected inputs include sender account number, receiver account number, and amount.

Return: ResponseEntity<TransactionDto> representing the transaction object.

---

Endpoint: `/api/transactions/{account-number}`

Method: **GET**

Description: Returns transactions associated with the logged-in user, both initiated and received.

Return: ResponseEntity<List<TransactionDto>>.


## LogoutController

Endpoint: `/api/logout`

Method: **POST**

Description: Logs out the user.

Return: ResponseEntity with a String information.


Global return values may vary in case of exceptions. In such cases, the return value will be ResponseEntity<CustomErrorResponse> with the following fields:

- message
- timestamp

