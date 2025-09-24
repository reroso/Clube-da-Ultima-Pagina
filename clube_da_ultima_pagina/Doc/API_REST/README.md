# API REST - Guia de Testes

Este documento serve como índice para acessar os endpoints de cada entidade da API REST do Clube da Última Página.

## Base URL
```
http://localhost:8080
```

---

## Endpoints por Entidade

### 📚 [Usuários](./usuario-endpoints.md)
- Gerenciamento de usuários do sistema
- **Endpoint**: `/api/v1/usuarios`

### 📖 [Livros](./livro-endpoints.md)
- Cadastro e gerenciamento de livros
- **Endpoint**: `/api/v1/livros`

### 👥 [Grupos](./grupo-endpoints.md)
- Criação e administração de grupos de leitura
- **Endpoint**: `/api/v1/grupos`

### 📅 [Encontros](./encontro-endpoints.md)
- Agendamento de encontros dos grupos
- **Endpoint**: `/api/v1/encontros`

### 🔗 [Grupo-Livro](./grupo-livro-endpoints.md)
- Associação entre grupos e livros
- **Endpoint**: `/api/v1/grupo-livros`

### 👤 [Usuário-Grupo](./usuario-grupo-endpoints.md)
- Participação de usuários em grupos
- **Endpoint**: `/api/v1/usuario-grupos`

---

## Sequência Recomendada de Testes

1. **Usuários** - Crie usuários primeiro
2. **Livros** - Cadastre os livros
3. **Grupos** - Crie grupos (precisa de usuário líder)
4. **Grupo-Livro** - Associe livros aos grupos
5. **Usuário-Grupo** - Adicione membros aos grupos
6. **Encontros** - Agende encontros para os grupos

---

## Ferramentas de Teste

### Postman
1. Crie uma nova coleção
2. Configure a base URL: `http://localhost:8080`
3. Adicione o header: `Content-Type: application/json`

### cURL
Todos os arquivos contêm exemplos específicos de cURL para cada endpoint.

---

## Notas Gerais

- **Formato de Data**: Use ISO 8601 (`YYYY-MM-DDTHH:MM:SS`)
- **IDs**: Ao referenciar entidades, use apenas o campo `id`
- **Relacionamentos**: Certifique-se de que os IDs referenciados existem
- **Validações**: Consulte cada arquivo específico para detalhes de validação

---

## Códigos de Status HTTP Comuns

- **200**: OK - Operação bem-sucedida
- **201**: Created - Recurso criado com sucesso
- **204**: No Content - Recurso excluído com sucesso
- **400**: Bad Request - Dados inválidos
- **404**: Not Found - Recurso não encontrado
- **500**: Internal Server Error - Erro interno do servidor
