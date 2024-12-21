# DESENVOLVIMENTO DE SISTEMAS WEB II - 2024.2

## Projeto de Supermercado

Este projeto é um sistema web para gerenciamento de produtos, clientes e pedidos de um supermercado. Ele implementa funcionalidades completas de cadastro, listagem, atualização, exclusão e outras funcionalidades que fazem sentido para produtos, clientes e pedidos. Além disso, a aplicação inclui segurança com autenticação, autorização e suporte a token JWT, além de documentação da API via Swagger.

## Funcionalidades

### Endpoints

#### Clientes

- `POST /clientes`: Cria um novo cliente.
- `GET /clientes/{id}`: Busca um cliente pelo ID.
- `GET /clientes/cpf/{cpf}`: Busca um cliente pelo CPF.
- `GET /clientes`: Lista todos os clientes cadastrados.
- `PUT /clientes/{id}`: Atualiza um cliente existente.
- `DELETE /clientes/{id}`: Deleta um cliente pelo ID.
- `PUT /clientes/{id}/delete`: Deleta logicamente um cliente pelo ID.

#### Pedidos

- `POST /pedidos`: Cria um novo pedido.
- `GET /pedidos/{id}`: Busca um pedido pelo ID.
- `GET /pedidos`: Lista todos os pedidos cadastrados.
- `PUT /pedidos/{id}`: Atualiza um pedido existente.
- `DELETE /pedidos/{id}`: Deleta um pedido pelo ID.
- `PUT /pedidos/{id}/delete`: Deleta logicamente um pedido pelo ID.
- `PUT /pedidos/{idPedido}/add/{idProduto}`: Adiciona um produto ao pedido.
- `PUT /pedidos/{idPedido}/remove/{idProduto}`: Remove um produto do pedido.

#### Produtos

- `POST /produtos`: Cria um novo produto.
- `GET /produtos/{id}`: Busca um produto pelo ID.
- `GET /produtos`: Lista todos os produtos cadastrados.
- `PUT /produtos/{id}`: Atualiza um produto existente.
- `DELETE /produtos/{id}`: Deleta um produto pelo ID.
- `PUT /produtos/{id}/delete`: Deleta logicamente um produto pelo ID.

### Segurança

- **Autenticação e Autorização**:
  - Controle de acesso por usuários com dois perfis:
    - `ADMIN`: Acesso completo (GET, POST, PUT, DELETE) a produtos, pedidos, clientes e usuários.
    - `USER`: Acesso restrito a métodos GET para produtos, pedidos e clientes.
  - Geração e validação de tokens JWT para autenticação.
  - Criptografia de senhas com BCrypt.
- **Endpoints**:
  - `POST /auth/login` – Autenticar usuário e obter token JWT.
  - `POST /auth/register` – `ADMIN` registrar um novo usuário.

### Documentação

- **API Documentada**:
  - Implementada via Swagger, acessível em `localhost:8080/swagger-ui/index.html`.


## Tecnologias Utilizadas

- **Frameworks**: Spring Boot, Spring Security.
- **Banco de Dados**: MySQL.
- **Documentação**: Springdoc OpenAPI (Swagger).
- **Bibliotecas**: Lombok, Java JWT.
- **Validação**: Spring Validation.
- **Testes**: Spring Boot Test, Spring Security Test.
- **DevTools**: Spring Boot DevTools.

## Como Configurar e Executar

1. **Configuração do Banco de Dados**:
   - Crie um banco chamado `market`.
   - Configure as credenciais no arquivo `application.properties`.

2. **Executando o Sistema**:
   - Compile e execute com Maven.

      ```bash
      mvn spring-boot:run
      ```

   - A aplicação estará disponível em `http://localhost:8080`.

3. **Documentação**:
   - Acesse o Swagger em `http://localhost:8080/swagger-ui`.

4. **Testes de API**:
   - Utilize ferramentas como Postman, Insomnia ou Swagger para testar os endpoints.
