# User API Spec

## Register User

Endpoint : POST /api/users

Request Body : 

```json
{
  "username" : "aphtbrt",
  "password" : "rahasia",
  "name" : "Andre Pernando Hutabarat"
}
```

Response Body (Success) :

```json
{
  "data" : "OK"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Username cannot be blank, ???"
}
```

## Login User

Endpoint : POST /api/auth/login

Request Body :

```json
{
  "username" : "aphtbrt",
  "password" : "rahasia"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "token" : "TOKEN",
    "expiredAt" : 1234567890 //millisecond
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors" : "Username or Password is Wrong!!!"
}
```

## Get User

Endpoint : GET /api/users/current

Request Header:

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data" : {
    "username" : "aphtbrt",
    "name" : "Andre Pernando Hutabarat"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## Update User

Endpoint : PATCH /api/users/current

Request Header:

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
  "name" : "aphtbrt", //put if only update name
  "password" : "rahasia" //put if only update pasword
}
```

Response Body (Success) :

```json
{
  "data" : {
    "username" : "aphtbrt",
    "name" : "Andre Pernando Hutabarat"
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors" : "Unauthorized"
}
```

## Logout User

Endpoint : DELETE /api/auth/logout

Request Header:

- X-API-TOKEN : Token (Mandatory)

Response Body :

```json
{
  "data" : "OK"
}
```