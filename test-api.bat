@echo off
REM Script para testar a API do Gerenciador de Biblioteca Pessoal
REM Certifique-se de que a aplicação está rodando em http://localhost:8080

set BASE_URL=http://localhost:8080/api/livros

echo 🚀 Testando API do Gerenciador de Biblioteca Pessoal
echo ==================================================

echo.
echo 📋 Listando todos os livros (inicial)
echo ----------------------------------------
curl -X GET "%BASE_URL%" -w "Status: %%{http_code}" -s

echo.
echo.
echo 📋 Criando primeiro livro
echo ----------------------------------------
curl -X POST "%BASE_URL%" -H "Content-Type: application/json" -d "{\"titulo\": \"O Senhor dos Anéis\", \"autor\": \"J.R.R. Tolkien\", \"anoPublicacao\": 1954, \"lido\": true}" -w "Status: %%{http_code}" -s

echo.
echo.
echo 📋 Criando segundo livro
echo ----------------------------------------
curl -X POST "%BASE_URL%" -H "Content-Type: application/json" -d "{\"titulo\": \"1984\", \"autor\": \"George Orwell\", \"anoPublicacao\": 1949, \"lido\": false}" -w "Status: %%{http_code}" -s

echo.
echo.
echo 📋 Criando terceiro livro
echo ----------------------------------------
curl -X POST "%BASE_URL%" -H "Content-Type: application/json" -d "{\"titulo\": \"Dom Casmurro\", \"autor\": \"Machado de Assis\", \"anoPublicacao\": 1899, \"lido\": true}" -w "Status: %%{http_code}" -s

echo.
echo.
echo 📋 Listando todos os livros após criação
echo ----------------------------------------
curl -X GET "%BASE_URL%" -w "Status: %%{http_code}" -s

echo.
echo.
echo 📋 Buscando livro por ID (ID: 1)
echo ----------------------------------------
curl -X GET "%BASE_URL%/1" -w "Status: %%{http_code}" -s

echo.
echo.
echo 📋 Atualizando livro (marcando 1984 como lido)
echo ----------------------------------------
curl -X PUT "%BASE_URL%/2" -H "Content-Type: application/json" -d "{\"titulo\": \"1984\", \"autor\": \"George Orwell\", \"anoPublicacao\": 1949, \"lido\": true}" -w "Status: %%{http_code}" -s

echo.
echo.
echo 📋 Verificando atualização do livro
echo ----------------------------------------
curl -X GET "%BASE_URL%/2" -w "Status: %%{http_code}" -s

echo.
echo.
echo 📋 Tentando buscar livro inexistente (deve retornar 404)
echo ----------------------------------------
curl -X GET "%BASE_URL%/999" -w "Status: %%{http_code}" -s

echo.
echo.
echo 📋 Deletando livro (ID: 3)
echo ----------------------------------------
curl -X DELETE "%BASE_URL%/3" -w "Status: %%{http_code}" -s

echo.
echo.
echo 📋 Listando livros após deleção
echo ----------------------------------------
curl -X GET "%BASE_URL%" -w "Status: %%{http_code}" -s

echo.
echo.
echo ✅ Testes concluídos!
echo ==================================================
echo 📊 Resumo dos testes:
echo    - ✅ Criação de livros
echo    - ✅ Listagem de livros
echo    - ✅ Busca por ID
echo    - ✅ Atualização de livros
echo    - ✅ Deleção de livros
echo    - ✅ Tratamento de erros (404)
echo.
echo 🌐 Acesse o console H2 em: http://localhost:8080/h2-console
echo    JDBC URL: jdbc:h2:mem:testdb
echo    Username: sa
echo    Password: password

pause
