# XPE Arquitetura de Software

Desafio final do Bootcamp de Arquitetura de Software da XPE

## Objetivos

- Aplicação dos conhecimentos de Arquitetura de sofwftare.
- Implementação de Uma API RESTfull
- Padrão MVC
- Variante CleanCode
- Especificções de design, api docs, arquitetura e organização de código

## Especificações básicas

**Projetos desenvoldvido em Java 21**

- Spring Boot 3.5.12
- H2 Data base (com persistencia em arquivo))


## Documentação

- [Swagger](http://localhost:8080/swagger-ui/index.html)
- [H2 Console](http://localhost:8080/h2-console)

## Execução

- Executar o arquivo `ApiApplication.java` ou executar o comando `mvn spring-boot:run` na raiz do projeto.

O sistema pode ser executado ainda:

- Com Maven
- Com Docker
- Com Docker Compose

Detalhes no README.md

## Testes

- Executar o comando `mvn test` na raiz do projeto.

Os endpoint das operações estão descritos no arquivo README.

O script `exemplo-uso.sh` de testes faz a autenticação e operação de CRUD na tabela de produtos.

Diagramas:

![Diagrama de classes](docs/diagrama-classes.png)

![Diagrama de sequencia](docs/diagrama-sequencia.png)