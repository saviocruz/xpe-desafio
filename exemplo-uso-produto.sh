#!/bin/bash

# Script de exemplo - Login e uso de token nas requisições
# Este script faz login, armazena o token e o utiliza nas demais requisições

BASE_URL="http://localhost:8080"

echo "=== 1. Realizando login ==="
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

echo "=== 2. Criando um produto ==="
curl -X POST $BASE_URL/api/produtos \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{
    "nome": "Notebook Dell",
    "descricao": "Notebook Dell Inspiron 15",
    "preco": 4500.00,
    "quantidade": 10,
    "categoria": "Eletrônicos"
  }'
echo ""
echo ""

echo "=== 3. Listando todos os produtos ==="
curl -X GET $BASE_URL/api/produtos \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 4. Buscando produto por ID ==="
curl -X GET $BASE_URL/api/produtos/1 \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 5. Buscando produtos por nome ==="
curl -X GET "$BASE_URL/api/produtos/nome/Notebook" \
  -H "$AUTH_HEADER"
echo ""
echo ""

echo "=== 6. Atualizando produto ==="
curl -X PUT $BASE_URL/api/produtos/1 \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{
    "nome": "Notebook Dell Atualizado",
    "descricao": "Notebook Dell Inspiron 15 - Atualizado",
    "preco": 4800.00,
    "quantidade": 8,
    "categoria": "Eletrônicos"
  }'
echo ""
echo ""

echo "=== 7. Contando produtos ==="
curl -X GET $BASE_URL/api/produtos/count \
  -H "$AUTH_HEADER"
echo ""
echo ""

#echo "=== 8. Deletando produto ==="
#curl -X DELETE $BASE_URL/api/produtos/1 \
#  -H "$AUTH_HEADER"
#echo ""
#echo ""

echo "✓ Script finalizado!"
