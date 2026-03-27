# ImageLite

É uma aplicação Full-Stack de gerenciamento e upload de imagens. 

Este projeto foi desenvolvido como parte do meu aprendizado prático e arquitetural do curso "Spring Boot + ReactJS: Fullstack do Zero ao Deploy no Docker
" ministrado por [Dougllas Sousa](https://github.com/cursodsousa), integrando um back-end robusto em Java com uma interface moderna em React.

## 🧑‍💻 Tecnologias Utilizadas

A arquitetura do projeto foi construída utilizando as seguintes tecnologias:

* **Back-end:** Java 17, Spring Boot, Spring Data JPA, Spring Security
* **Front-end:** TypeScript, React, Next.js, Tailwind CSS
* **Banco de Dados:** PostgreSQL
* **Infraestrutura:** Docker & Docker Compose

## ⚙️ Como Executar o Projeto Localmente

Graças à conteinerização, rodar este projeto na sua máquina é um processo muito simples. Você não precisa ter o Java, Node.js ou o PostgreSQL instalados na sua máquina física.

### Pré-requisitos
* [Git](https://git-scm.com/)
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) (com o motor em execução)

### Passo a Passo

1. Clone este repositório:
   ```bash
   git clone https://github.com/rafael-bispo/imagelite
   ```

2. Acesse a pasta do projeto:
   ```bash
   cd imagelite
   ```

3. Suba a infraestrutura completa:
   ```bash
   docker compose up --build
   ```

A API estará disponível em `http://localhost:8080` e o Front-end poderá ser acessado em `http://localhost:3000`.