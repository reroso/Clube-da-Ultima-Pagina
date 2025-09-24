# EncontroRestController - Endpoints

## Base URL
```
http://localhost:8080/api/v1/encontros
```

---

## Endpoints Disponíveis

### GET - Listar todos os encontros
```http
GET /api/v1/encontros
```
*Não requer corpo da requisição*

### GET - Buscar encontro por ID
```http
GET /api/v1/encontros/1
```
*Não requer corpo da requisição*

### POST - Criar novo encontro
```http
POST /api/v1/encontros
Content-Type: application/json

{
    "grupo": {
        "id": 1
    },
    "dataHora": "2024-01-15T19:00:00",
    "descricao": "Discussão sobre os primeiros capítulos de It - A Coisa. Vamos analisar como Stephen King constrói o terror e desenvolve os personagens infantis."
}
```

**Formato da data:** `YYYY-MM-DDTHH:MM:SS` (ISO 8601)

### PUT - Atualizar encontro
```http
PUT /api/v1/encontros/1
Content-Type: application/json

{
    "grupo": {
        "id": 1
    },
    "dataHora": "2024-01-15T20:00:00",
    "descricao": "Discussão sobre os primeiros capítulos de It - A Coisa. Vamos analisar como Stephen King constrói o terror, desenvolve os personagens infantis e explora os medos universais. ATENÇÃO: Horário alterado para 20h."
}
```

### DELETE - Excluir encontro
```http
DELETE /api/v1/encontros/1
```
*Não requer corpo da requisição*

---

## Exemplo com cURL

```bash
curl -X POST http://localhost:8080/api/v1/encontros \
  -H "Content-Type: application/json" \
  -d '{
    "grupo": {
        "id": 1
    },
    "dataHora": "2024-02-01T18:30:00",
    "descricao": "Encontro mensal do clube para discussão do livro da vez."
  }'
```

---

## Validações Importantes

- **Grupo**: Obrigatório, deve referenciar um grupo existente através do ID
- **Data e Hora**: Obrigatórias, formato ISO 8601 (YYYY-MM-DDTHH:MM:SS)
- **Descrição**: Opcional, texto livre para descrever o encontro

---

## Códigos de Status HTTP

- **200**: OK - Operação bem-sucedida (GET, PUT)
- **201**: Created - Encontro criado com sucesso (POST)
- **204**: No Content - Encontro excluído com sucesso (DELETE)
- **400**: Bad Request - Dados inválidos
- **404**: Not Found - Encontro não encontrado
- **500**: Internal Server Error - Erro interno do servidor
