# Seed do Banco de Dados

## O que é o Seed?

O seed é um processo para popular o banco de dados com dados iniciais de exemplo. Isso é útil para:

- Desenvolvimento e testes
- Demonstrações da aplicação
- Ter dados consistentes para trabalhar

## Como funciona?

O sistema possui um endpoint REST que executa o seed sob demanda, sem necessidade de reiniciar a aplicação.

### Endpoint do Seed

- **URL**: `http://localhost:8080/api/v1/seed`
- **Método**: POST
- **Resposta de sucesso**: `"Seed executado com sucesso!"`
- **Resposta de erro**: `"Erro ao executar seed: [mensagem do erro]"` (Status 500)

## Como executar o Seed?

### 1. Via Terminal (curl)

```bash
curl -X POST http://localhost:8080/api/v1/seed
```

### 2. Via Postman/Insomnia

- Método: POST
- URL: `http://localhost:8080/api/v1/seed`
- Sem body necessário

### 3. Via qualquer cliente HTTP

Faça uma requisição POST para o endpoint acima.

## Como personalizar os dados do Seed

Para alterar os dados criados pelo seed, edite o arquivo `SeedService.java` localizado em `/src/main/java/com/clubedolivro/clube_da_ultima_pagina/service/SeedService.java`.

### Personalizando Usuários

```java
// Exemplo de como alterar os usuários
Usuario usuario1 = new Usuario();
usuario1.setNome("Seu Nome");           // Altere o nome
usuario1.setEmail("seu@email.com");     // Altere o email
usuario1.setSenha("suaSenha123");       // Altere a senha
usuario1.setPerfil(PerfilEnum.ADMINISTRADOR); // Altere o perfil
usuarioRepository.save(usuario1);
```

### Personalizando Livros

```java
// Exemplo de como alterar os livros
Livro livro1 = new Livro();
livro1.setTitulo("Título do seu livro");     // Altere o título
livro1.setAutor("Nome do autor");            // Altere o autor
livro1.setDescricao("Descrição do livro");   // Altere a descrição
livroRepository.save(livro1);
```

### Personalizando Grupos

```java
// Exemplo de como alterar os grupos
Grupo grupo1 = new Grupo();
grupo1.setNome("Nome do seu grupo");         // Altere o nome
grupo1.setDescricao("Descrição do grupo");   // Altere a descrição
grupo1.setLider(usuario1);                   // Defina o líder (deve ser um usuário já criado)
grupoRepository.save(grupo1);
```

### Personalizando Encontros

```java
// Exemplo de como alterar os encontros
Encontro encontro1 = new Encontro();
encontro1.setGrupo(grupo1);                                    // Associe a um grupo
encontro1.setDataHora(LocalDateTime.now().plusDays(10));       // Altere a data (10 dias no futuro)
encontro1.setDescricao("Descrição do seu encontro");           // Altere a descrição
encontroRepository.save(encontro1);
```

### Adicionando mais dados

Você pode adicionar quantos registros quiser seguindo o mesmo padrão:

```java
// Criando um terceiro usuário
Usuario usuario3 = new Usuario();
usuario3.setNome("Carlos");
usuario3.setEmail("carlos@email.com");
usuario3.setSenha("123456");
usuario3.setPerfil(PerfilEnum.LIDER_GRUPO);
usuarioRepository.save(usuario3);

// Criando um terceiro livro
Livro livro3 = new Livro();
livro3.setTitulo("O Hobbit");
livro3.setAutor("J.R.R. Tolkien");
livro3.setDescricao("Fantasia épica");
livroRepository.save(livro3);
```

### Dicas importantes

1. **Perfis disponíveis**: Use apenas os valores do enum `PerfilEnum`:

   - `PerfilEnum.ADMINISTRADOR`
   - `PerfilEnum.LIDER_GRUPO`
   - `PerfilEnum.MEMBRO`

2. **Ordem de criação**: Mantenha a ordem (Usuários → Livros → Grupos → Encontros → Relacionamentos) para evitar problemas de referência.

3. **Emails únicos**: Cada usuário deve ter um email único.

4. **Relacionamentos**: Para `UsuarioGrupo` e `GrupoLivro`, certifique-se de que os objetos referenciados já foram criados e salvos.

## Observações Importantes

1. **Execute apenas uma vez**: O seed não verifica duplicatas. Se executar novamente, pode gerar erro por tentar criar registros duplicados.

2. **Limpe o banco antes**: Se precisar executar novamente, primeiro limpe o banco de dados ou delete os registros criados anteriormente.

3. **Apenas para desenvolvimento**: Este seed foi criado para facilitar o desenvolvimento e testes. Em produção, considere usar dados reais.

4. **Perfis do enum**: O sistema usa apenas os perfis definidos no enum `PerfilEnum`:
   - ADMINISTRADOR
   - LIDER_GRUPO
   - MEMBRO

## Estrutura do Código

- **SeedService**: Contém a lógica para criar os dados (`/service/SeedService.java`)
- **SeedController**: Endpoint REST para executar o seed (`/controller/restapi/SeedController.java`)

## Troubleshooting

### Erro 405 - Method Not Allowed

- Você está fazendo GET em vez de POST
- Use `curl -X POST` ou configure seu cliente para POST

### Erro 500 - Internal Server Error

- Verifique o log da aplicação para ver a mensagem de erro detalhada
- Geralmente é por dados duplicados ou problemas de relacionamento

### Aplicação não responde

- Verifique se o Spring Boot está rodando na porta 8080
- Acesse `http://localhost:8080` para confirmar
