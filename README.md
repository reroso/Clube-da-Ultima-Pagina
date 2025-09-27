# Clube da Última Página - API REST

*Felipe Soares e Kevin – Engenharia de Computação*

## 🎯 Descrição do Projeto

O **Clube da Última Página** é uma plataforma completa para gerenciamento de clubes de leitura virtuais. A aplicação oferece tanto uma interface web quanto uma **API RESTful completa** para criação de grupos de leitura, gerenciamento de livros, organização de encontros e acompanhamento de membros.

## 👥 Perfis de Usuário

- **Administrador:** Gerencia usuários, grupos e moderação da plataforma
- **Líder do Grupo:** Cria e gerencia grupos de leitura, organiza encontros e adiciona livros
- **Membro:** Participa de grupos, acompanha leituras e participa de discussões

## 🚀 Funcionalidades Implementadas

### Interface Web (MVC)
- ✅ Cadastro e autenticação de usuários
- ✅ Criação e gerenciamento de grupos de leitura
- ✅ Cadastro e busca de livros
- ✅ Agendamento de encontros
- ✅ Sistema de associações usuário-grupo
- ✅ Painel administrativo completo

### API REST (v1)
- ✅ **CRUD completo** para todas as entidades
- ✅ **Endpoints RESTful** com versionamento `/api/v1/`
- ✅ **Códigos HTTP semânticos** (200, 201, 204, 400, 404, 500)
- ✅ **Tratamento de exceções padronizado** com ProblemDetail (RFC 7807)
- ✅ **Documentação automática** com Swagger/OpenAPI
- ✅ **Consultas customizadas** nos repositórios

## 📋 Entidades Principais

- **Usuario** - Gestão de usuários e perfis
- **Grupo** - Grupos de leitura com líderes
- **Livro** - Catálogo de livros disponíveis
- **Encontro** - Agendamento de reuniões dos grupos
- **GrupoLivro** - Associação entre grupos e livros
- **UsuarioGrupo** - Participação de usuários em grupos
- **Perfil** - Tipos de usuário (ADMIN, LIDER, MEMBRO)

## 🔗 API REST Endpoints

### Usuários
```
GET    /api/v1/usuarios          # Listar todos os usuários
GET    /api/v1/usuarios/{id}     # Buscar usuário por ID
POST   /api/v1/usuarios          # Criar novo usuário
PUT    /api/v1/usuarios/{id}     # Atualizar usuário
DELETE /api/v1/usuarios/{id}     # Excluir usuário
```

### Grupos
```
GET    /api/v1/grupos            # Listar todos os grupos
GET    /api/v1/grupos/{id}       # Buscar grupo por ID
POST   /api/v1/grupos            # Criar novo grupo
PUT    /api/v1/grupos/{id}       # Atualizar grupo
DELETE /api/v1/grupos/{id}       # Excluir grupo
```

### Livros
```
GET    /api/v1/livros            # Listar todos os livros
GET    /api/v1/livros/{id}       # Buscar livro por ID
POST   /api/v1/livros            # Criar novo livro
PUT    /api/v1/livros/{id}       # Atualizar livro
DELETE /api/v1/livros/{id}       # Excluir livro
```

### Encontros
```
GET    /api/v1/encontros         # Listar todos os encontros
GET    /api/v1/encontros/{id}    # Buscar encontro por ID
POST   /api/v1/encontros         # Criar novo encontro
PUT    /api/v1/encontros/{id}    # Atualizar encontro
DELETE /api/v1/encontros/{id}    # Excluir encontro
```

### Associações
```
GET    /api/v1/grupo-livros      # Associações grupo-livro
GET    /api/v1/usuario-grupos    # Participações usuário-grupo
GET    /api/v1/perfis           # Tipos de perfil
```

## 🛠 Tecnologias Utilizadas

- **Java 22**
- **Spring Boot 3.4.0**
  - Spring Web MVC
  - Spring Data JPA
  - Spring Boot DevTools
- **Maven** - Gerenciamento de dependências
- **Thymeleaf** - Template engine para interface web
- **PostgreSQL** - Banco de dados relacional
- **springdoc-openapi** - Documentação Swagger/OpenAPI
- **ProblemDetail (RFC 7807)** - Tratamento padronizado de erros

## 📖 Documentação da API

### Swagger UI
A documentação interativa da API está disponível em:
```
http://localhost:8080/swagger-ui.html
```

### Tratamento de Erros (ProblemDetail)
Todas as APIs retornam erros no formato ProblemDetail (RFC 7807):

```json
{
  "type": "https://clube-livro.com/errors/usuario-validation",
  "title": "Erro de Validação - Usuário",
  "status": 400,
  "detail": "Já existe um usuário cadastrado com o email 'teste@email.com'.",
  "timestamp": "2025-09-26T23:30:00.123456Z",
  "category": "USUARIO"
}
```

## ⚙️ Configuração e Execução

### Pré-requisitos
- Java 22 ou superior
- PostgreSQL instalado e configurado
- Maven 3.8+

### Configuração do Banco de Dados
1. Crie um banco PostgreSQL chamado `clube_livro`
2. Configure as credenciais em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/clube_livro
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Executando a Aplicação

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/Clube-da-Ultima-Pagina.git

# Navegar para o diretório
cd clube_da_ultima_pagina

# Executar com Maven
mvn spring-boot:run
```

### Acessos
- **Interface Web:** http://localhost:8080
- **API REST:** http://localhost:8080/api/v1/
- **Documentação Swagger:** http://localhost:8080/swagger-ui.html

## 🗃️ Estrutura do Projeto

```
src/main/java/com/clubedolivro/clube_da_ultima_pagina/
├── controller/
│   ├── mvc/              # Controllers para interface web
│   └── restapi/          # Controllers REST API
├── model/                # Entidades JPA
├── repository/           # Repositórios Spring Data
├── service/              # Camada de negócio
├── exception/            # Exceções customizadas e handlers
└── ClubeUltimaPaginaApplication.java

src/main/resources/
├── templates/            # Templates Thymeleaf
├── static/              # Recursos estáticos (CSS, JS)
└── application.properties
```

## 📊 Funcionalidades Técnicas Implementadas

### Padrões RESTful
- ✅ Verbos HTTP corretos (GET, POST, PUT, DELETE)
- ✅ URLs baseadas em recursos com versionamento
- ✅ Códigos de status HTTP semânticos

### Persistência e Consultas
- ✅ Spring Data JPA com relacionamentos complexos
- ✅ Query Methods customizados (`findByEmailIgnoreCase`, `findByNomeContainingIgnoreCase`)
- ✅ Consultas JPQL customizadas com `@Query`
- ✅ Otimizações com JOIN FETCH

### Tratamento de Exceções
- ✅ `@ControllerAdvice` global unificado
- ✅ Detecção automática REST vs MVC
- ✅ ProblemDetail para APIs REST
- ✅ Views HTML personalizadas para interface web
- ✅ Exceções customizadas por domínio

### Documentação OpenAPI
- ✅ Swagger UI funcional e interativo
- ✅ Anotações `@Tag`, `@Operation`, `@ApiResponse`
- ✅ Documentação completa dos endpoints
- ✅ Exemplos de requisição e resposta

## 📅 Versionamento e Entregas

### Git Workflow
- **Branch principal:** `main`
- **Branch de desenvolvimento:** `develop` 
- **Branches de feature:** `feature/nome-da-funcionalidade`
- **Tags de versão:** `v2.0-FINAL`

### Marcos do Projeto

#### P1 - Fundação (v1.0-P1) ✅
- TR01: Documentação e estruturação inicial
- TR02: Configuração do ambiente Spring Boot
- TR03: Controllers MVC e rotas
- TR04: Entidades JPA, Services e Repositories
- TR05: Interface web com Thymeleaf

#### P2 - API REST Completa (v2.0-FINAL) ✅
- **Endpoints RESTful** com padrões HTTP corretos
- **CRUD completo** para todas as entidades
- **ProblemDetail (RFC 7807)** para tratamento de erros
- **Documentação Swagger/OpenAPI** interativa
- **Consultas customizadas** nos repositórios
- **Arquitetura limpa** com separação de responsabilidades

## 👨‍💻 Equipe de Desenvolvimento

- **Felipe Soares** - Desenvolvedor Full Stack
- **Kevin** - Desenvolvedor Full Stack

---

*Projeto desenvolvido como avaliação da disciplina de Engenharia de Software - API REST com Spring Boot*
