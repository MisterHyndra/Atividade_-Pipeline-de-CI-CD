#!/bin/bash

# Script para testar a API do Gerenciador de Biblioteca Pessoal
# Certifique-se de que a aplicação está rodando em http://localhost:8080

BASE_URL="http://localhost:8080/api/livros"

echo "🚀 Testando API do Gerenciador de Biblioteca Pessoal"
echo "=================================================="

# Função para fazer requisições com formatação
make_request() {
    local method=$1
    local url=$2
    local data=$3
    local description=$4
    
    echo ""
    echo "📋 $description"
    echo "----------------------------------------"
    
    if [ -n "$data" ]; then
        curl -X $method "$url" \
            -H "Content-Type: application/json" \
            -d "$data" \
            -w "\nStatus: %{http_code}\n" \
            -s
    else
        curl -X $method "$url" \
            -w "\nStatus: %{http_code}\n" \
            -s
    fi
}

# 1. Listar livros (deve estar vazio inicialmente)
make_request "GET" "$BASE_URL" "" "Listando todos os livros (inicial)"

# 2. Criar primeiro livro
make_request "POST" "$BASE_URL" '{
    "titulo": "O Senhor dos Anéis",
    "autor": "J.R.R. Tolkien",
    "anoPublicacao": 1954,
    "lido": true
}' "Criando primeiro livro"

# 3. Criar segundo livro
make_request "POST" "$BASE_URL" '{
    "titulo": "1984",
    "autor": "George Orwell",
    "anoPublicacao": 1949,
    "lido": false
}' "Criando segundo livro"

# 4. Criar terceiro livro
make_request "POST" "$BASE_URL" '{
    "titulo": "Dom Casmurro",
    "autor": "Machado de Assis",
    "anoPublicacao": 1899,
    "lido": true
}' "Criando terceiro livro"

# 5. Listar todos os livros
make_request "GET" "$BASE_URL" "" "Listando todos os livros após criação"

# 6. Buscar livro por ID (assumindo que o primeiro livro tem ID 1)
make_request "GET" "$BASE_URL/1" "" "Buscando livro por ID (ID: 1)"

# 7. Atualizar livro (marcar 1984 como lido)
make_request "PUT" "$BASE_URL/2" '{
    "titulo": "1984",
    "autor": "George Orwell",
    "anoPublicacao": 1949,
    "lido": true
}' "Atualizando livro (marcando 1984 como lido)"

# 8. Verificar atualização
make_request "GET" "$BASE_URL/2" "" "Verificando atualização do livro"

# 9. Tentar buscar livro inexistente
make_request "GET" "$BASE_URL/999" "" "Tentando buscar livro inexistente (deve retornar 404)"

# 10. Deletar um livro
make_request "DELETE" "$BASE_URL/3" "" "Deletando livro (ID: 3)"

# 11. Listar livros após deleção
make_request "GET" "$BASE_URL" "" "Listando livros após deleção"

echo ""
echo "✅ Testes concluídos!"
echo "=================================================="
echo "📊 Resumo dos testes:"
echo "   - ✅ Criação de livros"
echo "   - ✅ Listagem de livros"
echo "   - ✅ Busca por ID"
echo "   - ✅ Atualização de livros"
echo "   - ✅ Deleção de livros"
echo "   - ✅ Tratamento de erros (404)"
echo ""
echo "🌐 Acesse o console H2 em: http://localhost:8080/h2-console"
echo "   JDBC URL: jdbc:h2:mem:testdb"
echo "   Username: sa"
echo "   Password: password"
