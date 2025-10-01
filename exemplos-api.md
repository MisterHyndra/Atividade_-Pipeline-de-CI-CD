# 📚 Exemplos de Uso da API - Gerenciador de Biblioteca Pessoal

Este documento contém exemplos práticos de como usar a API REST do Gerenciador de Biblioteca Pessoal.

## 🚀 Iniciando a Aplicação

```bash
# Executar localmente
./mvnw spring-boot:run

# Ou com Docker
docker build -t biblioteca-pessoal .
docker run -p 8080:8080 biblioteca-pessoal
```

## 📋 Endpoints Disponíveis

### 1. Listar Todos os Livros
```bash
curl -X GET http://localhost:8080/api/livros
```

**Resposta:**
```json
[
  {
    "id": 1,
    "titulo": "O Senhor dos Anéis",
    "autor": "J.R.R. Tolkien",
    "anoPublicacao": 1954,
    "lido": true
  }
]
```

### 2. Buscar Livro por ID
```bash
curl -X GET http://localhost:8080/api/livros/1
```

**Resposta:**
```json
{
  "id": 1,
  "titulo": "O Senhor dos Anéis",
  "autor": "J.R.R. Tolkien",
  "anoPublicacao": 1954,
  "lido": true
}
```

### 3. Criar Novo Livro
```bash
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "1984",
    "autor": "George Orwell",
    "anoPublicacao": 1949,
    "lido": false
  }'
```

**Resposta:**
```json
{
  "id": 2,
  "titulo": "1984",
  "autor": "George Orwell",
  "anoPublicacao": 1949,
  "lido": false
}
```

### 4. Atualizar Livro Existente
```bash
curl -X PUT http://localhost:8080/api/livros/2 \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "1984",
    "autor": "George Orwell",
    "anoPublicacao": 1949,
    "lido": true
  }'
```

**Resposta:**
```json
{
  "id": 2,
  "titulo": "1984",
  "autor": "George Orwell",
  "anoPublicacao": 1949,
  "lido": true
}
```

### 5. Deletar Livro
```bash
curl -X DELETE http://localhost:8080/api/livros/2
```

**Resposta:** `204 No Content`

## 🎯 Exemplos com Postman

### Collection JSON para Postman
```json
{
  "info": {
    "name": "Biblioteca Pessoal API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Listar Livros",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/livros",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "livros"]
        }
      }
    },
    {
      "name": "Criar Livro",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"titulo\": \"Dom Casmurro\",\n  \"autor\": \"Machado de Assis\",\n  \"anoPublicacao\": 1899,\n  \"lido\": true\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/livros",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "livros"]
        }
      }
    }
  ]
}
```

## 🗄️ Acessando o Console H2

1. Acesse: http://localhost:8080/h2-console
2. Configure a conexão:
   - **JDBC URL:** `jdbc:h2:mem:testdb`
   - **User Name:** `sa`
   - **Password:** `password`
3. Clique em "Connect"

### Consultas SQL Úteis

```sql
-- Ver todos os livros
SELECT * FROM livros;

-- Buscar livros lidos
SELECT * FROM livros WHERE lido = true;

-- Buscar livros por autor
SELECT * FROM livros WHERE autor = 'J.R.R. Tolkien';

-- Contar livros por status de leitura
SELECT lido, COUNT(*) FROM livros GROUP BY lido;
```

## 🧪 Testando com Dados de Exemplo

### Script para popular a base com dados de teste:
```bash
# Criar alguns livros de exemplo
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{"titulo": "O Hobbit", "autor": "J.R.R. Tolkien", "anoPublicacao": 1937, "lido": true}'

curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{"titulo": "A Revolução dos Bichos", "autor": "George Orwell", "anoPublicacao": 1945, "lido": false}'

curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{"titulo": "Cem Anos de Solidão", "autor": "Gabriel García Márquez", "anoPublicacao": 1967, "lido": true}'

curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{"titulo": "1984", "autor": "George Orwell", "anoPublicacao": 1949, "lido": false}'
```

## 🔍 Códigos de Status HTTP

- **200 OK** - Operação realizada com sucesso
- **201 Created** - Recurso criado com sucesso
- **204 No Content** - Recurso deletado com sucesso
- **400 Bad Request** - Dados inválidos enviados
- **404 Not Found** - Recurso não encontrado
- **500 Internal Server Error** - Erro interno do servidor

## 🚨 Tratamento de Erros

### Exemplo de erro 404:
```bash
curl -X GET http://localhost:8080/api/livros/999
```

**Resposta:**
```
404 Not Found
```

### Exemplo de erro 400:
```bash
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{"titulo": "", "autor": "Autor", "anoPublicacao": 2023, "lido": false}'
```

**Resposta:**
```
400 Bad Request
```

## 📊 Monitoramento e Logs

A aplicação gera logs detalhados que podem ser visualizados no console. Para desenvolvimento, os logs incluem:

- Requisições HTTP recebidas
- Consultas SQL executadas
- Erros e exceções
- Informações de inicialização

## 🔧 Configurações Avançadas

### Variáveis de Ambiente
```bash
# Configurar porta personalizada
export SERVER_PORT=9090

# Configurar perfil de ambiente
export SPRING_PROFILES_ACTIVE=dev

# Executar com configurações personalizadas
./mvnw spring-boot:run
```
