# GrupoLivroRestController - Endpoints

## Base URL
```
http://localhost:8080/api/v1/grupo-livros
```

---

## Descrição
Este controller gerencia a relação entre grupos e livros, permitindo associar livros específicos aos grupos de leitura.

---

## Endpoints Disponíveis

### GET - Listar todas as relações grupo-livro
```http
GET /api/v1/grupo-livros
```
*Não requer corpo da requisição*

### GET - Buscar relação por ID
```http
GET /api/v1/grupo-livros/1
```
*Não requer corpo da requisição*

### POST - Criar nova relação grupo-livro
```http
POST /api/v1/grupo-livros
Content-Type: application/json

{
    "grupo": {
        "id": 1
    },
    "livro": {
        "id": 1
    },
    "observacao": "Livro escolhido para discussão no primeiro semestre. Foco na análise das técnicas de horror psicológico e construção de personagens."
}
```

### PUT - Atualizar relação grupo-livro
```http
PUT /api/v1/grupo-livros/1
Content-Type: application/json

{
    "grupo": {
        "id": 1
    },
    "livro": {
        "id": 1
    },
    "observacao": "Livro escolhido para discussão no primeiro semestre. Foco na análise das técnicas de horror psicológico, construção de personagens e o estilo narrativo único de Stephen King."
}
```

### DELETE - Excluir relação grupo-livro
```http
DELETE /api/v1/grupo-livros/1
```
*Não requer corpo da requisição*

---

## Exemplo com cURL

```bash
curl -X POST http://localhost:8080/api/v1/grupo-livros \
  -H "Content-Type: application/json" \
  -d '{
    "grupo": {
        "id": 1
    },
    "livro": {
        "id": 2
    },
    "observacao": "Livro selecionado para leitura do próximo mês."
  }'
```

---

## Validações Importantes

- **Grupo**: Obrigatório, deve referenciar um grupo existente através do ID
- **Livro**: Obrigatório, deve referenciar um livro existente através do ID
- **Observação**: Opcional, texto livre para anotações sobre a relação

---

## Códigos de Status HTTP

- **200**: OK - Operação bem-sucedida (GET, PUT)
- **201**: Created - Relação criada com sucesso (POST)
- **204**: No Content - Relação excluída com sucesso (DELETE)
- **400**: Bad Request - Dados inválidos
- **404**: Not Found - Relação não encontrada
- **500**: Internal Server Error - Erro interno do servidor
