# 🚀 Desafio Técnico - Duxus Especialista

Este repositório contém a solução do desafio prático de programação.
A aplicação consiste em um sistema completo de gerenciamento que expõe uma API RESTful e interage com uma interface moderna e responsiva.

---

# 🛠️ Tecnologias Utilizadas

## Backend (`duxus-back`)

* Java 8 (1.8)
* Spring Boot 2.5.3

    * Spring Web
    * Spring Data JPA
    * Spring Validation
* H2 Database
* Springdoc OpenAPI / Swagger UI
* Maven

---

## Frontend (`duxus-front`)

* Vue 3 (Composition API)
* Vite
* TypeScript
* Axios

---

## Infraestrutura / DevOps

* Docker
* Docker Compose
* Nginx

---

# 📐 Arquitetura do Projeto

O sistema foi desenvolvido seguindo práticas modernas de arquitetura de software, focado no desacoplamento entre cliente e servidor (**Decoupled Architecture**).

```plaintext
📂 desafio-duxus-programador/
┣ 📂 duxus-back/
┃ ┗ 📄 Dockerfile
┣ 📂 duxus-front/
┃ ┗ 📄 Dockerfile
┗ 📄 docker-compose.yml
```

---

# 🧱 Estrutura da Aplicação

## 1️⃣ Backend — Arquitetura em Camadas

A API Java foi estruturada utilizando o padrão de arquitetura em camadas para isolar responsabilidades e facilitar testes de unidade e integração.

### Camadas

#### Controller

Responsável pela exposição dos endpoints HTTP da API REST e validação dos dados de entrada utilizando `@Valid`.

#### Service

Contém as regras de negócio, cálculos e lógica principal da aplicação.

#### Repository

Camada de acesso ao banco de dados utilizando Spring Data JPA.

#### Model / Entity

Mapeamento objeto-relacional (ORM) das entidades do banco H2.

---

## 2️⃣ Banco de Dados — H2

O banco H2 foi configurado para rodar de forma embutida junto à aplicação Java.

Para evitar perda de dados em reinicializações do container, foi configurada persistência em arquivo utilizando Docker Volumes.

---

## 3️⃣ Frontend — SPA Vue 3

O frontend foi desenvolvido como uma **Single Page Application (SPA)** utilizando Vue 3 e TypeScript.

### Benefícios da Stack

* Tipagem estática
* Melhor manutenção do código
* Maior segurança em tempo de compilação
* Build otimizado com Vite

Em ambiente Docker, os arquivos gerados na pasta `/dist` são servidos através de uma imagem leve do Nginx.

---

# 🚀 Como Executar o Projeto

> Você NÃO precisa ter Java, Maven ou Node instalados localmente.
> Apenas Docker e Docker Compose são necessários.

---

## 📥 Passo 1 — Clonar o Repositório

```bash
git clone https://github.com/Dourado8083/desafio-duxus-programador.git

cd desafio-duxus-programador
```

---

## ▶️ Passo 2 — Subir a Aplicação

Na raiz do projeto, execute:

```bash
docker compose up --build
```

### ℹ️ Observação

O parâmetro `--build` garante que os fontes atuais sejam compilados dentro dos containers.

A primeira execução pode demorar alguns minutos devido ao download das dependências do Maven e npm.

---

# 🔗 Ambientes Disponíveis

Após a inicialização dos containers, os seguintes serviços estarão disponíveis:

| Serviço        | URL                                           | Descrição                      |
| -------------- | --------------------------------------------- | ------------------------------ |
| Frontend (Vue) | `http://localhost`                            | Interface principal do sistema |
| Backend API    | `http://localhost:8080`                       | Endpoint base da API REST      |
| Swagger UI     | `http://localhost:8080/swagger-ui/index.html` | Documentação interativa da API |
| H2 Console     | `http://localhost:8080/h2-console`            | Console visual do banco H2     |

---

# 🗄️ Configuração do H2 Console

## Parâmetros de Conexão

```plaintext
JDBC URL: jdbc:h2:file:/data/duxusdb
User Name: sa
Password:
```

> Deixe o campo de senha em branco.

---

# 🛠️ Comandos Úteis

## Rodar apenas o backend

```bash
docker compose up backend --build
```

---

## Derrubar os containers

```bash
docker compose down
```

---

## Rodar em segundo plano

```bash
docker compose up -d
```

---

# 📌 Considerações Técnicas

* Arquitetura desacoplada entre frontend e backend
* Persistência de dados via Docker Volumes
* Build multi-stage nos containers
* Ambiente totalmente containerizado
* API documentada automaticamente com Swagger/OpenAPI
* Frontend responsivo utilizando Vue 3 + TypeScript

---

# 👨‍💻 Autor

Desenvolvido por Gustavo Dourado.
