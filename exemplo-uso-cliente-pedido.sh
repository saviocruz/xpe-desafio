#!/bin/bash

# Script de exemplo - Cliente e Pedido
# Este script demonstra as operações de Cliente e Pedido

BASE_URL="http://localhost:8080"

echo "=== Realizando login ==="
LOGIN_RESPONSE=$(curl -s -X POST $BASE_URL/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}')

echo "Resposta do login: $LOGIN_RESPONSE"

# Extrai o token da resposta
TOKEN=$(echo $LOGIN_RESPONSE | jq -r '.token')

if [ -z "$TOKEN" ] || [ "$TOKEN" = "null" ]; then
  echo "Erro: Falha ao obter o token. Verifique as credenciais."
  exit 1
fi

echo "✓ Token obtido com sucesso!"
echo "Token: $TOKEN"
echo ""

# Define o header de autenticação
AUTH_HEADER="Authorization: Bearer $TOKEN"

echo "=== 1. Criando Cliente ==="
curl -X POST $BASE_URL/api/clientes \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "telefone": "(11) 99999-9999",
    "endereco": "Rua ABC, 123 - São Paulo, SP"
  }'
echo ""
echo ""

echo "=== 2. Criando segundo Cliente ==="
curl -X POST $BASE_URL/api/clientes \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{
    "nome": "Maria Santos",
    "email": "maria@email.com",
    "telefone": "(21) 88888-8888",
    "endereco": "Av. Paulista, 1000 - Rio de Janeiro, RJ"
  }'
echo ""
echo ""

echo "=== 3. Listando todos os Clientes ==="
curl -X GET $BASE_URL/api/clientes \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 4. Buscando Cliente por ID ==="
curl -X GET $BASE_URL/api/clientes/1 \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 5. Atualizando Cliente ==="
curl -X PUT $BASE_URL/api/clientes/1 \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{
    "nome": "João Silva Atualizado",
    "email": "joao.novo@email.com",
    "telefone": "(11) 88888-8888",
    "endereco": "Rua Nova, 456 - São Paulo, SP"
  }'
echo ""
echo ""

echo "=== 6. Criando Produto para teste ==="
curl -X POST $BASE_URL/api/produtos \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{
    "nome": "Teclado Mecânico",
    "descricao": "Teclado Mecânico RGB",
    "preco": 350.00,
    "quantidade": 15,
    "categoria": "Periféricos"
  }'
echo ""
echo ""

echo "=== 7. Criando segundo Produto ==="
curl -X POST $BASE_URL/api/produtos \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{
    "nome": "Mouse Gamer",
    "descricao": "Mouse Gamer 16000 DPI",
    "preco": 250.00,
    "quantidade": 20,
    "categoria": "Periféricos"
  }'
echo ""
echo ""

echo "=== 8. Listando Produtos ==="
curl -X GET $BASE_URL/api/produtos \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 9. Criando Pedido ==="
curl -X POST $BASE_URL/api/pedidos \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{
    "clienteId": 1,
    "itens": [
      {
        "produtoId": 1,
        "quantidade": 2
      },
      {
        "produtoId": 2,
        "quantidade": 1
      }
    ]
  }'
echo ""
echo ""

echo "=== 10. Listando todos os Pedidos ==="
curl -X GET $BASE_URL/api/pedidos \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 11. Buscando Pedido por ID ==="
curl -X GET $BASE_URL/api/pedidos/1 \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 12. Filtrando Pedidos por Status (AGUARDANDO_PAGAMENTO) ==="
curl -X GET $BASE_URL/api/pedidos/status/AGUARDANDO_PAGAMENTO \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 13. Filtrando Pedidos por Status (PROCESSANDO) ==="
curl -X GET $BASE_URL/api/pedidos/status/PROCESSANDO \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 14. Atualizando Status do Pedido para PROCESSANDO ==="
curl -X PUT $BASE_URL/api/pedidos/1/status \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{"status": "PROCESSANDO"}'
echo ""
echo ""

echo "=== 15. Verificando que estoque foi decrementado ==="
curl -X GET $BASE_URL/api/produtos/1 \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 16. Atualizando Status do Pedido para ENVIADO ==="
curl -X PUT $BASE_URL/api/pedidos/1/status \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{"status": "ENVIADO"}'
echo ""
echo ""

echo "=== 17. Atualizando Status do Pedido para ENTREGUE ==="
curl -X PUT $BASE_URL/api/pedidos/1/status \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{"status": "ENTREGUE"}'
echo ""
echo ""

echo "=== 18. Listando Pedidos do Cliente 1 ==="
curl -X GET $BASE_URL/api/clientes/1 \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 19. Tentando cancelar pedido já entregue (deve falhar) ==="
curl -X DELETE $BASE_URL/api/pedidos/1 \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 20. Criando novo Pedido para testar cancelamento ==="
curl -X POST $BASE_URL/api/pedidos \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{
    "clienteId": 2,
    "itens": [
      {
        "produtoId": 1,
        "quantidade": 1
      }
    ]
  }'
echo ""
echo ""

echo "=== 21. Cancelando Pedido ==="
curl -X DELETE $BASE_URL/api/pedidos/2 \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 22. Verificando que estoque foi restaurado ==="
curl -X GET $BASE_URL/api/produtos/1 \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 23. Deletando Cliente (deve falhar se tiver pedidos) ==="
curl -X DELETE $BASE_URL/api/clientes/1 \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 24. Deletando Cliente 2 ==="
curl -X DELETE $BASE_URL/api/clientes/2 \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 25. Listando Clientes após exclusão ==="
curl -X GET $BASE_URL/api/clientes \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "✓ Script finalizado!"
