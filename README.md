## Tecnologias

| Tecnologia  | Versão                |
|-------------|-----------------------|
| Docker      | 26.0.0, build 2ae903e |
| Java        | OpenJDK 17            |
| Maven       | 4.0.0                 |
| Spring Boot | 3.2.5                 |

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
