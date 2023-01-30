<h1 align="center"> API REST para Gerenciamento de Pessoas </h1>

[![NPM](https://img.shields.io/npm/l/react)](https://github.com/gabrielgrx/workshop-springboot3-jpa/blob/main/licence) 

# Sobre o projeto

API Restful com Spring Boot para gerenciamento do cadastro de pessoas com os seguintes atributos:

- Nome
- Data de nascimento
- Endereço:
  - Logradouro
  - CEP
  - Número
  - Cidade

# Tecnologias utilizadas
- IntelliJ IDEA
- Java 11
- Spring Boot 3.0.1
- Maven
- H2 DataBase
- Lombok
- SwaggerUI (OpenAPI)
- Postman
- Json

# API REST

## pessoa-controller

| Method | Path | Descrição
| ------------- | ------------- | ------------- |
| POST  | /pessoas  | Cadastra uma pessoa |
| GET  | /pessoas  | Retorna uma lista com todas as pessoas cadastradas |
| GET  | /pessoas/{pessoaId}  | Retorna a pessoa selecionada |
| PUT  | /pessoas/{pessoaId}  | Atualiza a pessoa selecionada |
| DELETE  | /pessoas/{pessoaId}  | Deleta a pessoa selecionada |

## endereço-controller

| Method | Path | Descrição
| ------------- | ------------- | ------------- |
| POST  | /pessoas/{pessoaId}/enderecos  | Cadastra de um endereço para a pessoa selecionada |
| GET  | /pessoas/{pessoaId}/enderecos  | Retorna uma lista com todos os endereços da pessoa selecionada |
| GET  | /pessoas/{pessoaId}/enderecos/principal  | Retorna o endereço principal da pessoa selecionada |
| PUT  | /pessoas/{pessoaId}/enderecos/{enderecoId}  | Atualiza o endereço selecionado |
| DELETE  | /pessoas/{pessoaId}/enderecos/{enderecoId}  | Deleta o endereço selecionado |


# Documentação da API com SwaggerUI

A documentação pode ser encontrada em: http://localhost:8080/swagger-ui/index.html

![image](https://user-images.githubusercontent.com/91229808/214113149-9a4963cc-ed83-4a97-8b87-2cabbd1bed66.png)


# Autor

Gabriel Ramos Xavier

https://www.linkedin.com/in/gabrielgrx/
