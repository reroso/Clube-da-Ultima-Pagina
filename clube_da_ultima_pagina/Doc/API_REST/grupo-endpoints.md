# GrupoRestController - Endpoints

## Base URL
```
http://localhost:8080/api/v1/grupos
```

---

## Endpoints Disponíveis

### GET - Listar todos os grupos
```http
GET /api/v1/grupos
```
*Não requer corpo da requisição*

### GET - Buscar grupo por ID
```http
GET /api/v1/grupos/1
```
*Não requer corpo da requisição*

### POST - Criar novo grupo
```http
POST /api/v1/grupos
Content-Type: application/json

{
    "nome": "Clube de Terror e Suspense",
    "descricao": "Grupo dedicado à leitura e discussão de obras de terror, suspense e horror contemporâneo.",
    "lider": {
        "id": 1
    }
}
```

### PUT - Atualizar grupo
```http
PUT /api/v1/grupos/1
Content-Type: application/json

{
    "nome": "Clube de Terror e Horror Psicológico",
    "descricao": "Grupo dedicado à leitura e discussão de obras de terror, suspense, horror psicológico e thriller contemporâneo.",
    "lider": {
        "id": 2
    }
}
```

### DELETE - Excluir grupo
```http
DELETE /api/v1/grupos/1
```
*Não requer corpo da requisição*

---

## Exemplo com cURL

```bash
curl -X POST http://localhost:8080/api/v1/grupos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Clube de Ficção Científica",
    "descricao": "Grupo para discussão de livros de ficção científica.",
    "lider": {
        "id": 1
    }
  }'
```

---

## Validações Importantes

- **Nome**: Obrigatório, máximo 100 caracteres
- **Descrição**: Opcional, texto livre
- **Líder**: Deve referenciar um usuário existente através do ID

---

## Códigos de Status HTTP

- **200**: OK - Operação bem-sucedida (GET, PUT)
- **201**: Created - Grupo criado com sucesso (POST)
- **204**: No Content - Grupo excluído com sucesso (DELETE)
- **400**: Bad Request - Dados inválidos
- **404**: Not Found - Grupo não encontrado
- **500**: Internal Server Error - Erro interno do servidor
