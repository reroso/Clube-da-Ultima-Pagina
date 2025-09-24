# UsuarioGrupoRestController - Endpoints

## Base URL
```
http://localhost:8080/api/v1/usuario-grupos
```

---

## Descrição
Este controller gerencia a relação entre usuários e grupos, permitindo adicionar ou remover membros dos grupos de leitura.

---

## Endpoints Disponíveis

### GET - Listar todas as relações usuário-grupo
```http
GET /api/v1/usuario-grupos
```
*Não requer corpo da requisição*

### GET - Buscar relação por ID
```http
GET /api/v1/usuario-grupos/1
```
*Não requer corpo da requisição*

### POST - Criar nova relação usuário-grupo
```http
POST /api/v1/usuario-grupos
Content-Type: application/json

{
    "usuario": {
        "id": 2
    },
    "grupo": {
        "id": 1
    }
}
```

### PUT - Atualizar relação usuário-grupo
```http
PUT /api/v1/usuario-grupos/1
Content-Type: application/json

{
    "usuario": {
        "id": 2
    },
    "grupo": {
        "id": 2
    }
}
```

### DELETE - Excluir relação usuário-grupo
```http
DELETE /api/v1/usuario-grupos/1
```
*Não requer corpo da requisição*

---

## Exemplo com cURL

```bash
curl -X POST http://localhost:8080/api/v1/usuario-grupos \
  -H "Content-Type: application/json" \
  -d '{
    "usuario": {
        "id": 3
    },
    "grupo": {
        "id": 1
    }
  }'
```

---

## Validações Importantes

- **Usuário**: Obrigatório, deve referenciar um usuário existente através do ID
- **Grupo**: Obrigatório, deve referenciar um grupo existente através do ID
- A combinação usuário-grupo deve ser única (um usuário não pode estar duplicado no mesmo grupo)

---

## Códigos de Status HTTP

- **200**: OK - Operação bem-sucedida (GET, PUT)
- **201**: Created - Relação criada com sucesso (POST)
- **204**: No Content - Relação excluída com sucesso (DELETE)
- **400**: Bad Request - Dados inválidos
- **404**: Not Found - Relação não encontrada
- **500**: Internal Server Error - Erro interno do servidor
