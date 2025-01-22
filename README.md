# EscolaSpring - Gerenciamento de Estudantes e Cursos

Este projeto é uma aplicação Spring Boot para gerenciar estudantes e cursos de uma instituição de ensino. Ele fornece funcionalidades para cadastrar, listar, atualizar e excluir estudantes e cursos, além de gerenciar a relação entre eles.


## Funcionalidades
- Cadastro de estudantes com nome, e-mail e CPF.
- Cadastro de cursos com nome e descrição.
- Associação de estudantes a cursos.
- Listagem de estudantes matriculados em um curso.
- Listagem de cursos em que um estudante está matriculado.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.x**
- **Jakarta Persistence API (JPA)**
- **H2 Database** (ou outro banco relacional à sua escolha)
- **Lombok**
- **Maven**

## Pré-requisitos
- **Java 17** ou superior instalado.
- **Maven** instalado.
- Um banco de dados configurado (H2 por padrão).

## Configuração do Ambiente
1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/escolaspring.git
   ```

2. Acesse o diretório do projeto:
   ```bash
   cd escolaspring
   ```

3. Configure o banco de dados:
   - O projeto usa o banco de dados H2 como padrão. Para usar outro banco, atualize o arquivo `application.properties` em `src/main/resources` com as informações do banco desejado.

   Exemplo para MySQL:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/escolaspring
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
   ```

   Certifique-se de criar o banco de dados com o nome **escolaspring** antes de executar a aplicação.

4. Configure as variáveis de ambiente para o banco de dados (opcional, mas recomendado):
   - `DB_URL`: URL de conexão do banco de dados.
   - `DB_USERNAME`: Usuário do banco.
   - `DB_PASSWORD`: Senha do banco.

5. Instale as dependências:
   ```bash
   mvn clean install
   ```

## Executando a Aplicação
Para executar o projeto, use o seguinte comando:
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: [http://localhost:8080](http://localhost:8080)

## Endpoints Principais
- **Estudantes**
  - `GET /estudantes`: Lista todos os estudantes.
  - `POST /estudantes`: Adiciona um novo estudante.
  - `PUT /estudantes/{id}`: Atualiza um estudante existente.
  - `DELETE /estudantes/{id}`: Exclui um estudante.

- **Cursos**
  - `GET /cursos`: Lista todos os cursos.
  - `POST /cursos`: Adiciona um novo curso.
  - `PUT /cursos/{id}`: Atualiza um curso existente.
  - `DELETE /cursos/{id}`: Exclui um curso.

## Estrutura do Banco de Dados
- Tabelas:
  - **estudantes**: Armazena os dados dos estudantes.
  - **cursos**: Armazena os dados dos cursos.
  - **estudante_curso**: Tabela intermediária para gerenciar a relação muitos-para-muitos entre estudantes e cursos.

## Testes
Para executar os testes automatizados, utilize o comando:
```bash
mvn test
```

## Autor
- **Daniel Azevedo**
