# LivroRestController - Endpoints

## Base URL
```
http://localhost:8080/api/v1/livros
```

---

## Endpoints Disponíveis

### GET - Listar todos os livros
```http
GET /api/v1/livros
```
*Não requer corpo da requisição*

### GET - Buscar livro por ID
```http
GET /api/v1/livros/1
```
*Não requer corpo da requisição*

### POST - Criar novo livro
```http
POST /api/v1/livros
Content-Type: application/json

{
    "titulo": "It - A Coisa",
    "autor": "Stephen King",
    "descricao": "Romance de terror que conta a história de um grupo de crianças que enfrenta uma entidade maligna na cidade de Derry, Maine."
}
```

### PUT - Atualizar livro
```http
PUT /api/v1/livros/1
Content-Type: application/json

{
    "titulo": "It - A Coisa (Edição Definitiva)",
    "autor": "Stephen King",
    "descricao": "Romance de terror que conta a história de um grupo de crianças que enfrenta uma entidade maligna na cidade de Derry, Maine. Esta edição inclui material adicional e prefácio do autor."
}
```

### DELETE - Excluir livro
```http
DELETE /api/v1/livros/1
```
*Não requer corpo da requisição*

---

## Exemplo com cURL

```bash
curl -X POST http://localhost:8080/api/v1/livros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "O Iluminado",
    "autor": "Stephen King",
    "descricao": "Romance de horror psicológico sobre um escritor que aceita um trabalho como zelador de inverno em um hotel isolado nas montanhas."
  }'
```

---

## Validações Importantes

- **Título**: Obrigatório, máximo 200 caracteres
- **Autor**: Obrigatório, máximo 100 caracteres
- **Descrição**: Opcional, texto livre

---

## Códigos de Status HTTP

- **200**: OK - Operação bem-sucedida (GET, PUT)
- **201**: Created - Livro criado com sucesso (POST)
- **204**: No Content - Livro excluído com sucesso (DELETE)
- **400**: Bad Request - Dados inválidos
- **404**: Not Found - Livro não encontrado
- **500**: Internal Server Error - Erro interno do servidor
