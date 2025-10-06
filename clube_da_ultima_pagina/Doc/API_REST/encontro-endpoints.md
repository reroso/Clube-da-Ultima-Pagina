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
    "dataHora": "2025-12-15T19:00:00",
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
    "dataHora": "2025-10-15T20:00:00",
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

- **Grupo**: 
  - ⚠️ **OBRIGATÓRIO** - Não pode ser null
  - Deve referenciar um grupo existente através do ID
  - O grupo deve existir no banco de dados
- **Data e Hora**: 
  - ⚠️ **OBRIGATÓRIAS** - Não podem ser null
  - Formato ISO 8601: `YYYY-MM-DDTHH:MM:SS`
  - **🚨 REGRA IMPORTANTE**: Deve ser pelo menos 1 hora no futuro
  - **🚨 CONFLITO**: Não pode haver outros encontros do mesmo grupo com menos de 2 horas de diferença
- **Descrição**: Opcional, texto livre

## ❌ Principais Causas do Erro 400

### 1. **Data no passado ou muito próxima**
```json
// ❌ ERRO - Data no passado ou menos de 1h no futuro
{
  "grupo": {"id": 1},
  "dataHora": "2024-01-15T19:00:00"  // Data passada
}

// ✅ CORRETO - Data com pelo menos 1h no futuro
{
  "grupo": {"id": 1},
  "dataHora": "2025-12-15T19:00:00"  // Data futura
}
```

### 2. **Conflito de horário (mesmo grupo)**
```json
// ❌ ERRO - Se já existe encontro do grupo 1 entre 17h-21h do mesmo dia
{
  "grupo": {"id": 1},
  "dataHora": "2025-12-15T19:00:00"
}

// ✅ CORRETO - Mais de 2h de diferença do encontro anterior
{
  "grupo": {"id": 1},  
  "dataHora": "2025-12-16T19:00:00"
}
```

### 3. **Outras validações comuns**
```json
// ❌ ERRO - Grupo faltando
{
  "dataHora": "2025-12-15T19:00:00"
}

// ❌ ERRO - Data faltando  
{
  "grupo": {"id": 1}
}

// ❌ ERRO - Formato de data inválido
{
  "grupo": {"id": 1},
  "dataHora": "15/12/2025 19:00"
}
```

---

## Códigos de Status HTTP

- **200**: OK - Operação bem-sucedida (GET, PUT)
- **201**: Created - Encontro criado com sucesso (POST)
- **204**: No Content - Encontro excluído com sucesso (DELETE)
- **400**: Bad Request - Dados inválidos
- **404**: Not Found - Encontro não encontrado
- **500**: Internal Server Error - Erro interno do servidor
