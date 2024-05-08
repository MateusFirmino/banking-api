## Tecnologias

| Tecnologia  | Versão                |
|-------------|-----------------------|
| Docker      | 26.0.0, build 2ae903e |
| Java        | OpenJDK 17            |
| Maven       | 4.0.0                 |
| Spring Boot | 3.2.5                 |
| Postgres    | 16.2-1                |

## Postgres
As credenciais de acesso e portas podem ser alteradas antes de subir o docker através das variáveis de arquivo
`application.properties` e `compose.yml`.

|       Porta        |        Nome           |  Usuário |  Senha   | 
|:------------------:|:---------------------:|:--------:|:--------:|
|        15432       |     banking_api_db   | postgres | 123456  |


## Deploy
O projeto está dockerizado, caso não tenha instalado, clickar no link e seguir os passos a baixo.

* [Docker](https://docs.docker.com/desktop/)
  * Para verificar se o docker foi instalado corretamente execute o comando `docker --version`
* [Docker Compose](https://docs.docker.com/compose/install/)
  * Para verificar se o docker-compose foi instalado corretamente execute o comando `docker-compose --version`

O projeto está dockerizado e pode ser executado com o comando abaixo:

```bash
docker-compose up -d
```
Antes de subir o docker é nescessário alterar o caminho da pasta do banco. Dentro do arquivo `compose.yaml` na aba `volumes`, alterar o caminho para uma pasta desejada, o sufixo `:/var/lib/postgresql/data` permanece após o diretório da pasta

### Endpoints

#### POST /customer

Endpoint para criação de um customer.

- Request Payload

```
{
 {
  "name": "Mateus",
  "birthdate": "1999-02-24",
  "email": "mateus@gmail.com",
}
}

```
- Response Payload

```json
{
    "success": true,
    "message": null,
    "data": {
        "id": 1,
        "name": "Mateus",
        "birthdate": "1999-02-24",
        "email": "mateus@gmail.com",
        "accountNumber": "576109054991631046",
        "balance": 0
    }
}
```

----------------------------------------------------------------------------

#### POST /deposit e /withdraw tem o mesmo comportamento

Endpoint para criação de um deposit ou withdraw.

- Request Payload

```
{
 {
"amount": 100,
  "accountNumber": "575903352908710838"
}
}

```
- Response Payload

```json
{
    "success": true,
    "message": null,
    "data": {
        "id": 9,
        "amount": 100,
        "date": "2024-05-08T07:32:02.7897366",
        "accountNumber": "575903352908710838",
        "transactionType": "DEPOSIT"
    }
}
```

----------------------------------------------------------------------------

#### GET /customer/balances

Endpoint para criação de um customer.

- Response Payload

```json
{
    "success": true,
    "message": null,
    "data": {
        "content": [
            {
                "name": "mateus ",
                "accountNumber": "575996316714505206",
                "balance": 0.00
            }   
        ],
        "pageable": {
            "pageNumber": 0,
            "pageSize": 20,
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "paged": true,
            "unpaged": false
        },
        "last": true,
        "totalPages": 1,
        "totalElements": 1,
        "size": 20,
        "number": 0,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "first": true,
        "numberOfElements": 1,
        "empty": false
    }
}
```

