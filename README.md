# Central Erros API
![Master Branch](https://github.com/savitoh/central-erro-api-codenation-acelera-dev-java/workflows/Master%20Branch/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=savitoh_central-erro-api-codenation-acelera-dev-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=savitoh_central-erro-api-codenation-acelera-dev-java)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=savitoh_central-erro-api-codenation-acelera-dev-java&metric=coverage)](https://sonarcloud.io/dashboard?id=savitoh_central-erro-api-codenation-acelera-dev-java)

Projeto Final da Aceleração Java Online da Codenation.

## Objetivo

Em projetos modernos é cada vez mais comum o uso de arquiteturas baseadas em serviços ou microsserviços. Nestes ambientes complexos, erros podem surgir em diferentes camadas da aplicação (backend, frontend, mobile, desktop) e mesmo em serviços distintos. Desta forma, é muito importante que os desenvolvedores possam centralizar todos os registros de erros em um local, de onde podem monitorar e tomar decisões mais acertadas. Neste projeto vamos implementar uma API Rest para centralizar registros de erros de aplicações.

Abaixo estão os requisitos desta API, o time terá total liberdade para tomar as decisões técnicas e de arquitetura da API, desde que atendam os requisitos abaixo.

## API

### Tecnologia

* Utilizar a tecnologia base da aceleração para o desenvolvimento (Exemplo: Java, Node.js)

### Premissas

* A API deve ser pensada para atender diretamente um front-end
* Deve ser capaz de gravar os logs de erro em um banco de dados relacional
* O acesso a ela deve ser permitido apenas por requisições que utilizem um token de acesso válido

### Funcionalidades

* Deve permitir a autenticação do sistema que deseja utilizar a API gerando o Token de Acesso
* Pode ser acessado por multiplos sistemas
* Deve permitir gravar registros de eventos de log salvando informações de Level(error, warning, info), Descrição do Evento, LOG do Evento, ORIGEM(Sistema ou Serviço que originou o evento), DATA(Data do evento), QUANTIDADE(Quantidade de Eventos de mesmo tipo)
* Deve permitir a listagem dos eventos juntamente com a filtragem de eventos por qualquer parâmetro especificado acima
* Deve suportar Paginação
* Deve suportar Ordenação por diferentes tipos de atributos
* A consulta de listagem não deve retornar os LOGs dos Eventos
* Deve permitir a busca de um evento por um ID, dessa maneira exibindo o LOG desse evento em específico

## Rodando a aplicação

A aplicação pode ser iniciada de diversas formas, abaixo exploraremos todas elas. 

### Importando em IDE

#### Requisitos:
* JDK 11
* Maven V3.2
* VScode, Eclipse, Netbeans, IntelliJ IDEA, escolha uma versão com suporte as tecnoligias acima.

Para importar corretamente o projeto deve ser gerado o JPA Criteria Metamodel, para tal rode o comando a seguir na pasta root do projeto:
```
mvn clean generate-sources
```

### Usando Banco em Memória (H2)

#### Requisitos: 
* JDK 11
* Maven V3.2

Rode o comando a seguir na pasta root do projeto: 
```
mvn sprinb-boot:run -Dspring.profiles.active=h2
```

### Usando Docker

#### Requisitos: 

* Docker e Docker-Compose

Rode o comandos abaixo na pasta root do projeto: 

```
docker-compose up --build  (Cria a imagem Docker da Aplicação e sobe o container)
```

### Documentação

Após levantar o Serviço, acesse a documentação, baseada na especificação da OpenAPI, no seguinte endereço:

* OpenAPI especificação: 

```
http://localhost:8080/api-docs 
```

* Swagger UI:

```
http://localhost:8080/swagger-ui
```



