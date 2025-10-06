# UsuarioRestController - Endpoints

## Base URL
```
http://localhost:8080/api/v1/usuarios
```

---

## Endpoints Disponíveis

### GET - Listar todos os usuários
```http
GET /api/v1/usuarios
```
*Não requer corpo da requisição*

### GET - Buscar usuário por ID
```http
GET /api/v1/usuarios/1
```
*Não requer corpo da requisição*

### POST - Criar novo usuário
```http
POST /api/v1/usuarios
Content-Type: application/json

{
    "nome": "João Silva",
    "email": "joao.silva@email.com",
    "senha": "senha123",
    "perfil": "MEMBRO"
}
```

**Exemplos de perfis válidos:**
- `ADMINISTRADOR`
- `LIDER_GRUPO` 
- `MEMBRO`

### PUT - Atualizar usuário
```http
PUT /api/v1/usuarios/1
Content-Type: application/json

{
    "nome": "João Santos",
    "email": "joao.santos@email.com",
    "senha": "novasenha123",
    "perfil": "LIDER_GRUPO"
}
```

### DELETE - Excluir usuário
```http
DELETE /api/v1/usuarios/1
```
*Não requer corpo da requisição*

---

## Exemplo com cURL

```bash
curl -X POST http://localhost:8080/api/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Silva",
    "email": "maria@email.com",
    "senha": "senha123",
    "perfil": "MEMBRO"
  }'
```

---

## Validações Importantes

- **Nome**: Obrigatório, máximo 100 caracteres
- **Email**: Obrigatório, formato válido, único no sistema
- **Senha**: Obrigatória, mínimo 6 caracteres
- **Perfil**: Obrigatório, deve ser um dos valores válidos

---

## Códigos de Status HTTP

- **200**: OK - Operação bem-sucedida (GET, PUT)
- **201**: Created - Usuário criado com sucesso (POST)
- **204**: No Content - Usuário excluído com sucesso (DELETE)
- **400**: Bad Request - Dados inválidos
- **404**: Not Found - Usuário não encontrado
- **500**: Internal Server Error - Erro interno do servidor
