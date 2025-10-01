# 📚 Gerenciador de Biblioteca Pessoal

Uma API RESTful simples para gerenciar sua biblioteca pessoal, desenvolvida com Spring Boot e integrada com um pipeline CI/CD completo usando GitHub Actions.

## 🚀 Funcionalidades

- **CRUD completo** para gerenciamento de livros
- **Banco de dados H2** em memória com console web
- **API REST** com endpoints padronizados
- **Pipeline CI/CD** automatizado com GitHub Actions
- **Containerização** com Docker multi-stage
- **Deploy automatizado** para ambientes de desenvolvimento e produção

## 🏗️ Arquitetura

### Entidade Livro
- `id` (Long) - Chave primária, gerada automaticamente
- `titulo` (String) - Título do livro
- `autor` (String) - Autor do livro
- `anoPublicacao` (int) - Ano de publicação
- `lido` (boolean) - Status de leitura

### Endpoints da API
- `GET /api/livros` - Listar todos os livros
- `GET /api/livros/{id}` - Buscar livro por ID
- `POST /api/livros` - Criar novo livro
- `PUT /api/livros/{id}` - Atualizar livro existente
- `DELETE /api/livros/{id}` - Deletar livro

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **Maven**
- **Docker**
- **GitHub Actions**

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Docker (opcional, para containerização)

## 🚀 Como Executar

### Execução Local

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd biblioteca-pessoal
```

2. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

3. Acesse a aplicação:
- API: http://localhost:8080/api/livros
- Console H2: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

### Execução com Docker

1. Build da imagem:
```bash
docker build -t biblioteca-pessoal .
```

2. Execução do container:
```bash
docker run -p 8080:8080 biblioteca-pessoal
```

## 🧪 Testando a API

### Exemplo de criação de livro:
```bash
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "O Senhor dos Anéis",
    "autor": "J.R.R. Tolkien",
    "anoPublicacao": 1954,
    "lido": true
  }'
```

### Exemplo de listagem de livros:
```bash
curl http://localhost:8080/api/livros
```

## 🔄 Pipeline CI/CD

O projeto inclui um pipeline completo de CI/CD com os seguintes jobs:

1. **Build & Test** - Compila o código e executa os testes
2. **Build & Push Docker Image** - Constrói e publica a imagem no GHCR
3. **Deploy to Development** - Deploy automático para desenvolvimento
4. **Deploy to Production** - Deploy para produção (requer aprovação manual)

### Configuração dos Ambientes

Para configurar os ambientes no GitHub:

1. Vá em **Settings > Environments** do repositório
2. Crie os ambientes `development` e `production`
3. No ambiente `production`, configure **Required reviewers** para aprovação manual

## 📁 Estrutura do Projeto

```
biblioteca-pessoal/
├── src/
│   ├── main/
│   │   ├── java/com/biblioteca/bibliotecapessoal/
│   │   │   ├── controller/
│   │   │   │   └── LivroController.java
│   │   │   ├── model/
│   │   │   │   └── Livro.java
│   │   │   ├── repository/
│   │   │   │   └── LivroRepository.java
│   │   │   └── BibliotecaPessoalApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── .github/
│   └── workflows/
│       └── ci-biblioteca.yml
├── Dockerfile
├── pom.xml
└── README.md
```

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👨‍💻 Autor

Desenvolvido como parte da Atividade Prática de CI/CD - Construindo seu Próprio Pipeline de CI/CD.
Pipeline testado em: 10/01/2025 00:50:14
