# API REST Java - XPE Arquitetura

## Descrição

API REST para gerenciamento de uma empresa de vendas online. Esta API expõe dados de Produtos, Clientes e Pedidos para parceiros, seguindo a arquitetura MVC e utilizando padrões de projeto como Facade, Adapter e Factory.

## Tecnologias

- **Java 21**
- **Spring Boot 3.5**
- **H2 Database** (file-based para persistência)
- **Spring Security + JWT**
- **Swagger/OpenAPI 3.0**
- **JUnit 5 + Mockito**
- **Docker**

## Pré-requisitos

- Java 21 ou superior
- Maven 3.6 ou superior
- Docker (opcional)

## Como Executar

### Com Maven

```bash
# Compilar o projeto
mvn clean package

# Executar a aplicação
java -jar target/xpe-arquitetura-1.0.0.jar
```

### Com Docker

```bash
# Build da imagem
docker build -t xpe-arquitetura .

# Executar o container
docker run -p 8080:8080 xpe-arquitetura
```

### Com Docker Compose

```bash
# Executar com docker-compose
docker-compose up
```

## Endpoints da API

### Autenticação

- `POST /api/auth/login` - Login e obtenção de token JWT

### Produtos (Requer autenticação JWT)

- `POST /api/produtos` - Criar produto
- `GET /api/produtos` - Listar todos os produtos (paginado)
- `GET /api/produtos/{id}` - Buscar produto por ID
- `GET /api/produtos/nome/{nome}` - Buscar produtos por nome
- `PUT /api/produtos/{id}` - Atualizar produto
- `DELETE /api/produtos/{id}` - Deletar produto
- `GET /api/produtos/count` - Contar produtos

### Clientes (Requer autenticação JWT)

- `POST /api/clientes` - Criar cliente
- `GET /api/clientes` - Listar todos os clientes (paginado)
- `GET /api/clientes/{id}` - Buscar cliente por ID
- `PUT /api/clientes/{id}` - Atualizar cliente
- `DELETE /api/clientes/{id}` - Deletar cliente

### Pedidos (Requer autenticação JWT)

- `POST /api/pedidos` - Criar pedido
- `GET /api/pedidos` - Listar todos os pedidos (paginado)
- `GET /api/pedidos/{id}` - Buscar pedido por ID
- `GET /api/pedidos/status/{status}` - Filtrar pedidos por status
- `PUT /api/pedidos/{id}/status` - Atualizar status do pedido
- `DELETE /api/pedidos/{id}` - Cancelar pedido (restaura estoque)

### Status de Pedido

Os status possíveis para um pedido são:
- `AGUARDANDO_PAGAMENTO`
- `PROCESSANDO`
- `ENVIADO`
- `ENTREGUE`
- `CANCELADO`

## Exemplos de Uso

### 1. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer"
}
```

### 2. Criar Produto

```bash
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -d '{
    "nome": "Notebook Dell",
    "descricao": "Notebook Dell Inspiron 15",
    "preco": 4500.00,
    "quantidade": 10,
    "categoria": "Eletrônicos"
  }'
```

### 3. Listar Produtos

```bash
curl -X GET http://localhost:8080/api/produtos \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### 4. Criar Cliente

```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "telefone": "(11) 99999-9999",
    "endereco": "Rua ABC, 123 - São Paulo, SP"
  }'
```

### 5. Listar Clientes

```bash
curl -X GET http://localhost:8080/api/clientes \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### 6. Criar Pedido

```bash
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -d '{
    "clienteId": 1,
    "itens": [
      {
        "produtoId": 1,
        "quantidade": 2
      }
    ]
  }'
```

### 7. Listar Pedidos

```bash
curl -X GET http://localhost:8080/api/pedidos \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### 8. Filtrar Pedidos por Status

```bash
curl -X GET http://localhost:8080/api/pedidos/status/AGUARDANDO_PAGAMENTO \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### 9. Atualizar Status do Pedido

```bash
curl -X PUT http://localhost:8080/api/pedidos/1/status \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -d '{"status": "PROCESSANDO"}'
```

### 10. Cancelar Pedido

```bash
curl -X DELETE http://localhost:8080/api/pedidos/1 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

## Documentação Swagger

A documentação da API está disponível em:

- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## H2 Console

O console do banco de dados H2 está disponível em:

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/xpe-arquitetura-db`
- Username: `sa`
- Password: 

```
jdbc:h2:~/projetos/code/xpe-arquitetura/xpe-arquitetura-db;AUTO_SERVER=TRUE
```

## Estrutura do Projeto

```
xpe-arquitetura/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/xpe/arquitetura/
│   │   │       ├── ApiApplication.java
│   │   │       ├── config/
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   ├── SwaggerConfig.java
│   │   │       │   └── CorsConfig.java
│   │   │       ├── controller/
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── ClienteController.java
│   │   │       │   ├── PedidoController.java
│   │   │       │   └── ProdutoController.java
│   │   │       ├── service/
│   │   │       │   ├── AuthService.java
│   │   │       │   ├── ClienteService.java
│   │   │       │   ├── PedidoService.java
│   │   │       │   └── ProdutoService.java
│   │   │       ├── adapter/
│   │   │       │   ├── ClienteAdapter.java
│   │   │       │   ├── ItemPedidoAdapter.java
│   │   │       │   ├── PedidoAdapter.java
│   │   │       │   └── ProdutoAdapter.java
│   │   │       ├── factory/
│   │   │       │   ├── ClienteFactory.java
│   │   │       │   ├── ItemPedidoFactory.java
│   │   │       │   ├── PedidoFactory.java
│   │   │       │   └── ProdutoFactory.java
│   │   │       ├── model/
│   │   │       │   ├── Cliente.java
│   │   │       │   ├── ItemPedido.java
│   │   │       │   ├── Pedido.java
│   │   │       │   ├── Produto.java
│   │   │       │   ├── StatusPedido.java
│   │   │       │   └── Usuario.java
│   │   │       ├── dto/
│   │   │       │   ├── ClienteDTO.java
│   │   │       │   ├── ClienteRequestDTO.java
│   │   │       │   ├── ItemPedidoDTO.java
│   │   │       │   ├── ItemPedidoRequestDTO.java
│   │   │       │   ├── LoginRequestDTO.java
│   │   │       │   ├── PedidoDTO.java
│   │   │       │   ├── PedidoRequestDTO.java
│   │   │       │   ├── ProdutoDTO.java
│   │   │       │   └── ProdutoRequestDTO.java
│   │   │       ├── repository/
│   │   │       │   ├── ClienteRepository.java
│   │   │       │   ├── PedidoRepository.java
│   │   │       │   ├── ProdutoRepository.java
│   │   │       │   └── UsuarioRepository.java
│   │   │       ├── security/
│   │   │       │   ├── JwtAuthenticationFilter.java
│   │   │       │   └── JwtTokenProvider.java
│   │   │       └── exception/
│   │   │           ├── BusinessException.java
│   │   │           ├── ClienteNaoEncontradoException.java
│   │   │           ├── EstoqueInsuficienteException.java
│   │   │           ├── GlobalExceptionHandler.java
│   │   │           ├── PedidoNaoEncontradoException.java
│   │   │           └── ResourceNotFoundException.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/
│           └── com/xpe/arquitetura/
│               ├── adapter/
│               ├── controller/
│               ├── factory/
│               ├── integration/
│               ├── repository/
│               └── service/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Padrões de Projeto

### Facade Pattern
Implementado em [`ProdutoService`](src/main/java/com/xpe/arquitetura/service/ProdutoService.java), [`ClienteService`](src/main/java/com/xpe/arquitetura/service/ClienteService.java) e [`PedidoService`](src/main/java/com/xpe/arquitetura/service/PedidoService.java), orquestrando operações complexas de negócio.

### Adapter Pattern
Implementado em [`ClienteAdapter`](src/main/java/com/xpe/arquitetura/adapter/ClienteAdapter.java), [`PedidoAdapter`](src/main/java/com/xpe/arquitetura/adapter/PedidoAdapter.java), [`ItemPedidoAdapter`](src/main/java/com/xpe/arquitetura/adapter/ItemPedidoAdapter.java) e [`ProdutoAdapter`](src/main/java/com/xpe/arquitetura/adapter/ProdutoAdapter.java), convertendo entre DTOs e Entities.

### Factory Pattern
Implementado em [`ClienteFactory`](src/main/java/com/xpe/arquitetura/factory/ClienteFactory.java), [`PedidoFactory`](src/main/java/com/xpe/arquitetura/factory/PedidoFactory.java), [`ItemPedidoFactory`](src/main/java/com/xpe/arquitetura/factory/ItemPedidoFactory.java) e [`ProdutoFactory`](src/main/java/com/xpe/arquitetura/factory/ProdutoFactory.java), criando instâncias de forma controlada.

## Modelo de Dados

```
Cliente (1) ─────< (N) Pedido
  │                    │
  │                    │
  │                    │
  └────< (N) ItemPedido >──── (1) Produto
```

- Um **Cliente** pode ter vários **Pedidos**
- Um **Pedido** pode ter vários **ItensPedido**
- Um **ItemPedido** referencia um **Produto**

## Usuários para Teste

- **Admin**: username: `admin`, password: `admin123`
- **User**: username: `user`, password: `user123`

## Licença

Este projeto é parte do curso XPE Arquitetura.
