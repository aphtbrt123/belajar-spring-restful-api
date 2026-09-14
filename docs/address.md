# Address API Spec

## Create Address

Endpoint : POST /api/contacts/{idContacts}/addresses

Request Header : 

- X-API-TOKEN : Token (Mandatory)

Request Body : 

```json
{
  "street" : "Nama Jalan",
  "city" : "Kota",
  "province" : "Provinsi",
  "country" : "Negara",
  "postalCode" : "22343"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id" : "randomstring",
    "street" : "Nama Jalan",
    "city" : "Kota",
    "province" : "Provinsi",
    "country" : "Negara",
    "postalCode" : "22343"
  }
}
```

Response Body (Failed) : 

```json
{
  "errors" : "Contact is not found"
}
```

## Update Address

Endpoint : PUT /api/contacts/{idContact}/addresses/{idAddress}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
  "street" : "Nama Jalan",
  "city" : "Kota",
  "province" : "Provinsi",
  "country" : "Negara",
  "postalCode" : "22343"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id" : "randomstring",
    "street" : "Nama Jalan",
    "city" : "Kota",
    "province" : "Provinsi",
    "country" : "Negara",
    "postalCode" : "22343"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Address is not found"
}
```

## Get Address

Endpoint : GET /api/contacts/{idContact}/addresses/{idAddress}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data" : {
    "id" : "randomstring",
    "street" : "Nama Jalan",
    "city" : "Kota",
    "province" : "Provinsi",
    "country" : "Negara",
    "postalCode" : "22343"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Address is not found"
}
```

## Remove Address

Endpoint : DELETE /api/contacts/{idContact}/addresses/{idAddress}

Request Header :

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
  "data" : "Address is not found"
}
```

## List Address

Endpoint : GET /api/contacts/{idContact}/addresses

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data" : [
    {
      "id" : "randomstring",
      "street" : "Nama Jalan",
      "city" : "Kota",
      "province" : "Provinsi",
      "country" : "Negara",
      "postalCode" : "22343"
    }
  ]
}
```

Response Body (Failed) :

```json
{
  "errors" : "Contact is not found"
}
```