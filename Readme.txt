***** Sistema de Reservas de Salas *****

Este projeto é um sistema de reservas de salas de reunião, desenvolvido como parte de um desafio acadêmico. O sistema permite gerenciar salas, realizar reservas,
visualizar reservas existentes e cancelar reservas, utilizando o MongoDB como banco de dados.

***** Funcionalidades *****

- CRUD de Salas: Criar, ler e atualizar salas.
- CRUD de Reservas: Criar, ler, cancelar e listar reservas de salas.
- Validação de Conflitos de Horários: Garantir que as reservas não se sobreponham.
- Filtros de Busca: Pesquisar salas disponíveis por data, capacidade e recursos.

***** Tecnologias Utilizadas *****

- Spring Boot: Framework para desenvolvimento da aplicação.
- MongoDB: Banco de dados NoSQL para persistência de dados.
- JUnit: Framework de testes unitários.
- Docker: Para facilitar a configuração e execução do ambiente.
- Maven: Gerenciamento de dependências e construção do projeto.

***** Pré-requisitos *****

Antes de executar o projeto, verifique se você possui as seguintes ferramentas instaladas:

- Java 20 ou superior: A aplicação foi desenvolvida utilizando o Java 20.
- MongoDB (Opcional): Caso queira executar o MongoDB em localhost.
- Docker: O Docker deve estar em execução para que o sistema funcione corretamente.

***** Como Executar o Projeto *****

 1. Clonando o Repositório

- Clone o repositório para o seu ambiente local: git clone https://github.com/Lucasnevesz/Sistema_Reserva_de_Salas

2. Configurando o Docker

- É necessário criar um contêiner com o comando: docker-compose up -d
- Verifique se o MongoDB está rodando corretamente: docker ps
- O comando anterior irá listar o contêiner "mongo" em execução

3. Executando a aplicação

- Caso utilize o vscode, execute o "run java" na classe main "SistemaReservasApplication.java"
- A aplicação está em execução na página "http://localhost:8080/".

Observações: A página home funciona independente do contêiner estar em execução ou não, as demais precisam do contêiner em execução.