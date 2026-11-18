# Barbearia Manager

Sistema web desenvolvido em Java para gerenciamento de uma barbearia, permitindo o cadastro de clientes, barbeiros e agendamentos de serviços.

O projeto foi desenvolvido como atividade acadêmica da disciplina de Tecnologia de Desenvolvimento para Internet III, utilizando como base um sistema CRUD disponibilizado pelo professor.

## Tecnologias utilizadas

- Java 17
- Jakarta Servlets
- JSP e JSTL
- JDBC
- MySQL
- HTML, CSS e Bootstrap
- Apache Tomcat 10.1
- Eclipse IDE

## Funcionalidades

- Login e logout de usuários
- Proteção das páginas por filtro de autenticação
- Cadastro, edição, listagem e exclusão de clientes
- Cadastro, edição, listagem e exclusão de barbeiros
- Cadastro, edição, listagem e exclusão de agendamentos
- Relacionamento entre clientes, barbeiros e agendamentos
- Paginação dos cadastros
- Cadastro de publicações e empresas herdado do projeto-base
- Interface personalizada com tema de barbearia

## Estrutura do projeto

O sistema utiliza uma organização baseada no padrão MVC:

- **Model:** classes que representam as entidades do sistema.
- **DAO:** interfaces e classes responsáveis pelo acesso ao banco de dados.
- **Controller:** Servlets responsáveis por receber e processar requisições.
- **View:** páginas JSP responsáveis pela interface com o usuário.

O banco de dados contém seis tabelas: `users`, `posts`, `companies`, `barbers`, `appointments` e `login_accounts`.

## Como executar

1. Instale o Java 17, Eclipse IDE, Apache Tomcat 10.1 e MySQL.
2. Importe os arquivos para um projeto Dynamic Web Project chamado `Barbearia`, configurado com Jakarta Servlet 6.0.
3. Configure o contexto da aplicação como `crud-manager`.
4. Execute o arquivo `script-sql.sql` para criar o banco de dados e suas tabelas.
5. Configure as bibliotecas necessárias no projeto, incluindo MySQL Connector/J e JSTL.
6. Configure as variáveis de ambiente utilizadas pela conexão:

   - `DB_URL`: endereço JDBC do banco, por exemplo, `jdbc:mysql://localhost:3307/crud_manager`.
   - `DB_USER`: usuário do MySQL.
   - `DB_PASSWORD`: senha do MySQL.

7. Crie uma conta inicial na tabela `login_accounts`, associada a um registro de `users`, com uma senha armazenada no formato de hash PBKDF2 esperado pela aplicação.
8. Execute o projeto no Tomcat e acesse `http://localhost:8080/crud-manager/login`.

**Observação:** por segurança, nenhuma senha de banco de dados ou credencial inicial deve ser publicada no repositório.

## Objetivo acadêmico

Demonstrar a implementação de operações CRUD utilizando Servlets, JSP, JDBC, banco de dados relacional, autenticação, relacionamentos entre entidades e paginação.

## Autoria

Projeto desenvolvido individualmente para fins acadêmicos, a partir da estrutura inicial fornecida pelo professor.
