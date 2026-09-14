# Contact API Spec

## Create Contact

Endpoint : POST /api/contacts

Request Header:

- X-API-TOKEN : Token (Mandatory)

Request Body : 

```json
{
  "firstName" : "Andre Pernando",
  "lastName" : "Pernando",
  "email" : "andre@haha.com",
  "phone" : "087666542563"
}
```

Response Body (Success) : 

```json
{
  "data" : {
    "id" : "random-string",
    "firstName" : "Andre Pernando",
    "lastName" : "Pernando",
    "email" : "andre@haha.com",
    "phone" : "087666542563"
  }
}
```

Response Body (Failed) :

```json
{
  "errros" : "Email format invalid, phone number format invalid, ........."
}
```

## Update Contact

Endpoint : PUT /api/contacts/{idContact}

Request Header:

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
  "firstName" : "Andre Pernando",
  "lastName" : "Pernando",
  "email" : "andre@haha.com",
  "phone" : "087666542563"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id" : "random-string",
    "firstName" : "Andre Pernando",
    "lastName" : "Pernando",
    "email" : "andre@haha.com",
    "phone" : "087666542563"
  }
}
```

Response Body (Failed) :

```json
{
  "errros" : "Email format invalid, phone number format invalid, ........."
}
```

## Get Contact

Endpoint : GET /api/contacts/{idContact}

Request Header:

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) : 

```json
{
  "data" : {
    "id" : "random-string",
    "firstName" : "Andre Pernando",
    "lastName" : "Hutabarat",
    "email" : "andre@haha.com",
    "phone" : "087666542563"
  }
}
```

Response Body (Failed, 404) :

```json
{
  "errors" : "contact is not found"
}
```

## Search Contact

Endpoint : GET /api/contacts

Query Param : 

- name : String, contact first name or last name, using like query (Optional)
- phone : String, contact phone, using like query
- email : String, contact email, using like query
- page : Integer, start from 0, default = 0
- size : Integer, default = 10

Request Header:

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data" : [
    {
      "id" : "random-string",
      "firstName" : "Andre Pernando",
      "lastName" : "Hutabarat",
      "email" : "andre@haha.com",
      "phone" : "087666542563"
    }
  ],
  "paging" : {
    "currentPage" : 0,
    "totalPage" : 10,
    "size" : 10
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "unauthorized"
}
```

## Remove Contact

Endpoint : DELETE /api/contacts/{idContact}

Request Header:

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) : 

```json
{
  "data" : "OK"
}
```

Response Body (Failed) :

```json
{
  "errors" : "contact is not found"
}
```