# Desafio Forum

Este é o repositório do projeto [desafio-forum]. Aqui você encontrará informações sobre como configurar, executar e contribuir para o projeto.

## Requisitos Técnicos

Certifique-se de ter os seguintes requisitos instalados antes de prosseguir:

- Java (Versão 17)
- Maven
- PostgreSQL

## Configuração do Projeto
- Clone o repositório:
```bash
git clone https://github.com/jorgeRjunior/desafio-forum.git
cd desafio-forum
```
- Construa o projeto:
```bash
mvn clean install
```

## Executando o Projeto
Execute o seguinte comando para iniciar o aplicativo:
```bash
java -jar target/blog-0.0.1-SNAPSHOT.jar
```
O aplicativo estará acessível em http://localhost:8080 e toda documentação de endpoint estará disponível a partir desta página.


## Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL chamado `tangerino`.
2. Atualize as configurações de banco de dados no arquivo `application.properties`.

```properties
# src/main/resources/application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/tangerino
spring.datasource.username=admin
spring.datasource.password=admin

```